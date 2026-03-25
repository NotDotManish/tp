package seedu.address;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class MainAppTest {

    @TempDir
    public Path tempDir;

    @Test
    public void migrateDataFile_legacyExists_renamedToGymOps() throws IOException {
        Path legacyPath = tempDir.resolve("addressbook.json");
        Files.createFile(legacyPath);

        Path result = MainApp.migrateDataFile(legacyPath);

        assertEquals(tempDir.resolve("GymOps.json"), result);
        assertTrue(Files.exists(result));
        assertFalse(Files.exists(legacyPath));
    }

    @Test
    public void migrateDataFile_gymOpsAlreadyExists_noRename() throws IOException {
        Path legacyPath = tempDir.resolve("addressbook.json");
        Path gymOpsPath = tempDir.resolve("GymOps.json");
        Files.createFile(legacyPath);
        Files.createFile(gymOpsPath);

        Path result = MainApp.migrateDataFile(legacyPath);

        assertEquals(gymOpsPath, result);
        assertTrue(Files.exists(gymOpsPath));
        assertTrue(Files.exists(legacyPath));
    }

    @Test
    public void migrateDataFile_neitherExists_returnsGymOpsPath() {
        Path legacyPath = tempDir.resolve("addressbook.json");

        Path result = MainApp.migrateDataFile(legacyPath);

        assertEquals(tempDir.resolve("GymOps.json"), result);
        assertFalse(Files.exists(result));
    }

    @Test
    public void migrateDataFile_nonLegacyPath_returnsUnchanged() {
        Path customPath = tempDir.resolve("GymOps.json");

        Path result = MainApp.migrateDataFile(customPath);

        assertEquals(customPath, result);
    }
}
