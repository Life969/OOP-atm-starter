package practice.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EquivalenceClassesTest {

    @ParameterizedTest(name = "invalid amount {0}")
    @ValueSource(ints = {Integer.MIN_VALUE, -50, -1, 0, 1, 49, 51, 75})
    @DisplayName("invalid amount is rejected before hardware access")
    void invalidAmountIsRejectedBeforeHardware(int amount) {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{1, 1, 1, 1, 1});
        MyATM atm = new MyATM(hardware);

        assertThrows(IllegalArgumentException.class, () -> atm.withdraw(amount));
        assertEquals(0, hardware.readCalls());
        assertEquals(0, hardware.giveCalls());
    }

    @ParameterizedTest(name = "amount {0}")
    @MethodSource("successfulWithdrawals")
    @DisplayName("representatives of successful equivalence classes")
    void representativeSuccessfulWithdrawals(
            int amount,
            int[] initialCounts,
            int[] expectedCounts
    ) {
        InMemoryHardware hardware = new InMemoryHardware(initialCounts);
        MyATM atm = new MyATM(hardware);

        assertTrue(atm.withdraw(amount));

        assertArrayEquals(expectedCounts, hardware.currentCounts());
    }

    static Stream<Arguments> successfulWithdrawals() {
        return Stream.of(
                Arguments.of(50, new int[]{1, 0, 0, 0, 0}, new int[]{0, 0, 0, 0, 0}),
                Arguments.of(600, new int[]{0, 1, 1, 0, 0}, new int[]{0, 0, 0, 0, 0}),
                Arguments.of(5650, new int[]{1, 1, 1, 0, 1}, new int[]{0, 0, 0, 0, 0})
        );
    }

    @Test
    @DisplayName("student adds one new class before declaring the suite complete")
    void addOneNewEquivalenceClass() {
        // STUDENT_TASK: replace this representative with a class not covered above.
        InMemoryHardware hardware = new InMemoryHardware(new int[]{2, 0, 0, 0, 0});
        MyATM atm = new MyATM(hardware);

        assertTrue(atm.withdraw(100));
        assertArrayEquals(new int[]{0, 0, 0, 0, 0}, hardware.currentCounts());
    }
}
