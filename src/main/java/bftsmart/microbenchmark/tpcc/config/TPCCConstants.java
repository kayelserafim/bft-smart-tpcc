package bftsmart.microbenchmark.tpcc.config;

public final class TPCCConstants {

    /**
     * <p>
     * For each WAREHOUSE there are 10 DISTRICT rows.
     * </p>
     * <code>tpc-c std = 10</code>
     */
    public static final int DIST_PER_WHSE = 10;

    public static final int LIMIT_ORDER = 2101;
    /**
     * <p>
     * Within each DISTRICT there are 3,000 CUSTOMERs.
     * </p>
     * <code>tpc-c std = 3,000</code>
     */
    public static final int CUST_PER_DIST = 3000;
    public static final int NB_MAX_ITEM = 100_000;

    public static final int C_LAST = 255;
    public static final int C_ID = 1023;
    public static final int OL_I_ID = 8191;
    public static final int MAX_C_LAST = 999;
    public static final int NB_MAX_CUSTOMER = 3000;

    private TPCCConstants() {
        super();
    }

}
