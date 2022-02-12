package bftsmart.microbenchmark.tpcc.server.transaction.neworder.input;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewOrderInput implements Serializable {

    private static final long serialVersionUID = -5998555582667194482L;

    @JsonProperty("w_id")
    private Integer warehouseId;
    @JsonProperty("d_id")
    private Integer districtId;
    @JsonProperty("c_id")
    private Integer customerId;
    @JsonProperty("ol_o_cnt")
    private Integer orderLineCnt;
    @JsonProperty("o_all_local")
    private Integer orderAllLocal;
    @JsonProperty("itemIds")
    private List<Integer> itemIds;
    @JsonProperty("supplierWarehouseIDs")
    private List<Integer> supplierWarehouseIds;
    @JsonProperty("orderQuantities")
    private List<Integer> orderQuantities;

    public Integer getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
    }

    public NewOrderInput withWarehouseId(Integer warehouseId) {
        setWarehouseId(warehouseId);
        return this;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public NewOrderInput withDistrictId(Integer districtId) {
        setDistrictId(districtId);
        return this;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public NewOrderInput withCustomerId(Integer customerId) {
        setCustomerId(customerId);
        return this;
    }

    public Integer getOrderLineCnt() {
        return orderLineCnt;
    }

    public void setOrderLineCnt(Integer orderLineCnt) {
        this.orderLineCnt = orderLineCnt;
    }

    public NewOrderInput withOrderLineCnt(Integer orderLineCnt) {
        setOrderLineCnt(orderLineCnt);
        return this;
    }

    public Integer getOrderAllLocal() {
        return orderAllLocal;
    }

    public void setOrderAllLocal(Integer orderAllLocal) {
        this.orderAllLocal = orderAllLocal;
    }

    public NewOrderInput withOrderAllLocal(Integer orderAllLocal) {
        setOrderAllLocal(orderAllLocal);
        return this;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }

    public NewOrderInput withItemIds(List<Integer> itemIds) {
        setItemIds(itemIds);
        return this;
    }

    public List<Integer> getSupplierWarehouseIds() {
        return supplierWarehouseIds;
    }

    public void setSupplierWarehouseIds(List<Integer> supplierWarehouseIds) {
        this.supplierWarehouseIds = supplierWarehouseIds;
    }

    public NewOrderInput withSupplierWarehouseIds(List<Integer> supplierWarehouseIds) {
        setSupplierWarehouseIds(supplierWarehouseIds);
        return this;
    }

    public List<Integer> getOrderQuantities() {
        return orderQuantities;
    }

    public void setOrderQuantities(List<Integer> orderQuantities) {
        this.orderQuantities = orderQuantities;
    }

    public NewOrderInput withOrderQuantities(List<Integer> orderQuantities) {
        setOrderQuantities(orderQuantities);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("NewOrderInput [warehouseId=")
                .append(warehouseId)
                .append(", districtId=")
                .append(districtId)
                .append(", customerId=")
                .append(customerId)
                .append(", orderLineCnt=")
                .append(orderLineCnt)
                .append(", orderAllLocal=")
                .append(orderAllLocal)
                .append(", itemIds=")
                .append(itemIds)
                .append(", supplierWarehouseIds=")
                .append(supplierWarehouseIds)
                .append(", orderQuantities=")
                .append(orderQuantities)
                .append(']');
        return builder.toString();
    }

}
