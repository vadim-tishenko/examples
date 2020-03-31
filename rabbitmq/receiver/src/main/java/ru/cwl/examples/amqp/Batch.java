package ru.cwl.examples.amqp;

import java.util.ArrayList;
import java.util.List;

public class Batch {
    List<byte[]> packets = new ArrayList<>();
    long started = System.currentTimeMillis();
}
