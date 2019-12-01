package ru.cwl.examples.httprequestmeter;

import org.apache.http.client.methods.HttpRequestBase;


public class MeasureTask {
    final HttpRequestBase request;

    public MeasureTask(HttpRequestBase request) {
        this.request = request;
    }

    public HttpRequestBase getRequest() {
        return request;
    }
}
