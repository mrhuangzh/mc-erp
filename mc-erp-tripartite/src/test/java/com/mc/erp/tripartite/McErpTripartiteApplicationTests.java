package com.mc.erp.tripartite;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest(classes = McErpTripartiteApplicationTests.class)
@ComponentScan(value = {"com.mc.erp.tripartite"})
class McErpTripartiteApplicationTests {

    @Test
    void contextLoads() {
    }

}
