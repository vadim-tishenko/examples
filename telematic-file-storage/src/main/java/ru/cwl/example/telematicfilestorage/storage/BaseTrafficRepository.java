package ru.cwl.example.telematicfilestorage.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class BaseTrafficRepository {
    public List<TrafficDto> toDto(long trId, Collection<String> lines, long from, long to) {
        List<TrafficDto> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            TrafficDto dto = getTrafficDto(trId, line);
            if (dto.getTime() < from) continue;
            if (dto.getTime() >= to) continue;
            result.add(dto);
        }
        return result;
    }

    public TrafficDto getTrafficDto(long trId, String line) {
        String[] sss = line.split(";");
        return TrafficDto.builder()
                .trId(trId)
                .time(Long.valueOf(sss[0]))
                .lat(Double.valueOf(sss[1]))
                .lon(Double.valueOf(sss[2]))
                .speed(Integer.valueOf(sss[3]))
                .heading(Integer.valueOf(sss[4]))
                .alt(Integer.valueOf(sss[5]))
                .build();
    }

    public String content(TrafficDto dto) {

        String result = String.format(Locale.ROOT, "%d;%f;%f;%d;%d;%d\n", dto.getTime(), dto.getLat(), dto.getLon(), dto.getSpeed(),
                dto.getHeading(), dto.getAlt());
        return result;
    }

    public String content2(TrafficDto dto) {
        StringBuilder sb = new StringBuilder();
        sb.append(dto.getTime()).append(';');
//        String result = String.format(Locale.ROOT,"%d;%f;%f;%d;%d;%d\n"
//                , dto.getTime()
        sb.append(dto.getLat()).append(';');
        sb.append(dto.getLon()).append(';');
        sb.append(dto.getSpeed() ).append(';');
        sb.append(dto.getHeading()).append(';');
        sb.append(dto.getAlt());//.append(';');
        return sb.toString();
    }
}
