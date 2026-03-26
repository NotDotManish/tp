package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ExportCommandTest {

    @TempDir
    public Path testFolder;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_exportSuccess() {
        Path exportPath = testFolder.resolve("export.json");
        ExportCommand exportCommand = new ExportCommand(exportPath);

        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, exportPath.toString());

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        assertCommandSuccess(exportCommand, model, expectedMessage, expectedModel);

        // verify file was created
        assertTrue(exportPath.toFile().exists());
    }

    @Test
    public void equals() {
        Path path1 = Path.of("path1.json");
        Path path2 = Path.of("path2.json");
        ExportCommand exportCommand1 = new ExportCommand(path1);
        ExportCommand exportCommand2 = new ExportCommand(path2);

        // same object -> returns true
        assertTrue(exportCommand1.equals(exportCommand1));

        // same values -> returns true
        ExportCommand exportCommand1Copy = new ExportCommand(path1);
        assertTrue(exportCommand1.equals(exportCommand1Copy));

        // different types -> returns false
        assertFalse(exportCommand1.equals(1));

        // null -> returns false
        assertFalse(exportCommand1.equals(null));

        // different command -> returns false
        assertFalse(exportCommand1.equals(exportCommand2));
    }
}
