package ru.cwl.example.sb2logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoggingController2 {

    Logger logger = LoggerFactory.getLogger(LoggingController2.class);

    @RequestMapping("/b")
    public String index() {
        logger.trace("B TRACE Message");
        logger.debug("B DEBUG Message");
        logger.info("B INFO Message");
        logger.warn("B WARN Message");
        logger.error("B ERROR Message");

        return "Howdy! Check out the Logs to see the output...";
    }
}
