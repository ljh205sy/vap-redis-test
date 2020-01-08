package com.vrv.vap.consumer.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wh1107066
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "vap.cache")
public class CacheManagerProperties {

    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
