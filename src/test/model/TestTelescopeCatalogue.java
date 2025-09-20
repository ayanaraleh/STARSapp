package model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class TestTelescopeCatalogue {
    private TelescopeCatalogue tc;
    private Telescope s1;
    private Telescope s2;
    private TelescopeType type1;
    private TelescopeType type2;

    @BeforeEach
    public void runBefore() {
        tc = new TelescopeCatalogue();
        type1 = TelescopeType.REFRACTOR;
        type2 = TelescopeType.MAKSUTOV_CASSEGRAIN;
        s1 = new Telescope("Celestron 200", type1);
        s2 = new Telescope("SkyWatcher 1800", type2);

    }

    @Test
    public void testConstructor() {
        assertEquals(0, tc.viewCatalogue().size());

    }

    @Test
    public void testAdd1Telescope() {
        tc.addTelescope(s1);
        assertEquals(1, tc.viewCatalogue().size());
        assertNotNull(tc.getTelescope(s1.getTelescopeId()));
    }

    @Test
    public void testAddManyTelescopes() {
        tc.addTelescope(s1);
        tc.addTelescope(s2);
        assertEquals(2, tc.viewCatalogue().size());
        assertNotNull(tc.getTelescope(s1.getTelescopeId()));
        assertNotNull(tc.getTelescope(s2.getTelescopeId()));

        // check order of scope addition:
        assertEquals(s1, tc.viewCatalogue().get(0));
        assertEquals(s2, tc.viewCatalogue().get(1));
    }

    @Test
    public void testRemove1TelescopeSuccess() {
        tc.addTelescope(s1);
        assertTrue(tc.removeTelescope(s1.getTelescopeId()));
        assertEquals(0, tc.viewCatalogue().size());

    }

    @Test
    public void testRemove1TelescopeFail() {
        tc.addTelescope(s1);
        tc.removeTelescope(s2.getTelescopeId());
        assertFalse(tc.removeTelescope(s2.getTelescopeId()));
        assertEquals(1, tc.viewCatalogue().size());

    }

    @Test
    public void testRemove1TelescopeReserved() {
        tc.addTelescope(s1);
        s1.reserveTelescope();
        tc.removeTelescope(s1.getTelescopeId());
        assertFalse(tc.removeTelescope(s1.getTelescopeId()));
        assertEquals(1, tc.viewCatalogue().size());

    }

    @Test
    public void testRemove2Telescopes() {
        tc.addTelescope(s1);
        tc.addTelescope(s2);

        assertTrue(tc.removeTelescope(s2.getTelescopeId()));
        assertTrue(tc.removeTelescope(s1.getTelescopeId()));
        assertNull(tc.getTelescope(s2.getTelescopeId()));
        assertNull(tc.getTelescope(s1.getTelescopeId()));
        assertEquals(0, tc.viewCatalogue().size());

    }

    @Test
    public void test1AvailableTelescope() {
        tc.addTelescope(s1);
        List<Telescope> available = tc.getAvailableTelescopes();
        assertEquals(1, available.size());
        assertTrue(s1.getAvailabilityStatus());
        assertEquals(s1.getTelescopeId(), available.get(0).getTelescopeId());

    }

    @Test
    public void testReturnedAvailableTelescope() {
        tc.addTelescope(s1);
        s1.reserveTelescope();
        s1.returnTelescope();

        List<Telescope> available = tc.getAvailableTelescopes();
        assertEquals(1, available.size());
        assertEquals(s1.getTelescopeId(), available.get(0).getTelescopeId());
        assertTrue(available.get(0).getAvailabilityStatus());

    }

    @Test
    public void test2AvailableTelescopes() {
        tc.addTelescope(s1);
        tc.addTelescope(s2);

        List<Telescope> available = tc.getAvailableTelescopes();

        assertEquals(2, available.size());
        assertEquals(s1.getTelescopeId(), available.get(0).getTelescopeId());
        assertEquals(s2.getTelescopeId(), available.get(1).getTelescopeId());
        assertTrue(available.get(0).getAvailabilityStatus());
        assertTrue(available.get(1).getAvailabilityStatus());

    }

    @Test
    // test telescope gets added to correct lists
    // outcome: is not added to list of availables
    public void testAvailableAndUnavailableTelescope() {
        // 1. telescope is available
        tc.addTelescope(s1);
        assertEquals(1, tc.getAvailableTelescopes().size());
        assertEquals(0, tc.getUnavailableTelescopes().size());

        // 2. telescope is reserved and unavailable
        s1.reserveTelescope();
        assertEquals(0, tc.getAvailableTelescopes().size());
        assertEquals(1, tc.getUnavailableTelescopes().size());

    }

    @Test
    public void testUnavailableTelescope() {
        tc.addTelescope(s1);
        s1.reserveTelescope();

        List<Telescope> unavailable = tc.getUnavailableTelescopes();
        assertEquals(1, unavailable.size());
        assertEquals(s1.getTelescopeId(), unavailable.get(0).getTelescopeId());
        assertFalse(unavailable.get(0).getAvailabilityStatus());

    }

    @Test
    public void test2UnavailableTelescopes() {
        tc.addTelescope(s1);
        tc.addTelescope(s2);
        s1.reserveTelescope();
        s2.reserveTelescope();

        List<Telescope> unavailable = tc.getUnavailableTelescopes();
        assertEquals(2, unavailable.size());
        assertEquals(s1.getTelescopeId(), unavailable.get(0).getTelescopeId());
        assertEquals(s2.getTelescopeId(), unavailable.get(1).getTelescopeId());
        assertFalse(unavailable.get(0).getAvailabilityStatus());
        assertFalse(unavailable.get(1).getAvailabilityStatus());

    }

    @Test
    public void testFilterByExperience1Match() {
        tc.addTelescope(s1);
        List<Telescope> oneMatch = tc.filterByExperience("BEGINNER");
        assertEquals(1, oneMatch.size());
        assertEquals(s1.getTelescopeId(), oneMatch.get(0).getTelescopeId());
        assertEquals(s1.getTelescopeType(), oneMatch.get(0).getTelescopeType());

    }

    @Test
    // test for unavailable telescope matching experience level
    // outcome: return an empty list
    public void testFilterByExperienceUnavailable() {
        tc.addTelescope(s1);
        s1.reserveTelescope();
        List<Telescope> unavailableMatch = tc.filterByExperience("BEGINNER");
        assertEquals(0, unavailableMatch.size());

    }

    @Test
    public void testFilterByExperienceNoMatch() {
        List<Telescope> noMatch = tc.filterByExperience("BEGINNER");
        assertEquals(0, noMatch.size());

    }

    @Test
    public void testFilterByExperience2Matches() {
        Telescope s3 = new Telescope("Binocular 9000", type1);
        tc.addTelescope(s1);
        tc.addTelescope(s3);

        List<Telescope> twoMatch = tc.filterByExperience("BEGINNER");
        assertEquals(2, twoMatch.size());
        assertEquals(s1, twoMatch.get(0));
        assertEquals(s3, twoMatch.get(1));
        assertEquals(s1.getTelescopeType(), twoMatch.get(0).getTelescopeType());
        assertEquals(s3.getTelescopeType(), twoMatch.get(1).getTelescopeType());

    }

    // ensure list only returns same experienceLevel
    // if catalogue has mixed types
    @Test
    public void testFilterByExperienceMixed() {
        TelescopeType type3 = TelescopeType.NEWTONIAN;
        Telescope s3 = new Telescope("Dobsonian 80", type3);
        Telescope s4 = new Telescope("Binocular 9000", type1);

        tc.addTelescope(s2);
        tc.addTelescope(s1);
        tc.addTelescope(s3);
        tc.addTelescope(s4);

        List<Telescope> mixed = tc.filterByExperience("BEGINNER");
        assertEquals(2, mixed.size());
        assertEquals(s1, mixed.get(0));
        assertEquals(s4, mixed.get(1));
        assertEquals(s1.getTelescopeType(), mixed.get(0).getTelescopeType());
        assertEquals(s4.getTelescopeType(), mixed.get(1).getTelescopeType());

    }

    // TEST FOR toJson() Method.
    // outcome: method correctly converts Java to JSON
    @Test
    void testToJson() {
        tc.addTelescope(s1);
        tc.addTelescope(s2);

        JSONObject json = tc.toJson();

        assertTrue(json.has("telescopes"));
        assertEquals(2, json.getJSONArray("telescopes").length());

        JSONObject t1 = json.getJSONArray("telescopes").getJSONObject(0);
        assertEquals(s1.getTelescopeName(), t1.getString("name"));
        assertEquals(s1.getTelescopeType().toString(), t1.getString("type"));

        JSONObject t2 = json.getJSONArray("telescopes").getJSONObject(1);
        assertEquals(s2.getTelescopeName(), t2.getString("name"));
        assertEquals(s2.getTelescopeType().toString(), t2.getString("type"));
    }

}
