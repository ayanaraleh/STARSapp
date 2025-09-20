package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestReservation {
    private Reservation r1;
    private Telescope t1;
    private Telescope t2;
    private TelescopeType type1;
    private TelescopeType type2;

    @BeforeEach
    public void runBefore() {
        type1 = TelescopeType.REFRACTOR;
        type2 = TelescopeType.MAKSUTOV_CASSEGRAIN;
        t1 = new Telescope("Celestron 200", type1);
        t2 = new Telescope("SkyWatcher 1800", type2);
        r1 = new Reservation("Hadfield", t1);
    }

    @Test
    public void testConstructor() {
        assertEquals("Hadfield", r1.getUserName());
        assertEquals(t1, r1.getTelescope());
        assertTrue(r1.getReservationStatus());
    }

    @Test
    // check: reservation id's are unique
    public void testUniqueId() {
        Reservation r2 = new Reservation("Chris", t2);
        assertNotEquals(r1.getReservationId(), r2.getReservationId());

    }

    @Test
    public void testCancelReservation() {
        r1.cancelReservation();
        assertFalse(r1.getReservationStatus());
        assertTrue(r1.getTelescope().getAvailabilityStatus());

    }

    @Test
    // check: cancel reservation twice
    // outcome: nothing chances after 2nd cancellation
    public void testCancelReservationTwice() {
        r1.cancelReservation();
        r1.cancelReservation();
        assertFalse(r1.getReservationStatus());
        assertTrue(r1.getTelescope().getAvailabilityStatus());

    }

    @Test
    public void testToJson() {
        JSONObject json = r1.toJson();

        assertTrue(json.has("user"));
        assertTrue(json.has("telescope"));

        assertEquals("Hadfield", json.getString("user"));
        assertEquals("Celestron 200", json.getString("telescope"));
    }

    

}
