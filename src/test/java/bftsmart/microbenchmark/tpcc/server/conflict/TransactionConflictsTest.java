package bftsmart.microbenchmark.tpcc.server.conflict;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import bftsmart.microbenchmark.tpcc.domain.TransactionType;

class TransactionConflictsTest {

    @Test
    void testHasConflictNewOrderWithNewOrder() {
        // Given
        TransactionType newOrder1 = TransactionType.NEW_ORDER;
        TransactionType newOrder2 = TransactionType.NEW_ORDER;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictNewOrderWithPayment() {
        // Given
        TransactionType newOrder1 = TransactionType.NEW_ORDER;
        TransactionType newOrder2 = TransactionType.PAYMENT;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictPaymentWithNewOrder() {
        // Given
        TransactionType newOrder1 = TransactionType.PAYMENT;
        TransactionType newOrder2 = TransactionType.NEW_ORDER;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasPossibleConflictNewOrderWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.NEW_ORDER;
        TransactionType newOrder2 = TransactionType.ORDER_STATUS;

        // When
        boolean actual = TransactionConflicts.hasPossibleConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasPossibleConflictOrderStatusWithNewOrder() {
        // Given
        TransactionType newOrder1 = TransactionType.ORDER_STATUS;
        TransactionType newOrder2 = TransactionType.NEW_ORDER;

        // When
        boolean actual = TransactionConflicts.hasPossibleConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictNewOrderWithDelivery() {
        // Given
        TransactionType newOrder1 = TransactionType.NEW_ORDER;
        TransactionType newOrder2 = TransactionType.DELIVERY;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictDeliveryWithNewOrder() {
        // Given
        TransactionType newOrder1 = TransactionType.DELIVERY;
        TransactionType newOrder2 = TransactionType.NEW_ORDER;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictNewOrderWithStockLevel() {
        // Given
        TransactionType newOrder1 = TransactionType.NEW_ORDER;
        TransactionType newOrder2 = TransactionType.STOCK_LEVEL;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictStockLevelWithNewOrder() {
        // Given
        TransactionType newOrder1 = TransactionType.STOCK_LEVEL;
        TransactionType newOrder2 = TransactionType.NEW_ORDER;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasPossibleConflictPaymentWithPayment() {
        // Given
        TransactionType newOrder1 = TransactionType.PAYMENT;
        TransactionType newOrder2 = TransactionType.PAYMENT;

        // When
        boolean actual = TransactionConflicts.hasPossibleConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasPossibleConflictPaymentWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.PAYMENT;
        TransactionType newOrder2 = TransactionType.ORDER_STATUS;

        // When
        boolean actual = TransactionConflicts.hasPossibleConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasPossibleConflictOrderStatusWithPayment() {
        // Given
        TransactionType newOrder1 = TransactionType.ORDER_STATUS;
        TransactionType newOrder2 = TransactionType.PAYMENT;

        // When
        boolean actual = TransactionConflicts.hasPossibleConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictPaymentWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.PAYMENT;
        TransactionType newOrder2 = TransactionType.DELIVERY;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictOrderStatusWithPayment() {
        // Given
        TransactionType newOrder1 = TransactionType.DELIVERY;
        TransactionType newOrder2 = TransactionType.PAYMENT;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictPaymentWithStockLevel() {
        // Given
        TransactionType newOrder1 = TransactionType.PAYMENT;
        TransactionType newOrder2 = TransactionType.STOCK_LEVEL;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictStockLevelWithPayment() {
        // Given
        TransactionType newOrder1 = TransactionType.STOCK_LEVEL;
        TransactionType newOrder2 = TransactionType.PAYMENT;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictOrderStatusWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.ORDER_STATUS;
        TransactionType newOrder2 = TransactionType.ORDER_STATUS;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictOrderStatusWithDelivery() {
        // Given
        TransactionType newOrder1 = TransactionType.ORDER_STATUS;
        TransactionType newOrder2 = TransactionType.DELIVERY;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictDeliveryWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.DELIVERY;
        TransactionType newOrder2 = TransactionType.ORDER_STATUS;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictOrderStatusWithStockLevel() {
        // Given
        TransactionType newOrder1 = TransactionType.ORDER_STATUS;
        TransactionType newOrder2 = TransactionType.STOCK_LEVEL;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictStockLevelWithOrderStatus() {
        // Given
        TransactionType newOrder1 = TransactionType.STOCK_LEVEL;
        TransactionType newOrder2 = TransactionType.ORDER_STATUS;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasConflictDeliveryWithDelivery() {
        // Given
        TransactionType newOrder1 = TransactionType.DELIVERY;
        TransactionType newOrder2 = TransactionType.DELIVERY;

        // When
        boolean actual = TransactionConflicts.hasConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictDeliveryWithStockLevel() {
        // Given
        TransactionType newOrder1 = TransactionType.DELIVERY;
        TransactionType newOrder2 = TransactionType.STOCK_LEVEL;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictStockLevelWithDelivery() {
        // Given
        TransactionType newOrder1 = TransactionType.STOCK_LEVEL;
        TransactionType newOrder2 = TransactionType.DELIVERY;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

    @Test
    void testHasNotConflictStockLevelWithStockLevel() {
        // Given
        TransactionType newOrder1 = TransactionType.STOCK_LEVEL;
        TransactionType newOrder2 = TransactionType.STOCK_LEVEL;

        // When
        boolean actual = TransactionConflicts.hasNotConflict(newOrder1.getClassId(), newOrder2.getClassId());

        // Then
        assertThat(actual).isTrue();
    }

}
