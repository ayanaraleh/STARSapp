package persistence;

import model.Telescope;
import model.TelescopeCatalogue;
import model.TelescopeType;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestCatalogueJsonWriter {
    private static final String TEST_FILE = "./data/test_catalogue_writer.json";
    private static final String EMPTY_FILE = "./data/test_catalogue_writer_empty.json";
    private TelescopeCatalogue tc;
    private CatalogueJsonWriter writer;

    @Test
    public void testWriteEmptyCatalogue() {
        try {
            tc = new TelescopeCatalogue();
            writer = new CatalogueJsonWriter(EMPTY_FILE);
            writer.open();
            writer.write(tc);
            writer.close();

            CatalogueJsonReader reader = new CatalogueJsonReader(EMPTY_FILE);
            tc = reader.read();
            assertNotNull(tc);
            assertEquals(0, tc.viewCatalogue().size());
        } catch (IOException e) {
            fail("Exception should not be thrown....");
        }

    }

    @Test
    public void testWriteCatalogue() {
        Telescope s1 = new Telescope("Celestron 200", TelescopeType.REFRACTOR);
        Telescope s2 = new Telescope("SkyWatcher 1800", TelescopeType.REFLECTOR);
        try {
            tc = new TelescopeCatalogue();
            tc.addTelescope(s1);
            tc.addTelescope(s2);

            writer = new CatalogueJsonWriter(TEST_FILE);
            writer.open();
            writer.write(tc);
            writer.close();

            CatalogueJsonReader reader = new CatalogueJsonReader(TEST_FILE);
            tc = reader.read();
            assertNotNull(tc);
            assertEquals(2, tc.viewCatalogue().size());
            assertEquals(s1.getTelescopeName(), tc.viewCatalogue().get(0).getTelescopeName());
            assertEquals(s2.getTelescopeName(), tc.viewCatalogue().get(1).getTelescopeName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
