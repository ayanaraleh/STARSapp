package model;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestReservationManager {
    private ReservationManager rm;
    private Telescope t1;
    private Telescope t2;

    @BeforeEach
    public void runBefore() {
        rm = new ReservationManager();
        TelescopeType type1 = TelescopeType.REFRACTOR;
        TelescopeType type2 = TelescopeType.MAKSUTOV_CASSEGRAIN;
        t1 = new Telescope("Celestron 200", type1);
        t2 = new Telescope("SkyWatcher 1800", type2);
    }

    @Test
    // test list of Reservation constructed as empty
    public void testConstructor() {
        assertEquals(0, rm.getReservations().size());

    }

    @Test
    public void testMake1ValidReservation() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        assertNotNull(r1);
        assertFalse(r1.getTelescope().getAvailabilityStatus());
        assertEquals(1, rm.getReservations().size());

    }

    @Test
    public void testMake2ValidReservations() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        Reservation r2 = rm.makeReservation("Chris", t2);
        assertNotNull(r1);
        assertNotNull(r2);
        assertFalse(r1.getTelescope().getAvailabilityStatus());
        assertFalse(r2.getTelescope().getAvailabilityStatus());
        assertEquals(2, rm.getReservations().size());

    }

    @Test
    // test for reserving an unavailable scope
    // outcome: should not make the reservation
    public void testInValidReservation() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        Reservation r2 = rm.makeReservation("Chris", t1);
        assertNotNull(r1);
        assertNull(r2);
        assertFalse(r1.getTelescope().getAvailabilityStatus());
        assertEquals(1, rm.getReservations().size());

    }

    @Test
    public void test1CancelReservation() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        assertNotNull(r1);
        assertTrue(rm.cancelReservation(r1.getReservationId()));
        assertTrue(r1.getTelescope().getAvailabilityStatus());
        assertEquals(0, rm.getReservations().size());
    }

    @Test
    // test for non-existing reservation id
    // outcome: should return false
    public void testCancelReservationDne() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        assertFalse(rm.cancelReservation(r1.getReservationId() + 1));
        assertEquals(1, rm.getReservations().size());
    }

    @Test
    // test for booking a canceled telescope
    // outcome: should make reservation successfully
    public void testCancelThenBookReservations() {
        Reservation r1 = rm.makeReservation("Hadfield", t1);
        rm.cancelReservation(r1.getReservationId());
        Reservation r2 = rm.makeReservation("Chris", t1);
        assertFalse(r2.getTelescope().getAvailabilityStatus());
        assertEquals(1, rm.getReservations().size());
    }
}
