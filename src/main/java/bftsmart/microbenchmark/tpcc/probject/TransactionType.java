package bftsmart.microbenchmark.tpcc.probject;

public enum TransactionType {

    NEW_ORDER(1),
    PAYMENT(2),
    ORDER_STATUS(3),
    DELIVERY(4),
    STOCK_LEVEL(5);

    private final int classId;

    TransactionType(final int classId) {
        this.classId = classId;
    }

    public int getClassId() {
        return classId;
    }

}