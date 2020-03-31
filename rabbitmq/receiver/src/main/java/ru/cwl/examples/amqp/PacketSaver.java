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
import java.util.ArrayList;
import java.util.List;

@Slf4j

@Component
public class PacketSaver implements ApplicationContextAware {


    Batch batch = new Batch();

    long started = System.currentTimeMillis();
    long totalCount = 0;

    @PostConstruct
    void init() {
        log.info("PacketSaver start");
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


    private void save(Batch batch) {
        log.info("save! sz:{}" , batch.packets.size());
        String dir = "/home/vad/temp/o1/";
        String fileName = dir + batch.started;
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
            log.error("",e);
        } catch (IOException e) {
            log.error("",e);
        }
        log.info("save finish!");
    }

    private boolean isBatchDone(Batch batch) {
        final int MAX_BACH_SIZE = 10000;
        final int MAX_SECONDS_PER_BATCH = 10;
        long now = System.currentTimeMillis();
        return batch.packets.size() >= MAX_BACH_SIZE || (now - batch.started) >= MAX_SECONDS_PER_BATCH * 1000;
    }

    boolean isProcessDone() {
        final int MAX_PROCESS_SIZE = 1000_0000;
        final int MAX_PROCESS_SECONDS = 120;
        long now = System.currentTimeMillis();
        return totalCount >= MAX_PROCESS_SIZE || (now - started) >= MAX_PROCESS_SECONDS * 1000;
    }

    ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

}
