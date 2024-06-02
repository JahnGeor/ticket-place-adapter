package ru.kidesoft.desktop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DesktopApplicationTests {

    private static final Logger log = LogManager.getLogger(DesktopApplicationTests.class);

    @Test
    void contextLoads() {
        log.info("Hello");
    }


}
