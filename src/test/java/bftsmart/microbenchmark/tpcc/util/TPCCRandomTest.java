package bftsmart.microbenchmark.tpcc.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class TPCCRandomTest {

    @Test
    void testGetCLastIntWithNumber371() {
        // Given
        Integer aNumber = 371;

        // When
        String actual = new TPCCRandom().getCLast(aNumber);

        // Then
        assertThat(actual).isEqualTo("PRICALLYOUGHT");
    }

    @Test
    void testGetCLastIntWithNumber40() {
        // Given
        Integer aNumber = 40;

        // When
        String actual = new TPCCRandom().getCLast(aNumber);

        // Then
        assertThat(actual).isEqualTo("BARPRESBAR");
    }

}
