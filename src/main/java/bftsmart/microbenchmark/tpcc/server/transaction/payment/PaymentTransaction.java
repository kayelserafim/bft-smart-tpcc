package bftsmart.microbenchmark.tpcc.server.transaction.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;

import com.google.inject.Inject;

import bftsmart.microbenchmark.tpcc.domain.CommandRequest;
import bftsmart.microbenchmark.tpcc.domain.CommandResponse;
import bftsmart.microbenchmark.tpcc.domain.TransactionType;
import bftsmart.microbenchmark.tpcc.server.repository.CustomerRepository;
import bftsmart.microbenchmark.tpcc.server.repository.DistrictRepository;
import bftsmart.microbenchmark.tpcc.server.repository.HistoryRepository;
import bftsmart.microbenchmark.tpcc.server.repository.WarehouseRepository;
import bftsmart.microbenchmark.tpcc.server.transaction.Transaction;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.input.PaymentInput;
import bftsmart.microbenchmark.tpcc.server.transaction.payment.output.PaymentOutput;
import bftsmart.microbenchmark.tpcc.table.Customer;
import bftsmart.microbenchmark.tpcc.table.District;
import bftsmart.microbenchmark.tpcc.table.History;
import bftsmart.microbenchmark.tpcc.table.Warehouse;
import bftsmart.microbenchmark.tpcc.util.Dates;

public class PaymentTransaction implements Transaction {

    @Inject
    private WarehouseRepository warehouseRepository;
    @Inject
    private DistrictRepository districtRepository;
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private HistoryRepository historyRepository;

    @Override
    public TransactionType transactionType() {
        return TransactionType.PAYMENT;
    }

    @Override
    public CommandResponse process(final CommandRequest command) {
        PaymentInput input = (PaymentInput) command;
        PaymentOutput paymentOutput = new PaymentOutput().withDateTime(LocalDateTime.now());

        Integer warehouseId = input.getWarehouseId();
        Integer districtId = input.getDistrictId();

        Integer customerWarehouseId = input.getCustomerWarehouseId();
        Integer customerDistrictId = input.getCustomerDistrictId();
        Customer customer;
        if (BooleanUtils.isTrue(input.getCustomerByName())) {
            // clause 2.6.2.2 (dot 3, Case 2)
            customer = customerRepository.find(input.getCustomerLastName(), districtId, warehouseId);
            if (customer == null) {
                String text = "C_LAST [%s] not found. D_ID [%s], W_ID [%s]";
                String msg = String.format(text, input.getCustomerLastName(), districtId, warehouseId);
                return new CommandResponse().withStatus(-1).withResponse(msg);
            }
        } else {
            // clause 2.6.2.2 (dot 3, Case 1)
            customer = customerRepository.find(input.getCustomerId(), districtId, warehouseId);
        }

        Warehouse warehouse = Warehouse.from(warehouseRepository.find(warehouseId))
                .addYearToDateBalance(BigDecimal.valueOf(input.getPaymentAmount()))
                .build();

        District district = District.from(districtRepository.find(districtId, warehouseId))
                .addYearToDateBalance(BigDecimal.valueOf(input.getPaymentAmount()))
                .build();

        String data = null;
        if ("BC".equals(customer.getCredit())) {
            data = new StringBuilder().append(input.getCustomerId())
                    .append(" ")
                    .append(customerDistrictId)
                    .append(" ")
                    .append(customerWarehouseId)
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
                .customerDistrictId(customerDistrictId)
                .customerWarehouseId(customerWarehouseId)
                .customerId(input.getCustomerId())
                .districtId(input.getDistrictId())
                .warehouseId(input.getWarehouseId())
                .date(Dates.now())
                .amount(BigDecimal.valueOf(input.getPaymentAmount()))
                .data(historyData)
                .build();

        warehouseRepository.save(warehouse);
        districtRepository.save(district);
        customerRepository.save(Customer.from(customer)
                .data(data)
                .paymentCntIncrement()
                .subtractBalance(BigDecimal.valueOf(input.getPaymentAmount()))
                .yearToDateBalancePayment(BigDecimal.valueOf(input.getPaymentAmount()))
                .build());
        historyRepository.save(history);

        paymentOutput.warehouse(warehouse)
                .district(district)
                .customer(customer)
                .withAmountPaid(input.getPaymentAmount());

        return new CommandResponse().withStatus(0).withResponse(outputScreen(paymentOutput));
    }

    private String outputScreen(PaymentOutput paymentOutput) {
        StringBuilder message = new StringBuilder();
        message.append("\n+---------------------------- PAYMENT ----------------------------+");
        message.append("\n Date: ").append(Dates.format(paymentOutput.getDateTime(), Dates.DATE_TIME_FORMAT));
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
                message.append("\n\n Cust-Data: ").append(customerData.substring(0, 50));
                int dataChunks = customerData.length() > 200 ? 4 : customerData.length() / 50;
                for (int n = 1; n < dataChunks; n++)
                    message.append("\n            ").append(customerData.substring(n * 50, (n + 1) * 50));
            } else {
                message.append("\n\n Cust-Data: ").append(customerData);
            }
        }
        message.append("\n+-----------------------------------------------------------------+\n\n");
        return message.toString();
    }

}
