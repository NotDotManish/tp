package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;

public class ImportCommandTest {

    @TempDir
    public Path testFolder;

    @Test
    public void execute_importSuccess() throws Exception {
        // First, create a valid JSON file to import
        Path importPath = testFolder.resolve("import.json");
        JsonAddressBookStorage storage = new JsonAddressBookStorage(importPath);
        storage.saveAddressBook(getTypicalAddressBook());

        // Setup models
        Model model = new ModelManager(); // empty model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        ImportCommand importCommand = new ImportCommand(importPath);

        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, importPath.toString());

        assertCommandSuccess(importCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_fileNotFound_throwsCommandException() {
        Path importPath = testFolder.resolve("nonExistent.json");
        ImportCommand importCommand = new ImportCommand(importPath);
        Model model = new ModelManager();

        String expectedMessage = String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, importPath.toString());

        assertCommandFailure(importCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidFormat_throwsCommandException() throws Exception {
        Path importPath = testFolder.resolve("invalid.json");
        java.nio.file.Files.writeString(importPath, "invalid json content");

        ImportCommand importCommand = new ImportCommand(importPath);
        Model model = new ModelManager();

        seedu.address.testutil.Assert.assertThrows(CommandException.class, () -> importCommand.execute(model));
    }

    @Test
    public void equals() {
        Path path1 = Path.of("path1.json");
        Path path2 = Path.of("path2.json");
        ImportCommand importCommand1 = new ImportCommand(path1);
        ImportCommand importCommand2 = new ImportCommand(path2);

        // same object -> returns true
        assertTrue(importCommand1.equals(importCommand1));

        // same values -> returns true
        ImportCommand importCommand1Copy = new ImportCommand(path1);
        assertTrue(importCommand1.equals(importCommand1Copy));

        // different types -> returns false
        assertFalse(importCommand1.equals(1));

        // null -> returns false
        assertFalse(importCommand1.equals(null));

        // different command -> returns false
        assertFalse(importCommand1.equals(importCommand2));
    }
}
