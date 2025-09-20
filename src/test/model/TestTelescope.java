package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTelescope {
    private TelescopeType type1;
    private TelescopeType type2;
    
    @BeforeEach
    public void runBefore() {
        type1 = TelescopeType.REFRACTOR;
        type2 = TelescopeType.MAKSUTOV_CASSEGRAIN;
    }

    @Test
    public void testConstructor() {
        // id indexing will start at 101, so test that id is < 100
        Telescope s1 = new Telescope("Celestron 2000", type1);
        assertEquals("Celestron 2000", s1.getTelescopeName());
        assertEquals(type1, s1.getTelescopeType());
        assertEquals("BEGINNER", s1.getExperienceLevel());
        assertTrue(s1.getAvailabilityStatus());
    }

    @Test
    public void testIdNumberIncrementing() {
        // test that id number does not change once generated
        Telescope s1 = new Telescope("Celestron 2000", type1);

        int initialId = s1.getTelescopeId();
        assertEquals(initialId, s1.getTelescopeId());
    }

    @Test
    public void testUniqueIdNumbers() {
        // test that each id number is unique
        Telescope s1 = new Telescope("Celestron 2000", type1);
        Telescope s2 = new Telescope("SkyWatcher 180", type2);

        int initialId1 = s1.getTelescopeId();
        int initialId2 = s2.getTelescopeId();

        assertNotEquals(initialId1, initialId2);
    }


    @Test 
    public void testReserve() {
        Telescope s1 = new Telescope("Celestron 2000", type1);

        assertTrue(s1.getAvailabilityStatus());

        s1.reserveTelescope();

        assertFalse(s1.getAvailabilityStatus());

    }

    @Test 
    public void testReturn() {
        Telescope s1 = new Telescope("Celestron 2000", type1);

        s1.reserveTelescope();

        assertFalse(s1.getAvailabilityStatus());

        s1.returnTelescope();

        assertTrue(s1.getAvailabilityStatus());

    }

    @Test
    public void testToJson() {
        Telescope s1 = new Telescope("Celestron 2000", type1);

        JSONObject json = s1.toJson();

        assertTrue(json.has("name"));
        assertTrue(json.has("type"));

        assertEquals("Celestron 2000", json.getString("name"));
        assertEquals("REFRACTOR", json.getString("type"));
    }
}
