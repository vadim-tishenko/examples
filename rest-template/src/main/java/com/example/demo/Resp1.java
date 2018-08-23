package com.example.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 23.08.2018 23:07.
 */
public class Resp1 {
    List<Route> routes=new ArrayList<>();
    String code;

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
