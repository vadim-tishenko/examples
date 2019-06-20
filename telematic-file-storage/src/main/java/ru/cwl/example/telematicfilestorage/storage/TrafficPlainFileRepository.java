package ru.cwl.example.telematicfilestorage.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class TrafficPlainFileRepository implements TrafficRepository {

    public static final long SECONDS_PER_DAY = 60 * 60 * 24;

    private final String rootPath;
    private final long periodLen;

    public TrafficPlainFileRepository() {
        this("D:\\var\\plain-file-storage", SECONDS_PER_DAY);
    }

    public TrafficPlainFileRepository(String rootPath, long periodLen) {
        this.rootPath = rootPath;
        this.periodLen = periodLen;
    }

    @Override
    public void save(TrafficDto dto) {
        String content = content(dto);
        final String fileName = fileNameForPeriod(dto.getTrId(), getPeriodId(dto.getTime()));
        final Path path = Paths.get(fileName);

        try {
            File targetFile = new File(fileName);
            if (!targetFile.exists()) {
                File parent = targetFile.getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + parent);
                }
                if (!targetFile.createNewFile()) {
                    throw new IllegalStateException("Couldn't create file: " + targetFile);
                }
            }

            save1(path, content);

//            save2(fileName,content);


        } catch (IOException e) {
            log.error("save", e);
        }
    }

    private void save1(Path path, String content) throws IOException {
        Files.write(path, content.getBytes(), StandardOpenOption.APPEND);
    }

    void save2(String fileName,String content) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(content);
        writer.close();
    }

    @Override
    public List<TrafficDto> find(long trId, long from, long to) {
        List<TrafficDto> result = new ArrayList<>();
        // читать из нескольких файлов, фильтровать результат, пригодиться в датчиках.
        for (long periodId = getPeriodId(from); periodId <= getPeriodId(to - 1); periodId++) {
            result.addAll(readFromFile(trId, periodId, from, to));
        }
        return result;
    }

    List<TrafficDto> readFromFile(long trId, long periodId, long from, long to) {
        Path path = Paths.get(fileNameForPeriod(trId, periodId));
        try {
            List<String> lines = Files.readAllLines(path);
            List<TrafficDto> result = toDto(trId, lines, from, to);
            return result;
        } catch (IOException e) {
            log.error("read", e);
        }
        return Collections.emptyList();
    }

//    public List<TrafficDto> readFromFile(long trId, long periodId, long from, long to) {
//        List<TrafficDto> result = readFromFile(trId, periodId,from,to);
////        result.removeIf(dto -> dto.time < from || dto.time >= to);
//        return result;
//    }


//    public List<TrafficDto> readFromFile(long trId, long periodId) {
//        Path path = Paths.get(fileNameForPeriod(trId, periodId);
//        // читать из нескольких файлов, фильтровать результат, пригодиться в датчиках.
//        try {
//            List<String> lines = Files.readAllLines(path);
//            List<TrafficDto> result = toDto(trId, lines);
//            return result;
//        } catch (IOException e) {
//            log.error("", e);
//        }
//        return Collections.emptyList();
//    }

//    private List<TrafficDto> toDto(long trId, List<String> lines) {
//        List<TrafficDto> result = new ArrayList<>(lines.size());
//        for (String line : lines) {
//            TrafficDto dto = getTrafficDto(trId, line);
//            result.add(dto);
//        }
//        return result;
//    }

    private List<TrafficDto> toDto(long trId, List<String> lines, long from, long to) {
        List<TrafficDto> result = new ArrayList<>(lines.size());
        for (String line : lines) {
            TrafficDto dto = getTrafficDto(trId, line);
            if (dto.getTime() < from) continue;
            if (dto.getTime() >= to) continue;
            result.add(dto);
        }
        return result;
    }

    private TrafficDto getTrafficDto(long trId, String line) {
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

//    String fileName(TrafficDto dto) {
//        return rootPath + "\\" + getPeriodId(dto.time) + "\\" + dto.getTrId() + "\\traffic.csv";
//    }


    // К периодам??
//    String fileName(long trId, long time) {
//        return rootPath + "\\" + getPeriodId(time) + "\\" + trId + "\\traffic.csv";
//    }

    String fileNameForPeriod(long trId, long periodId) {
        return rootPath + File.separator + periodId + File.separator + trId + File.separator + "traffic.csv";
    }

    /// математика периодов....
    long getPeriodId(long time) {
        return time / periodLen;
    }

    long getPeriodStart(long time) {
        return getPeriodId(time) * periodLen;
    }

    long getPeriodFinish(long time) {
        return (getPeriodId(time) + 1) * periodLen;
    }
    ///

    String content(TrafficDto dto) {
        String result = String.format(Locale.ROOT,"%d;%f;%f;%d;%d;%d\n", dto.getTime(), dto.getLat(), dto.getLon(), dto.getSpeed(),
                dto.getHeading(), dto.getAlt());
        return result;
    }

}
/*

Are you doing this for logging purposes? If so there are several libraries for this. Two of the most popular are Log4j and Logback.

Java 7+
If you just need to do this one time, the Files class makes this easy:

try {
    Files.write(Paths.get("myfile.txt"), "the text".getBytes(), StandardOpenOption.APPEND);
}catch (IOException e) {
    //exception handling left as an exercise for the reader
}
Careful: The above approach will throw a NoSuchFileException if the file does not already exist. It also does not append a newline automatically (which you often want when appending to a text file). Steve Chambers's answer covers how you could do this with Files class.

However, if you will be writing to the same file many times, the above has to open and close the file on the disk many times, which is a slow operation. In this case, a buffered writer is better:

try(FileWriter fw = new FileWriter("myfile.txt", true);
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw))
{
    out.println("the text");
    //more code
    out.println("more text");
    //more code
} catch (IOException e) {
    //exception handling left as an exercise for the reader
}
Notes:

The second parameter to the FileWriter constructor will tell it to append to the file, rather than writing a new file. (If the file does not exist, it will be created.)
Using a BufferedWriter is recommended for an expensive writer (such as FileWriter).
Using a PrintWriter gives you access to println syntax that you're probably used to from System.out.
But the BufferedWriter and PrintWriter wrappers are not strictly necessary.
 */