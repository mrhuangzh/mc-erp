package com.mc.erp.common;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = McErpCommonApplicationTests.class)
@ComponentScan(value = {"com.mc.erp.common"})
class McErpCommonApplicationTests {

    @Test
    void contextLoads() {
    }

}
