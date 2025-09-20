package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestTelescopeType {

    // verify TelescopeType has 6 values

    @Test
    public void testTelescopeTypeHas6() {
        TelescopeType[] types = TelescopeType.values();
        assertEquals(6, types.length);

    }

    // test that type has an experience level
    @Test
    public void testGetExperienceLevelNotNull() {
        TelescopeType newton = TelescopeType.NEWTONIAN;
        assertNotNull(newton.getExperienceLevel());

    }

    // test that type has correct experience level
    @Test
    public void testGetExperienceLevel() {
        TelescopeType newton = TelescopeType.NEWTONIAN;
        assertEquals("INTERMEDIATE", newton.getExperienceLevel());

    }
}
