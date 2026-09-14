package practice.atm;

/**
 * Port to the ATM hardware. Arrays always use the order
 * [50, 100, 500, 1000, 5000].
 */
public interface Hardware {

    /** Returns a snapshot of available bill counts. */ // показать список остатков
    int[] getBillsCounts();

    /** Atomically moves the selected bills to the dispensing box. */ // Выдать/списать купюры
    void giveBills(int[] billsCounts);
}
