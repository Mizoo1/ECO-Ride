package com.ECO.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CounterControllerIT {

        @Value(value="${local.server.port}")
        private int port;

        @Autowired
        private TestRestTemplate restTemplate;




    @Test
    public void countReturns0() throws Exception {
        int actual = this.restTemplate.getForObject("http://localhost:" + port + "/count",
                Integer.class);
        assertEquals(actual, 0);
    }

    }


