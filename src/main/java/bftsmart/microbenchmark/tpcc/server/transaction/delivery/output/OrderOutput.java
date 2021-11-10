package bftsmart.microbenchmark.tpcc.server.transaction.delivery.output;

public class OrderOutput {

    private final Integer districtId;
    private final Integer orderId;

    public OrderOutput(Integer districtId, Integer orderId) {
        super();
        this.districtId = districtId;
        this.orderId = orderId;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("OrderOutput [districtId=").append(districtId).append(", orderId=").append(orderId).append(']');
        return builder.toString();
    }

}
