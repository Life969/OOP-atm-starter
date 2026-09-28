package practice.atm;

import practice.atm.atmExceptions.InsufficientBillsException;
import practice.atm.atmExceptions.InvalidBillsException;
import practice.atm.atmExceptions.InvalidPlanException;

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
            throw new InvalidBillsException("Expected one count per denomination");
        }
        if (Arrays.stream(initialCounts).anyMatch(count -> count < 0)) {
            throw new InvalidBillsException("Bill count cannot be negative");
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
                throw new NullPointerException("список купюр пуст");
            }

            if (billsCounts.length != counts.length) {
                throw new InvalidBillsException("списки количества купюр не равны");
            }

            if (Arrays.stream(billsCounts).anyMatch(count -> count < 0)) {
                throw new InvalidBillsException("Количество купюр не может быть отрицательным," +
                        " в списке есть отрицательное число");
            }

            if (IntStream.range(0, billsCounts.length)
                    .anyMatch(i -> billsCounts[i] > counts[i])){
                throw new InsufficientBillsException("Не хватает купюр");
            }

                for (int i = 0; i < billsCounts.length; i++) {
                    counts[i] -= billsCounts[i];
                }

                giveCalls++;

        }

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