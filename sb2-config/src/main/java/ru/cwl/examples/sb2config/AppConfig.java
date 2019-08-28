package ru.cwl.examples.sb2config;


import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ToString
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
@Data
class AppConfig {

//    @Value("${app.name}")
    private String name;

//    @Value("${app.age}")
    private Integer age;

    private HashMap<String, Integer> someMap=new HashMap<>();
    private List<String> someStrList=new ArrayList<>();
    private Map<Integer,String> intStrMap=new ConcurrentHashMap<>();
    private UrlProp url=new UrlProp();
    private Map<String,Boolean> runServices=new HashMap<>();

    public HashMap<String, Integer> getSomeMap() {
        return someMap;
    }

    public void setSomeMap(HashMap<String, Integer> someMap) {
        this.someMap = someMap;
    }

    public List<String> getSomeStrList() {
        return someStrList;
    }

    public void setSomeStrList(List<String> someStrList) {
        this.someStrList = someStrList;
    }

    public Map<Integer, String> getIntStrMap() {
        return intStrMap;
    }

    public void setIntStrMap(Map<Integer, String> intStrMap) {
        this.intStrMap = intStrMap;
    }

    public UrlProp getUrl() {
        return url;
    }

    public void setUrl(UrlProp url) {
        this.url = url;
    }

    public Map<String, Boolean> getRunServices() {
        return runServices;
    }

    public void setRunServices(Map<String, Boolean> runServices) {
        this.runServices = runServices;
    }



    //    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }

    @ToString
    static class UrlProp{
        String in="http://default";
        String out;

        public String getIn() {
            return in;
        }

        public void setIn(String in) {
            this.in = in;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }
    }
}