package practice.atm;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyATMTest {

    @Test
    @DisplayName("constructor requires a hardware dependency")
    void constructorRequiresHardware() {
        assertThrows(NullPointerException.class, () -> new MyATM(null));
    }

    @Test
    @DisplayName("exact withdrawal updates state and returns true")
    void exactWithdrawalUpdatesState() {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{2, 2, 1, 1, 0});
        MyATM atm = new MyATM(hardware);

        assertTrue(atm.withdraw(650));

        assertArrayEquals(new int[]{1, 2, 0, 1, 0}, hardware.currentCounts());
        assertEquals(1, hardware.giveCalls());
    }

    @Test
    @DisplayName("one hardware read per withdrawal")
    void usesOneHardwareReadPerWithdrawal() {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{5, 5, 5, 5, 1});
        MyATM atm = new MyATM(hardware);

        assertTrue(atm.withdraw(5650));

        assertEquals(1, hardware.readCalls());
        assertEquals(1, hardware.giveCalls());
    }

    @Test
    @DisplayName("state stays unchanged when no denomination combination exists")
    void stateStaysUnchangedForImpossibleCombination() {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{0, 0, 1, 0, 0});
        MyATM atm = new MyATM(hardware);
        int[] before = hardware.currentCounts();

        assertFalse(atm.withdraw(100));

        assertArrayEquals(before, hardware.currentCounts());
        assertEquals(1, hardware.readCalls());
        assertEquals(0, hardware.giveCalls());
    }

    @Test
    @DisplayName("failed request does not prevent a later successful request")
    void failureDoesNotCorruptLaterState() {
        InMemoryHardware hardware = new InMemoryHardware(new int[]{0, 0, 1, 1, 0});
        MyATM atm = new MyATM(hardware);

        assertFalse(atm.withdraw(100));
        assertTrue(atm.withdraw(500));

        assertArrayEquals(new int[]{0, 0, 0, 1, 0}, hardware.currentCounts());
        assertEquals(2, hardware.readCalls());
        assertEquals(1, hardware.giveCalls());
    }
}
