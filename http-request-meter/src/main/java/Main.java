import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import ru.cwl.examples.httprequestmeter.MeasureResult;
import ru.cwl.examples.httprequestmeter.MeasureTask;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class Main {
// https://www.baeldung.com/httpclient-guide

    public static void main(String[] args) throws IOException {

        final String SAMPLE_URL = "http://iot.f5x.ru:8080/measures/for/1/0?period=DAY";

        HttpGet request = new HttpGet(SAMPLE_URL);
        System.out.println("start samp");
        MeasureTask measureTask = new MeasureTask(request);
        List<MeasureTask> taskList = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            taskList.add(measureTask);
        }

        Collection<MeasureResult> result = testRunner(taskList, 10);
        calcStatistic(result);

 /*
 PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(10);
        connManager.setDefaultMaxPerRoute(10);

        final String SAMPLE_URL = "http://iot.f5x.ru:8080/measures/for/1/0?period=DAY";
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();
  Map<Long, Integer> resultMap = new ConcurrentHashMap<>();
        Map<Long, Integer> rpsMap = new ConcurrentHashMap<>();
         int total = 200;
 ConcurrentLinkedQueue<MeasureResult> resultList = new ConcurrentLinkedQueue<>();
 for (int i = 0; i < total; i++) {
            long start = System.currentTimeMillis();
            CloseableHttpResponse response = httpClient.execute(measureTask.getRequest());
            long finish = System.currentTimeMillis();

            int statusCode = response.getStatusLine().getStatusCode();
            long contentLength = EntityUtils.toByteArray(response.getEntity()).length;

            MeasureResult measureResult = new MeasureResult(measureTask, start, finish, statusCode, contentLength);
            resultList.add(measureResult);

            response.close();
            long l = finish - start;
            Integer count = resultMap.computeIfAbsent(l, (a) -> 0);
            resultMap.put(l, count + 1);

            long sec = (int) (finish / 1000);
            Integer count2 = rpsMap.computeIfAbsent(sec, (a) -> 0);
            rpsMap.put(sec, count2 + 1);
        }

        System.out.println("finish samp");

        calcStatistic(resultList);*/
    }

    static Collection<MeasureResult> testRunner(Collection<MeasureTask> tasks) {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(20);
        connManager.setDefaultMaxPerRoute(10);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

        ConcurrentLinkedQueue<MeasureResult> resultList = new ConcurrentLinkedQueue<>();

        for (MeasureTask task : tasks) {
            try {
                long start = System.currentTimeMillis();
                CloseableHttpResponse response = httpClient.execute(task.getRequest());
                long finish = System.currentTimeMillis();

                int statusCode = response.getStatusLine().getStatusCode();
                long contentLength = EntityUtils.toByteArray(response.getEntity()).length;
                response.close();

                MeasureResult measureResult = new MeasureResult(task, start, finish, statusCode, contentLength);
                resultList.add(measureResult);
//                System.out.println("finish " + task);

            } catch (IOException e) {
                System.err.println(e);
            }
        }

        return resultList;
    }

    static Collection<MeasureResult> testRunner(Collection<MeasureTask> tasks, int threadsCount) {
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);


        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(20);
        connManager.setDefaultMaxPerRoute(12);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

        ConcurrentLinkedQueue<MeasureResult> resultList = new ConcurrentLinkedQueue<>();

        System.out.println("strat-----");
        int taskCount = 0;
        for (MeasureTask task : tasks) {
            taskCount++;
            final int tc = taskCount;
            executor.execute(() -> {
                try {
//                    System.out.println("start " + tc);

                    long start = System.currentTimeMillis();
                    CloseableHttpResponse response = httpClient.execute(task.getRequest());
                    long finish = System.currentTimeMillis();

                    int statusCode = response.getStatusLine().getStatusCode();
                    long contentLength = EntityUtils.toByteArray(response.getEntity()).length;
                    response.close();

                    MeasureResult measureResult = new MeasureResult(task, start, finish, statusCode, contentLength);
                    resultList.add(measureResult);
//                    System.out.println("finish " + tc);

                } catch (IOException e) {
                    System.err.println("ERR!");
                    e.printStackTrace();
                }
            });
        }
        executor.execute(() -> {
            executor.shutdown();
        });
        System.out.println("finish-----");

        try {
            executor.awaitTermination(100, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("finish2 --- " + resultList.size());

        return resultList;
    }


    private static void calcStatistic(Collection<MeasureResult> resultList) {
        long min = Long.MAX_VALUE;
        long max = Long.MIN_VALUE;
        long contentLenAcc = 0;
        Map<Long, Integer> resultMap = new HashMap<>();
        Map<Long, Integer> rpsMap = new HashMap<>();
        for (MeasureResult measure : resultList) {
            long l = measure.getFinish() - measure.getStart();
            Integer count = resultMap.computeIfAbsent(l, (a) -> 0);
            resultMap.put(l, count + 1);

            long sec = (int) (measure.getFinish() / 1000);
            Integer count2 = rpsMap.computeIfAbsent(sec, (a) -> 0);
            rpsMap.put(sec, count2 + 1);
            min = Long.min(min, measure.getStart());
            max = Long.max(max, measure.getFinish());
            contentLenAcc += measure.getResultLen();
        }
        System.out.printf("===================\n");
        double t = (max - min) / 1000.0;
        int size = resultList.size();
        System.out.printf("t:%2.2f s:%d l:%d bps:%5.0f\n", t, size, contentLenAcc, contentLenAcc / t);
        System.out.println("--------------");
        printPercentiles(resultMap, size);
        printRps(rpsMap, size);
        System.out.printf("===================\n");
    }

    static void printRps(Map<Long, Integer> rpsMap, int total) {
        double avg = rpsMap.values().stream().mapToInt(a -> a).average().getAsDouble();
        int min = rpsMap.values().stream().mapToInt(a -> a).min().getAsInt();
        int max = rpsMap.values().stream().mapToInt(a -> a).max().getAsInt();
        System.out.printf(Locale.ENGLISH, "rps avg:%5.2f min:%d max:%d\n", avg, min, max);
    }

    static void printPercentiles(Map<Long, Integer> resultMap, int total) {

        double[] percentile = {0.5, 0.9, 0.95, 0.99, 0.999, 1};
//        for (double p : percentile) {
//            double r = total * p;
//            System.out.println(r);
//        }
        System.out.printf("  percentiles\n----------------\n");
        TreeMap<Long, Integer> m2 = new TreeMap<>(resultMap);
        int count = 0;
        int n = 0;
        for (Map.Entry<Long, Integer> e : m2.entrySet()) {
            count += e.getValue();
            if (count > percentile[n] * total) {
                System.out.printf(Locale.ENGLISH, "%5.1f%% %5dms\n", 100 * percentile[n], e.getKey());
                n += 1;
//                if (n >= percentile.length) n = percentile.length - 1;
            }
        }
//        m2.forEach((ms, cnt) -> {
//            System.out.printf("%d %d\n", ms, cnt);
//        });
    }

}
/**
 * connManager = new PoolingHttpClientConnectionManager();
 * CloseableHttpClient client = HttpClients.custom()
 * .setConnectionManager(connManager).build();
 * HttpGet get = new HttpGet("http://google.com");
 * CloseableHttpResponse response = client.execute(get);
 * <p>
 * EntityUtils.consume(response.getEntity());
 * response.close();
 * client.close();
 * connManager.close();
 */

/*
https://www.baeldung.com/httpclient-connection-management#connection-manager
HttpGet get = new HttpGet("http://www.baeldung.com");
PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
CloseableHttpClient client = HttpClients.custom(). setConnectionManager(connManager).build();
MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(client, get);
MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(client, get);
MultiHttpClientConnThread thread30 = new MultiHttpClientConnThread(client, get);
thread1.start();
thread2.start();
thread3.start();
thread1.join();
thread2.join();
thread3.join();

 */
 /*       CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(1000)
                .setConnectTimeout(1000)
                .build();

        HttpGet httpget1 = new HttpGet("http://localhost/1");
        httpget1.setConfig(requestConfig);
        CloseableHttpResponse response1 = httpclient.execute(httpget1);//, context);
        try {
            HttpEntity entity1 = response1.getEntity();
        } finally {
            response1.close();
        }
        HttpGet httpget2 = new HttpGet("http://localhost/2");
        CloseableHttpResponse response2 = httpclient.execute(httpget2);//, context);
        try {
            HttpEntity entity2 = response2.getEntity();
        } finally {
            response2.close();
        }*/