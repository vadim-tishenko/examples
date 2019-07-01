package ru.cwl.example.springdataredis;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.List;

@Service
public class Example {



    // добавляем актуальный шаблон
//    @Autowired
    private final RedisTemplate<String, String> template;

    // добавляем шаблон как ListOperations
    // который может быть также и Value, Set, ZSet и HashOperations
//    @Resource(name = "redisTemplate")
    private final ListOperations<String, String> listOps;

    public Example(RedisTemplate<String, String> template, ListOperations<String, String> listOps) {

        this.template = template;
        this.listOps = listOps;
    }

    public void addLink(String userId, URL url) {
        listOps.leftPush(userId, url.toExternalForm());
        // или используем шаблон напрямую
//        template.boundListOps(userId).leftPush(url.toExternalForm());
    }

    public void addVal(String key, String value) {
        listOps.leftPush(key, value);
    }



    public List<String> getValForKey(String key) {
        return listOps.range(key, 0, -1);
    }

    public void syncTest(){
//        syncCommands.set("key", "Hello, Redis!");
//        String res = syncCommands.get("key");
//
//        System.out.println(res);
//
        long start1 = System.currentTimeMillis();
//
//

        Long len = listOps.size("list:1");
        System.out.println(len);

        for (int j = 0; j <20 ; j++) {
            long start = System.currentTimeMillis();

            for (int i = 0; i < 10000; i++) {
                addVal("list:1", "valvalval");
            }

            long stop = System.currentTimeMillis();

            final double x = (stop - start) / 1000.0;

            System.out.print("sec: "+ x);
            System.out.println("w iops:"+(int)(10000/x));
        }


        len = listOps.size("list:1");

        long stop2 = System.currentTimeMillis();

        final double x2 = (stop2 - start1) / 1000.0;

        System.out.print("\nsec: "+ x2);
        System.out.println(" all w iops:"+(10000*20)/x2);

        System.out.println(len);
        System.out.println("read...");
        long start = System.currentTimeMillis();
        List<String> resList = getValForKey("list:1");
        long stop = System.currentTimeMillis();
        final double x = (stop - start) / 1000.0;
        System.out.print("\nsec: "+ x);
        System.out.println("r iops:"+len/x);

        System.out.println(resList.size());


        listOps.trim("list:1",0,10000-1);
//
//        connection.close();
//        redisClient.shutdown();
    }


    @Autowired
    private ReactiveRedisTemplate<String, String> template22;

    public Mono<Long> addLink22(String userId, String url) {
        return template22.opsForList().leftPush(userId, url);
    }

    public void asyncTest(){
//        syncCommands.set("key", "Hello, Redis!");
//        String res = syncCommands.get("key");
//
//        System.out.println(res);
//
        long start1 = System.currentTimeMillis();
//
//

        Long len = listOps.size("list:1");
        System.out.println(len);
        Subscriber<Long> actual = new Subscriber<Long>() {
            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        for (int j = 0; j <20 ; j++) {
            long start = System.currentTimeMillis();

            for (int i = 0; i < 10000; i++) {
                Mono<Long> res = addLink22("list:1", "valvalval");

                res.subscribe();
//                res.block();
            }

            long stop = System.currentTimeMillis();

            final double x = (stop - start) / 1000.0;

            System.out.print("sec: "+ x);
            System.out.println("w iops:"+(int)(10000/x));
        }

        Mono<Long> res = addLink22("list:1", "valvalval");
        res.subscribe();

        Mono<Long> rLen = template22.opsForList().size("list:1");
        len=rLen.block();
//        len = listOps.size("list:1");
        System.out.println("afrer insert len:" +len);

        long stop2 = System.currentTimeMillis();

        final double x2 = (stop2 - start1) / 1000.0;

        System.out.print("\nsec: "+ x2);
        System.out.println(" all w iops:"+(10000*20)/x2);

        System.out.println(len);
        System.out.println("read...");
        long start = System.currentTimeMillis();
        List<String> resList = getValForKey("list:1");
        long stop = System.currentTimeMillis();
        final double x = (stop - start) / 1000.0;
        System.out.print("\nsec: "+ x);
        System.out.println("r iops:"+len/x);

        System.out.println(resList.size());


        listOps.trim("list:1",0,10000-1);
//
//        connection.close();
//        redisClient.shutdown();
    }


}