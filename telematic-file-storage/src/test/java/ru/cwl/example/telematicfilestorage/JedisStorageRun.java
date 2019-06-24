package ru.cwl.example.telematicfilestorage;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import ru.cwl.example.telematicfilestorage.storage.TrafficPlainFileRepository;
import ru.cwl.example.telematicfilestorage.storage.TrafficRepository;
import ru.cwl.example.telematicfilestorage.storage.redisstorage.RedisStringTelematicStorage;
import ru.cwl.example.telematicfilestorage.storage.redisstorage.RedisTelematicStorage;

@Slf4j
public class JedisStorageRun {

    public static final int LINE_COUNT = 15000;

    public static void main(String[] args) {
        TrafficRepository repo = new RedisStringTelematicStorage(new Jedis());
        TrafficDtoSource source = new TrafficDtoSource();
//        TrafficDto dto = TrafficDto.builder()
//                .trId(1)
//                .time(10)
//                .lon(2.4).lat(5.6)
//                .speed(10).heading(200)
//                .alt(100)
//                .build();

        log.info("start save");
        long start = System.currentTimeMillis();
        for (int i = 0; i < LINE_COUNT; i++) {
            repo.save(source.get());
        }
        long finish = System.currentTimeMillis();

        log.info("end save,iops: {} ", LINE_COUNT / ((finish - start) / 1000.0f));
//        log.info("start  load");
//        start = System.currentTimeMillis();

//        List<TrafficDto> res = repo.find(1, 0, 20);
//        finish = System.currentTimeMillis();
//        log.info("end load,iops: {} ", res.size() / ((finish - start) / 1000.0f));
//
//        System.out.println(res.size() == LINE_COUNT);



    }
}
