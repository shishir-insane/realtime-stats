/**
 * RealtimeStatsApplication.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RealtimeStatsApplication {

    /**
     * The main method.
     *
     * @param args
     *            the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(RealtimeStatsApplication.class, args);
    }
}
