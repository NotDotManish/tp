package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;

public class ExportCommandParserTest {

    private ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_validArgs_returnsExportCommand() {
        // no spaces
        Path expectedPath = Paths.get("data/export.json");
        assertParseSuccess(parser, "data/export.json", new ExportCommand(expectedPath));

        // with spaces
        expectedPath = Paths.get("data/my export.json");
        assertParseSuccess(parser, "data/my export.json", new ExportCommand(expectedPath));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid extension
        assertParseFailure(parser, "data/export.csv",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));

        // trailing arguments
        assertParseFailure(parser, "data/export.json abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
    }
}
