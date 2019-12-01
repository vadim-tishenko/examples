package ru.cwl.examples.httprequestmeter;

public class MeasureResult {
    final MeasureTask measureTask;
    final long start;
    final long finish;
    final int resultCode;
    final long resultLen;

    public MeasureResult(MeasureTask measureTask, long start, long finish, int resultCode, long resultLen) {
        this.measureTask = measureTask;
        this.start = start;
        this.finish = finish;
        this.resultCode = resultCode;
        this.resultLen = resultLen;
    }

    public MeasureTask getMeasureTask() {
        return measureTask;
    }

    public long getStart() {
        return start;
    }

    public long getFinish() {
        return finish;
    }

    public int getResultCode() {
        return resultCode;
    }

    public long getResultLen() {
        return resultLen;
    }
}
