package persistence;

import model.Reservation;
import model.ReservationManager;
import model.Telescope;
import model.TelescopeType;
import model.TelescopeCatalogue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

// NOTE: Implementation was done prior to testing to verify demo functionality
// As Reader is not implemented in any model classes, testing was only done for JSON files
public class TestReservationJsonReader {
    private static final String TEST_FILE = "./data/test_reservation_reader.json";
    private static final String INVALID_TELESCOPE_FILE = "./data/test_reservation_dne.json";
    private static final String EMPTY_FILE = "./data/test_reservation_empty.json";

    private TelescopeCatalogue tc;
    private ReservationJsonReader reader;

    @BeforeEach
    void setUp() {
        tc = new TelescopeCatalogue();
        tc.addTelescope(new Telescope("Celestron NexStar 8SE", TelescopeType.REFRACTOR));
        tc.addTelescope(new Telescope("Orion SkyQuest XT10", TelescopeType.REFLECTOR));
    }

    @Test
    void testReadValidReservations() {
        try {
            reader = new ReservationJsonReader(TEST_FILE, tc);
            ReservationManager rm = reader.read();
            assertEquals(2, rm.getReservations().size());

            Reservation r1 = rm.getReservations().get(0);
            assertEquals("Alice", r1.getUserName());
            assertEquals("Celestron NexStar 8SE", r1.getTelescope().getTelescopeName());

            Reservation r2 = rm.getReservations().get(1);
            assertEquals("Bob", r2.getUserName());
            assertEquals("Orion SkyQuest XT10", r2.getTelescope().getTelescopeName());

        } catch (IOException e) {
            fail("Failed to read from test file.");
        }
    }

    @Test
    void testReadInvalidTelescopeReservation() {
        try {
            reader = new ReservationJsonReader(INVALID_TELESCOPE_FILE, tc);
            ReservationManager rm = reader.read();
            assertEquals(0, rm.getReservations().size());

        } catch (IOException e) {
            fail("Failed to read from test file.");
        }
    }

    @Test
    void testReadEmptyReservations() {
        try {
            reader = new ReservationJsonReader(EMPTY_FILE, tc);
            ReservationManager rm = reader.read();
            assertEquals(0, rm.getReservations().size());

        } catch (IOException e) {
            fail("Failed to read from test file.");
        }
    }

}
