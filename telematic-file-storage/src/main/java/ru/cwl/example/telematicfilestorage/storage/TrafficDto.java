package ru.cwl.example.telematicfilestorage.storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrafficDto {
    long trId;
    long time;
    double lon;
    double lat;
    int speed;
    int alt;
    int heading;
}
