package persistence;

import model.TelescopeCatalogue;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCatalogueJsonReader {
    private static final String TEST_FILE = "./data/test_catalogue_reader.json";
    private static final String EMPTY_FILE = "./data/test_catalogue_empty.json";

    @Test
    void testReadCatalogue() {
        try {
            CatalogueJsonReader reader = new CatalogueJsonReader(TEST_FILE);
            TelescopeCatalogue tc = reader.read();
            assertEquals(2, tc.viewCatalogue().size());
        } catch (IOException e) {
            fail("Failed to read from test file.");
        }
    }

    // EFFECTS: Tests that empty catalogue file loads correctly.
    @Test
    void testReadEmptyCatalogue() {
        try {
            CatalogueJsonReader reader = new CatalogueJsonReader(EMPTY_FILE);
            TelescopeCatalogue tc = reader.read();
            assertEquals(0, tc.viewCatalogue().size());
        } catch (IOException e) {
            fail("Failed to read from test file.");
        }
    }

}
