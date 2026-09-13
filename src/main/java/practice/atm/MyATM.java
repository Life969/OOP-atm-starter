package practice.atm;

import java.util.Objects;

/** Coordinates validation, local planning and the final hardware commit. */

/* Кассир - проверяет сумму, смотрит остатки, формирует заявку, и выдает */
public final class MyATM {

    static final int[] DENOMINATIONS = {50, 100, 500, 1000, 5000};

    private final Hardware hardware;

    public MyATM(Hardware hardware) {
        this.hardware = Objects.requireNonNull(hardware, "hardware");
    }

    public boolean withdraw(int amount) {
        // TODO(студент): отклонить некорректную сумму до обращения к аппаратной части.
        // TODO(студент): прочитать один снимок состояния и построить локальный план от крупных купюр к мелким.
        // TODO(студент): вызвать giveBills только после того, как полная сумма будет распланирована.
        throw new UnsupportedOperationException("Student task");
    }
}
