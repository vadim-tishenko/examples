package ru.cwl.example.telematicfilestorage.storage.redisstorage;

import redis.clients.jedis.Jedis;
import ru.cwl.example.telematicfilestorage.storage.BaseTrafficRepository;
import ru.cwl.example.telematicfilestorage.storage.TrafficDto;
import ru.cwl.example.telematicfilestorage.storage.TrafficRepository;

import java.util.*;

// Jedis Implementation
public class RedisStringTelematicStorage extends BaseTrafficRepository implements TrafficRepository {

    final Jedis jedis;// = new Jedis();

    final long periodLen = 50;

    public RedisStringTelematicStorage(Jedis jedis) {
        this.jedis = jedis;
    }

/*
 jedis.lpush("queue#tasks", "firstTask");
jedis.lpush("queue#tasks", "secondTask");

String task = jedis.rpop("queue#tasks");


     */

    @Override
    public void save(TrafficDto dto) {
        jedis.append(getKey(dto), content(dto));
    }

    private String getKey(TrafficDto dto) {
        return "traffic2:" + dto.getTrId() + ":" + getPeriodId(dto.getTime());
    }

    @Override
    public List<TrafficDto> find(long trId, long from, long to) {
        List<TrafficDto> result = new ArrayList<>();
        // читать из нескольких файлов, фильтровать результат, пригодиться в датчиках.
        for (long periodId = getPeriodId(from); periodId <= getPeriodId(to - 1); periodId++) {
            result.addAll(readFromSet(trId, periodId, from, to));
        }
        return result;
    }

    long getPeriodId(long time) {
        return time / periodLen;
    }

    Collection<TrafficDto> readFromSet(long trId, long periodId, long from, long to) {
        Set<String> res ;
        String ss = jedis.get("traffic:" + trId + ":" + periodId);
        String[] r = ss.split("\n");
        List<TrafficDto> resDtoList = toDto(trId, Arrays.asList(r), from, to);
        return resDtoList;
    }

}
