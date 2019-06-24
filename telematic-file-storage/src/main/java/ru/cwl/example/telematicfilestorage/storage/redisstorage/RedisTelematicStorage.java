package ru.cwl.example.telematicfilestorage.storage.redisstorage;

import redis.clients.jedis.Jedis;
import ru.cwl.example.telematicfilestorage.storage.BaseTrafficRepository;
import ru.cwl.example.telematicfilestorage.storage.TrafficDto;
import ru.cwl.example.telematicfilestorage.storage.TrafficRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

// Jedis Implementation
public class RedisTelematicStorage extends BaseTrafficRepository implements TrafficRepository {

    final Jedis jedis;// = new Jedis();

    final long periodLen = 50;

    public RedisTelematicStorage(Jedis jedis) {
        this.jedis = jedis;
    }

/*
    5.3. Sets
Redis Sets are an unordered collection of Strings that come in handy when you want to exclude repeated members:

jedis.sadd("nicknames", "nickname#1");
jedis.sadd("nicknames", "nickname#2");
jedis.sadd("nicknames", "nickname#1");

Set<String> nicknames = jedis.smembers("nicknames");
boolean exists = jedis.sismember("nicknames", "nickname#1");
     */

    @Override
    public void save(TrafficDto dto) {
        jedis.sadd(getKey(dto), content(dto));
    }

    private String getKey(TrafficDto dto) {
        return "traffic:" + dto.getTrId() + ":" + getPeriodId(dto.getTime());
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
        Set<String> res = jedis.smembers("traffic:" + trId + ":" + periodId);
        List<TrafficDto> resDtoList = toDto(trId, res, from, to);
        return resDtoList;
    }

}
