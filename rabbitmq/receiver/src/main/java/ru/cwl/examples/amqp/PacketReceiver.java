package ru.cwl.examples.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor

@Component
public class PacketReceiver {

    private final PacketSaver packetSaver;

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(byte [] packet) throws InterruptedException {
        packetSaver.process(packet);
    }

}
