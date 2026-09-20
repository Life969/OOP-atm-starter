package practice.atm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHardwareTest {


    @Test
    @DisplayName("Передаем null вместо списка купюр")
    void rejectNull() {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{1, 1, 1, 1, 1});

        assertThrows(NullPointerException.class, () -> hardware.giveBills(null));

        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, hardware.currentCounts());
        assertEquals(0, hardware.giveCalls());

    }

    @Test
    @DisplayName("Неверная длина")
    void incorrectLength(){
        InMemoryHardware hardware = new InMemoryHardware(new int[]{1, 1, 1, 1, 1});

        assertThrows(IllegalArgumentException.class, () -> hardware.giveBills(new int[]{1, 1, 1}));

        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, hardware.currentCounts());
        assertEquals(0, hardware.giveCalls());
    }

    @Test
    @DisplayName("Отрицательное количество купюр")
    void incorrectCounts(){
        InMemoryHardware hardware = new InMemoryHardware(new int[]{1, 1, 1, 1, 1});

        assertThrows(IllegalArgumentException.class, () -> hardware.giveBills(new int[]{0, 1, -1, 1, 1}));

        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, hardware.currentCounts());
        assertEquals(0, hardware.giveCalls());
    }

    @Test
    @DisplayName("Не хватает купюр")
    void shortageOfBanknotes(){
        InMemoryHardware hardware = new InMemoryHardware(new int[]{1, 1, 1, 1, 1});

        assertThrows(IllegalArgumentException.class, () -> hardware.giveBills(new int[]{1, 1, 1, 1, 2}));

        assertArrayEquals(new int[]{1, 1, 1, 1, 1}, hardware.currentCounts());
        assertEquals(0, hardware.giveCalls());
    }

}
