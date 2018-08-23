package com.example.demo;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        //https://spring.io/guides/gs/consuming-rest/
        //https://www.baeldung.com/rest-template
        //http://project-osrm.org/docs/v5.15.2/api/#route-service
        // http://router.project-osrm.org/route/v1/driving/13.388860,52.517037;13.397634,52.529407;13.428555,52.523219?overview=false
        String url = "http://router.project-osrm.org/route/v1/driving/13.388860,52.517037;13.428555,52.523219?overview=false";
        RestTemplate restTemplate = new RestTemplate();

        String p1 = "55.876316,37.487774";
        String p2 = "55.884083,37.535217";
        String p3 = "37.487774,55.876316";
        String p4 = "37.535217,55.884083";
        url=String.format("http://router.project-osrm.org/route/v1/driving/%s;%s?overview=false",p3,p4);
//        String fooResourceUrl                = "http://localhost:8080/spring-rest/foos";
        Resp1 response = restTemplate.getForObject(url, Resp1.class);


    }
}
