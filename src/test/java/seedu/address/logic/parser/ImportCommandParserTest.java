package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_validArgs_returnsImportCommand() {
        // no spaces
        Path expectedPath = Paths.get("data/import.json");
        assertParseSuccess(parser, "data/import.json", new ImportCommand(expectedPath));

        // with spaces
        expectedPath = Paths.get("data/my import.json");
        assertParseSuccess(parser, "data/my import.json", new ImportCommand(expectedPath));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid extension
        assertParseFailure(parser, "data/import.csv",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));

        // trailing arguments
        assertParseFailure(parser, "data/import.json abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
