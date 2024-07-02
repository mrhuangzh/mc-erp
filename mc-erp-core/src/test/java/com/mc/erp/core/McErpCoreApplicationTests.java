package com.mc.erp.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = McErpCoreApplicationTests.class)
@ComponentScan(value = {"com.mc.erp.core"})
class McErpCoreApplicationTests {

    @Test
    void contextLoads() {
    }

}
