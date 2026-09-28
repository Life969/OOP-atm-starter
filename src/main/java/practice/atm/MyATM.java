package practice.atm;

import practice.atm.atmExceptions.InvalidAmountException;

import java.util.Objects;

/**
 * Coordinates validation, local planning and the final hardware commit.
 */

/* Кассир - проверяет сумму, смотрит остатки, формирует заявку, и выдает */
public final class MyATM {

    static final int[] DENOMINATIONS = {50, 100, 500, 1000, 5000};

    private final Hardware hardware;


    public MyATM(Hardware hardware) {
        this.hardware = Objects.requireNonNull(hardware, "hardware");
    }

    public boolean withdraw(int amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("Сумма должна быть больше нуля");
        }
        if (amount % DENOMINATIONS[0] != 0) {
            throw new InvalidAmountException("Сумма для съема должна быть кратна наименьшей купюре");
        }
        int[] billsCounts = hardware.getBillsCounts(); // остаток купюр в банкомате
        int[] plan = new int[DENOMINATIONS.length]; // план
        int remaining = amount; // остаток суммы после вычитания

        for (int i = DENOMINATIONS.length - 1; i >= 0; i--) {
            int bills = remaining / DENOMINATIONS[i];
            int temp = Math.min(billsCounts[i], bills);
            plan[i] = temp;
            remaining -= temp * DENOMINATIONS[i];


        }

        if (remaining != 0) {
            return false; // Не смогли собрать сумму
        }

        WithdrawalPlan wp = WithdrawalPlan.of(DENOMINATIONS, plan);
        hardware.giveBills(wp.billCounts());
        return true;


    }
}
