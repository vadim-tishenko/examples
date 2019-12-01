import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
// https://www.baeldung.com/httpclient-guide

    public static void main(String[] args) throws IOException {


        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(10);
        connManager.setDefaultMaxPerRoute(10);

        final String SAMPLE_URL = "http://iot.f5x.ru:8080/measures/for/1/0?period=DAY";
        HttpClient client = HttpClientBuilder.create().setConnectionManager(connManager).build();
        CloseableHttpClient c2 = HttpClients.custom().setConnectionManager(connManager).build();
        HttpGet request = new HttpGet(SAMPLE_URL);
        Map<Long, Integer> resultMap = new ConcurrentHashMap<>();
        Map<Long, Integer> rpsMap = new ConcurrentHashMap<>();
        System.out.println("start samp");
        int total = 200;

        long testStart = System.currentTimeMillis();

        for (int i = 0; i < total; i++) {
            long start = System.currentTimeMillis();
            CloseableHttpResponse response = c2.execute(request);
            long finish = System.currentTimeMillis();

            int statusCode = response.getStatusLine().getStatusCode();
            response.close();
            long l = finish - start;
            Integer count = resultMap.computeIfAbsent(l, (a) -> 0);
            resultMap.put(l, count + 1);

            long sec= (int) (finish/1000);
            Integer count2 = rpsMap.computeIfAbsent(sec, (a) -> 0);
            rpsMap.put(sec, count2 + 1);


            System.out.printf("%d %d\n", statusCode, l);

        }
        long testFinish = System.currentTimeMillis();

        System.out.println("finish samp");

        printPercentiles(resultMap, total);
        printRps(rpsMap, total);

//        assertThat(statusCode, equalTo(HttpStatus.SC_OK));
    }

    static void printRps(Map<Long, Integer> rpsMap, int total) {
        double avg = rpsMap.values().stream().mapToInt(a -> a).average().getAsDouble();
        int min = rpsMap.values().stream().mapToInt(a -> a).min().getAsInt();
        int max = rpsMap.values().stream().mapToInt(a -> a).max().getAsInt();
        System.out.printf(Locale.ENGLISH,"rps avg:%5.2f min:%d max:%d\n", avg, min, max);
    }

    static void printPercentiles(Map<Long, Integer> resultMap, int total) {

        double[] percentile = {0.5, 0.9, 0.95, 0.99, 0.999};
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