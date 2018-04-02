/**
 * AppUtils.java
 * realtime-stats
 *
 * @author Shishir Kumar
 */
package com.sk.sales.stats.util;

public final class AppUtils {

    public static final int DEFAULT_SIZE = 60; // it is the refresh time of the
                                               // data on the dashboard in
                                               // seconds

    // Endpoints
    public static final String ENDPOINT_SALES = "/sales";
    public static final String ENDPOINT_STATS = "/statistics";

    // Request/Response params
    public static final String REQ_PARAM_SALES_AMOUNT = "sales_amount";
    public static final String RES_PARAM_TOTAL_SALES_AMT = "total_sales_amount";
    public static final String RES_PARAM_AVG_AMT_PER_ORDER = "average_amount_per_order";

    private AppUtils() {
        // Hidden constructor
    }

}
