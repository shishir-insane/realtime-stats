/**
 * ServletInitializer.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.boot.web.support.SpringBootServletInitializer#
     * configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
        return application.sources(RealtimeStatsApplication.class);
    }

}
