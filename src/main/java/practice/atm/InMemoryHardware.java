package practice.atm;

import java.util.Arrays;
import java.util.Objects;

/**
 * Observable in-memory hardware for tests and local experiments.
 *
 * <p>It is intentionally small: production concerns stay behind Hardware.
 */

/* Здесь хранится массив остатков */
public final class InMemoryHardware implements Hardware {

    private final int[] counts;
    private int readCalls;
    private int giveCalls;

    public InMemoryHardware(int[] initialCounts) {
        Objects.requireNonNull(initialCounts, "initialCounts");
        if (initialCounts.length != MyATM.DENOMINATIONS.length) {
            throw new IllegalArgumentException("Expected one count per denomination");
        }
        if (Arrays.stream(initialCounts).anyMatch(count -> count < 0)) {
            throw new IllegalArgumentException("Bill count cannot be negative");
        }
        this.counts = initialCounts.clone();
    }

    @Override
    public int[] getBillsCounts() {
        readCalls += 1;
        return counts.clone();
    }

    @Override
    public void giveBills(int[] billsCounts) {
        // TODO(студент): полностью провалидировать команду перед изменением любого количества.
        // TODO(студент): атомарно вычесть выбранные купюры и увеличить giveCalls.
        throw new UnsupportedOperationException("Задача студента");
    }

    public int[] currentCounts() {
        return counts.clone();
    }

    public int readCalls() {
        return readCalls;
    }

    public int giveCalls() {
        return giveCalls;
    }
}