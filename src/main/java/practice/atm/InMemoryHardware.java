package practice.atm;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Observable in-memory hardware for tests and local experiments.
 *
 * <p>It is intentionally small: production concerns stay behind Hardware.
 */

/* Здесь хранится массив остатков */
public final class InMemoryHardware implements Hardware {

    private final int[] counts;
    private final AtomicInteger readCalls = new AtomicInteger();
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
        readCalls.incrementAndGet();
        return counts.clone();
    }

    @Override
    public void giveBills(int[] billsCounts) {


        synchronized (this) {

            if (billsCounts == null) {
                throw new IllegalArgumentException("список купюр пуст");
            }

            if (billsCounts.length != counts.length) {
                throw new IllegalArgumentException("списки количества купюр не равны");
            }

            if (Arrays.stream(billsCounts).anyMatch(count -> count < 0)) {
                throw new IllegalArgumentException("Количество купюр не может быть отрицательным," +
                        " в списке есть отрицательное число");
            }

            if (IntStream.range(0, billsCounts.length)
                    .anyMatch(i -> billsCounts[i] > counts[i])){
                throw new IllegalArgumentException("Не хватает купюр");
            }

                for (int i = 0; i < billsCounts.length; i++) {
                    counts[i] -= billsCounts[i];
                }

                giveCalls++;

        }

        // TODO(студент): полностью провалидировать команду перед изменением любого количества.
        // TODO(студент): атомарно вычесть выбранные купюры и увеличить giveCalls.
    }

    public int[] currentCounts() {
        return counts.clone();
    }

    public int readCalls() {
        return readCalls.get();
    }

    public int giveCalls() {
        return giveCalls;
    }
}