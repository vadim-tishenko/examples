package ru.cwl.example.telematicfilestorage.storage;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TrafficPlainFileRepositoryTest {

    public static final String ROOT_PATH = "c:/test/test-file-storage";
    private TrafficPlainFileRepository repo;

    @Before
    public void before() {
        repo = new TrafficPlainFileRepository(ROOT_PATH, 10);
    }

    @Test
    public void fileNameForPeriod() {
        assertEquals(ROOT_PATH + File.separatorChar + 15 + File.separatorChar + 10 + File.separatorChar + "traffic.csv", repo.fileNameForPeriod(10, 15));
    }

    @Test
    public void getPeriodId() {
        assertEquals(0, repo.getPeriodId(0));
        assertEquals(0, repo.getPeriodId(1));
        assertEquals(0, repo.getPeriodId(9));
        assertEquals(1, repo.getPeriodId(10));
    }

    @Test
    public void content() {
        TrafficDto dto = TrafficDto.builder()
                .trId(1)
                .time(10)
                .lon(2.4).lat(5.6)
                .speed(11).heading(200)
                .alt(100)
                .build();
        String content = repo.content(dto);
        assertEquals("10;5.600000;2.400000;11;200;100\n", content);

    }
}