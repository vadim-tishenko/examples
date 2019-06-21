package ru.cwl.example.telematicfilestorage;


import ru.cwl.example.telematicfilestorage.storage.TrafficDto;

public class TrafficDtoSource {

    private final int maxSize = 10000;
    private final TrafficDto arr[] = new TrafficDto[maxSize];
    private int count = 0;

    public TrafficDtoSource() {
        init();
    }

    void init() {
        for (int i = 0; i < maxSize; i++) {
            TrafficDto dto = TrafficDto.builder()
                    .trId(i)
                    .time(i % 10)
                    .lon(2.4).lat(5.6)
                    .speed(10).heading(200)
                    .alt(100)
                    .build();
            arr[i] = dto;
        }
    }

    TrafficDto get() {
        if (count >= maxSize) count = 0;
        return arr[count++];
    }

}
