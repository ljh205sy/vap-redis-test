package com.vrv.vap.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author wh1107066
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class VapProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VapProducerApplication.class, args);
    }

}
