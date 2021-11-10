package bftsmart.microbenchmark.tpcc.server.transaction.payment;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.exception.NotFoundException;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommand;
import bftsmart.microbenchmark.tpcc.probject.TPCCCommandType;
import bftsmart.microbenchmark.tpcc.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.repository.HistoryRepository;
import bftsmart.microbenchmark.tpcc.repository.WarehouseRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.output.PaymentOutput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.util.Times;

public class PaymentTransaction implements Transaction {

    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private WarehouseRepository warehouseRepository;
    @Inject
    private DistrictRepository districtRepository;
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private HistoryRepository historyRepository;

    @Override
    public TPCCCommandType commandType() {
        return TPCCCommandType.PAYMENT;
    }

    @Override
    public TPCCCommand process(final TPCCCommand aRequest) {
        Map<String, Serializable> params = aRequest.getRequest();

        PaymentOutput.Builder paymentBuilder = PaymentOutput.builder().dateTime(LocalDateTime.now());
        PaymentInput input = objectMapper.convertValue(params, PaymentInput.class);

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();

        Warehouse warehouse = warehouseRepository.find(warehouseId)
                .map(Warehouse::from)
                .map(builder -> builder.addYearToDateBalance(input.getPaymentAmount()))
                .map(Warehouse.Builder::build)
                .map(warehouseRepository::save)
                .orElseThrow(() -> new NotFoundException("Warehouse %s not found", warehouseId));

        District district = districtRepository.find(districtId, warehouseId)
                .map(District::from)
                .map(builder -> builder.addYearToDateBalance(input.getPaymentAmount()))
                .map(District.Builder::build)
                .map(districtRepository::save)
                .orElseThrow(() -> new NotFoundException("District %s not found", districtId));

        Customer customer;
        if (BooleanUtils.isTrue(input.getCustomerByName())) {
            // clause 2.6.2.2 (dot 3, Case 2)
            customer = customerRepository.findBy(input.getCustomerName(), districtId, warehouseId);
            if (customer == null) {
                String msg = "Customer [%s] not found. D_ID [%s], W_ID [%s]";
                return TPCCCommand.newErrorMessage(aRequest, msg, input.getCustomerName(), districtId, warehouseId);
            }
        } else {
            // clause 2.6.2.2 (dot 3, Case 1)
            customer = customerRepository.findBy(input.getCustomerId(), districtId, warehouseId);
        }
        if ("BC".equals(customer.getCredit())) {
            String data = new StringBuilder().append(input.getCustomerId())
                    .append(" ")
                    .append(input.getCustomerDistrictId())
                    .append(" ")
                    .append(input.getCustomerWarehouseId())
                    .append(" ")
                    .append(input.getDistrictId())
                    .append(" ")
                    .append(input.getWarehouseId())
                    .append(" ")
                    .append(input.getPaymentAmount())
                    .append(" |")
                    .toString();
            if (customer.getData().length() > data.length()) {
                data += customer.getData().substring(0, customer.getData().length() - data.length());
            } else {
                data += customer.getData();
            }
            if (data.length() > 500) {
                data = data.substring(0, 500);
            }
            customerRepository.save(Customer.from(customer).data(data).addBalance(input.getPaymentAmount()).build());
        }
        String warehouseName = warehouse.getName();
        if (warehouseName.length() > 10) {
            warehouseName = warehouseName.substring(0, 10);
        }
        String districtName = district.getName();
        if (districtName.length() > 10) {
            districtName = districtName.substring(0, 10);
        }
        String historyData = warehouseName + "    " + districtName;

        History history = History.builder()
                .customerDistrictId(input.getCustomerDistrictId())
                .customerWarehouseId(input.getCustomerWarehouseId())
                .customerId(input.getCustomerId())
                .districtId(input.getDistrictId())
                .warehouseId(input.getWarehouseId())
                .date(Times.currentTimeMillis())
                .amount(input.getPaymentAmount())
                .data(historyData)
                .build();

        historyRepository.save(history);

        paymentBuilder.warehouse(warehouse).district(district).customer(customer).amountPaid(input.getPaymentAmount());

        return TPCCCommand.newSuccessMessage(aRequest, outputScreen(paymentBuilder.build()));
    }

    private String outputScreen(PaymentOutput paymentOutput) {
        StringBuilder message = new StringBuilder();
        message.append("\n+---------------------------- PAYMENT ----------------------------+");
        message.append("\n Date: " + paymentOutput.getDateTime().format(Times.DATE_TIME_FORMAT));
        message.append("\n\n Warehouse: ");
        message.append(paymentOutput.getWarehouseId());
        message.append("\n   Street 1(W):  ");
        message.append(paymentOutput.getWarehouseStreet1());
        message.append("\n   Street 2(W):  ");
        message.append(paymentOutput.getWarehouseStreet2());
        message.append("\n   City(W):    ");
        message.append(paymentOutput.getWarehouseCity());
        message.append("   State(W): ");
        message.append(paymentOutput.getWarehouseState());
        message.append("  Zip(W): ");
        message.append(paymentOutput.getWarehouseZip());
        message.append("\n\n District:  ");
        message.append(paymentOutput.getDistrictId());
        message.append("\n   Street 1(D):  ");
        message.append(paymentOutput.getDistrictStreet1());
        message.append("\n   Street 2(D):  ");
        message.append(paymentOutput.getDistrictStreet2());
        message.append("\n   City(D):    ");
        message.append(paymentOutput.getDistrictCity());
        message.append("   State(D): ");
        message.append(paymentOutput.getDistrictState());
        message.append("  Zip(D): ");
        message.append(paymentOutput.getDistrictZip());
        message.append("\n\n Customer:  ");
        message.append(paymentOutput.getCustomerId());
        message.append("\n   Name:    ");
        message.append(paymentOutput.getCustomerFirst());
        message.append(" ");
        message.append(paymentOutput.getCustomerMiddle());
        message.append(" ");
        message.append(paymentOutput.getCustomerLast());
        message.append("\n   Street 1(C):  ");
        message.append(paymentOutput.getCustomerStreet1());
        message.append("\n   Street 2(C):  ");
        message.append(paymentOutput.getCustomerStreet2());
        message.append("\n   City(C):    ");
        message.append(paymentOutput.getCustomerCity());
        message.append("   State(C): ");
        message.append(paymentOutput.getCustomerState());
        message.append("  Zip(C): ");
        message.append(paymentOutput.getCustomerZip());
        message.append("\n   Since:   ");
        message.append(paymentOutput.getCustomerSince());
        message.append("\n   Credit:  ");
        message.append(paymentOutput.getCustomerCredit());
        message.append("\n   %Disc:   ");
        message.append(paymentOutput.getCustomerDiscount());
        message.append("\n   Phone:   ");
        message.append(paymentOutput.getCustomerPhone());
        message.append("\n\n Amount Paid:      ");
        message.append(paymentOutput.getAmountPaid());
        message.append("\n Credit Limit:     ");
        message.append(paymentOutput.getCustomerCreditLimit());
        message.append("\n New Cust-Balance: ");
        message.append(paymentOutput.getCustomerBalance());
        if ("BC".equals(paymentOutput.getCustomerCredit())) {
            String customerData = paymentOutput.getCustomerData();
            if (customerData.length() > 40) {
                message.append("\n\n Cust-Data: " + customerData.substring(0, 50));
                int dataChunks = customerData.length() > 200 ? 4 : customerData.length() / 50;
                for (int n = 1; n < dataChunks; n++)
                    message.append("\n            " + customerData.substring(n * 50, (n + 1) * 50));
            } else {
                message.append("\n\n Cust-Data: " + customerData);
            }
        }
        message.append("\n+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
