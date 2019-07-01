import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.List;

public class MainLettuceListExample {

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://localhost:6379/0");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();
        RedisAsyncCommands<String, String> asyncCommands = connection.async();


        syncCommands.set("key", "Hello, Redis!");
        String res = syncCommands.get("key");

        System.out.println(res);

        long start1 = System.currentTimeMillis();


        Long len = syncCommands.llen("list:1");
        System.out.println(len);

        for (int j = 0; j <20 ; j++) {
            long start = System.currentTimeMillis();

            for (int i = 0; i < 10000; i++) {
                asyncCommands.lpush("list:1", "valvalval");
            }

            long stop = System.currentTimeMillis();

            final double x = (stop - start) / 1000.0;

            System.out.print("sec: "+ x);
            System.out.println("w iops:"+(int)(10000/x));
        }


        len = syncCommands.llen("list:1");

        long stop2 = System.currentTimeMillis();

        final double x2 = (stop2 - start1) / 1000.0;

        System.out.print("\nsec: "+ x2);
        System.out.println(" all w iops:"+(10000*20)/x2);

        System.out.println(len);
        System.out.println("read...");
        long start = System.currentTimeMillis();
        List<String> resList = syncCommands.lrange("list:1", 0, -1);
        long stop = System.currentTimeMillis();
        final double x = (stop - start) / 1000.0;
        System.out.print("\nsec: "+ x);
        System.out.println("r iops:"+len/x);

        System.out.println(resList.size());


        syncCommands.ltrim("list:1",0,10000-1);

        connection.close();
        redisClient.shutdown();
    }
}
