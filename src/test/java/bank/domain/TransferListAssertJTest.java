package bank.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// 7. AssertJ list assertions
class TransferListAssertJTest {

    @Test
    void shouldCheckTransferListWithAssertJ() {
        List<Transfer> transfers = List.of(
                new Transfer("A1", "A2", new BigDecimal("100"), "SUCCESS"),
                new Transfer("A1", "A3", new BigDecimal("200"), "SUCCESS"),
                new Transfer("A4", "A2", new BigDecimal("50"), "FAILED")
        );

        assertThat(transfers)
                .hasSize(3)
                .extracting(Transfer::getStatus)
                .contains("SUCCESS", "FAILED");

        assertThat(transfers)
                .extracting(Transfer::getFromId)
                .containsExactly("A1", "A1", "A4");

        assertThat(transfers)
                .filteredOn(t -> t.getStatus().equals("SUCCESS"))
                .hasSize(2);
    }
}
