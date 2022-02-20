package bftsmart.microbenchmark.tpcc.server.transaction.delivery.output;

import java.util.StringJoiner;

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
        return new StringJoiner(", ", OrderOutput.class.getSimpleName() + "[", "]")
                .add("districtId=" + districtId)
                .add("orderId=" + orderId)
                .toString();
    }
}
