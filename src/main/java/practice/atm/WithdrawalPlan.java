package practice.atm;


/**
 * Неизменяемый value object, описывающий одно полное снятие наличных.
 *
 * <p>Задача студента: выполнить валидацию, защитные копии и вычисление суммы.
 */

/* Здесь составляется заявка на выдачу (номинал и количество купюр)*/
public final class WithdrawalPlan {

    private final int[] denominations;
    private final int[] billCounts;

    private WithdrawalPlan(int[] denominations, int[] billCounts) {


        this.denominations = denominations;
        this.billCounts = billCounts;
    }

    public static WithdrawalPlan of(int[] denominations, int[] billCounts) {

        if (denominations == null || billCounts == null) {
            throw new NullPointerException("Список номиналов " +
                    "или список оставшихся купюр не может быть пустым");
        }

        if (denominations.length != billCounts.length) {
            throw new IllegalArgumentException("Количество списка номиналов не совпадает" +
                    " с количеством списка оставшихся купюр");
        }


        for (int i = 0; i < denominations.length; i++) {
            if (denominations[i] <= 0) {
                throw new IllegalArgumentException("Номинал не должен быть больше 0");
            }

            if (billCounts[i] < 0) {
                throw new IllegalArgumentException("Количество купюр должно быть положительным");
            }

        }


        // TODO(студент): провалидировать оба массива, их форму, номиналы и количества.
        // TODO(студент): обеспечить, чтобы изменяемые входные данные не могли изменить созданное значение.
        return new WithdrawalPlan(denominations, billCounts);
    }

    public int[] billCounts() {

        return billCounts.clone();
        // TODO(студент): не раскрывать изменяемое внутреннее состояние.
    }

    public int totalAmount() {
        int total = 0;
        for (int i = 0; i < denominations.length; i++) {
            total += denominations[i] * billCounts[i];
        }
        return total;

        // TODO(студент): вычислить денежную сумму, представленную этим планом.
    }
}
