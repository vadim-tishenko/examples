package ru.cwl.example.telematicfilestorage.storage;

import java.util.List;

public interface TrafficRepository {
    void save(TrafficDto dto);
    // time>=from && time<to
    List<TrafficDto> find(long trId, long from, long to);
}
