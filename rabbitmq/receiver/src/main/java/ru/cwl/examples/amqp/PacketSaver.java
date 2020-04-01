package ru.cwl.examples.amqp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j

@Component
public class PacketSaver implements ApplicationContextAware {
    String dir = "/home/vad/temp/o2/";
    int fileNo = 0;
    Batch batch = new Batch();

    long started = System.currentTimeMillis();
    long totalCount = 0;

    @PostConstruct
    void init() {
        log.info("PacketSaver start");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        LocalDateTime ldt = LocalDateTime.now();

        dir = dir + ldt.format(fmt)+"/";
        File  f = new File(dir);
        boolean r1 = f.mkdirs();
        log.info("dump to dir: {} {}",dir,r1);
    }

    @PreDestroy
    void preDestroy() {
        log.info("PacketSaver finish");
    }

    synchronized void process(byte[] packet) {

        if (isProcessDone()) {
            save(batch);
            log.info("done1");
            ((ConfigurableApplicationContext) ctx).close();
            log.info("done2");
            return;
        }

        if (isBatchDone(batch)) {
            save(batch);
            batch = new Batch();
        }
        batch.packets.add(packet);
        totalCount++;
    }

    private void fakeSave(Batch batch) {
        log.info("fake save! sz:" + batch.packets.size());
    }


    DecimalFormat fmt = new DecimalFormat("00000000");

    private void save(Batch batch) {
        //??StopWatch watch = new StopWatch();
        fileNo++;
        log.info("save! sz:{}", batch.packets.size());
        // TODO: 01.04.2020 создавать каталог по времени начала ггггммдд-ччммсс 

        String fileName = dir + fmt.format(fileNo);
        String dataFileName = fileName + ".dat";
        String indexFileName = fileName + ".idx";

        try {
            OutputStream outputStream = null;
            DataOutputStream dataOutputStream = null;
            outputStream = new BufferedOutputStream(new FileOutputStream(dataFileName));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(indexFileName)));
            int start = 0;
            for (byte[] packet : batch.packets) {
                dataOutputStream.writeInt(start);
                dataOutputStream.writeInt(packet.length);
                start += packet.length;
                outputStream.write(packet);
            }
            outputStream.close();
            dataOutputStream.close();

        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
        log.info("save finish!");
    }

    // TODO: 01.04.2020 параметры в конфиг+дефолтные параметры
    private boolean isBatchDone(Batch batch) {
        final int MAX_BACH_SIZE = 100000;
        final int MAX_SECONDS_PER_BATCH = 30;
        long now = System.currentTimeMillis();
        return batch.packets.size() >= MAX_BACH_SIZE || (now - batch.started) >= MAX_SECONDS_PER_BATCH * 1000;
    }

    boolean isProcessDone() {
        final int MAX_PROCESS_SIZE = 7*1000_0000;
        final int MAX_PROCESS_SECONDS = 60*60;
        long now = System.currentTimeMillis();
        return totalCount >= MAX_PROCESS_SIZE || (now - started) >= MAX_PROCESS_SECONDS * 1000;
    }

    ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
