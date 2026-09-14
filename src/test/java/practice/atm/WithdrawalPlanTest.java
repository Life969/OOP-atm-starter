package practice.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WithdrawalPlanTest {

    @Test
    @DisplayName("factory validates null and array shape")
    void validatesFactoryArguments() {
        assertThrows(NullPointerException.class, () -> WithdrawalPlan.of(null, new int[5]));
        assertThrows(NullPointerException.class, () -> WithdrawalPlan.of(new int[5], null));
        assertThrows(
                IllegalArgumentException.class,
                () -> WithdrawalPlan.of(new int[]{50, 100}, new int[]{1})
        );
    }

    @Test
    @DisplayName("defensive copies protect mutable input arrays")
    void defensiveCopiesProtectInputs() {
        int[] denominations = {50, 100, 500, 1000, 5000};
        int[] counts = {1, 2, 0, 1, 0};

        WithdrawalPlan plan = WithdrawalPlan.of(denominations, counts);
        denominations[0] = 1;
        counts[0] = 99;

        assertArrayEquals(new int[]{1, 2, 0, 1, 0}, plan.billCounts());
        assertEquals(1250, plan.totalAmount());
    }

    @Test
    @DisplayName("defensive accessor copy cannot mutate the value object")
    void defensiveAccessorCopyCannotMutatePlan() {
        WithdrawalPlan plan = WithdrawalPlan.of(
                new int[]{50, 100, 500, 1000, 5000},
                new int[]{0, 1, 1, 0, 0}
        );

        int[] exposed = plan.billCounts();
        exposed[1] = 99;

        assertArrayEquals(new int[]{0, 1, 1, 0, 0}, plan.billCounts());
        assertEquals(600, plan.totalAmount());
    }

    @Test
    @DisplayName("negative bill count is outside the value-object contract")
    void rejectsNegativeBillCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> WithdrawalPlan.of(
                        new int[]{50, 100, 500, 1000, 5000},
                        new int[]{0, -1, 0, 0, 0}
                )
        );
    }
}
