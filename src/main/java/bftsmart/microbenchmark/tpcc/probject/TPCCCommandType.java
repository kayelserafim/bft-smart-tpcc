package bftsmart.microbenchmark.tpcc.probject;

public enum TPCCCommandType {

    NEW_ORDER(1),
    PAYMENT(2),
    ORDER_STATUS(3),
    DELIVERY(4),
    STOCK_LEVEL(5);

    private final int value;

    TPCCCommandType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
