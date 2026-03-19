package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReassignClientCommand;
import seedu.address.testutil.TypicalIndexes;

public class ReassignClientCommandParserTest {

    private final ReassignClientCommandParser parser = new ReassignClientCommandParser();

    @Test
    public void parse_missingParts_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReassignClientCommand.MESSAGE_USAGE);

        // empty input
        assertParseFailure(parser, "", expectedMessage);

        // missing client index (preamble is empty)
        assertParseFailure(parser, " t/1", expectedMessage);

        // missing trainer prefix
        assertParseFailure(parser, " 1", expectedMessage);
    }

    @Test
    public void parse_invalidClientIndex_throwsParseException() {
        assertParseFailure(parser, " abc t/1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidTrainerIndex_throwsParseException() {
        assertParseFailure(parser, " 1 t/abc", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_validArgs_returnsCommand() {
        ReassignClientCommand expected = new ReassignClientCommand(
                TypicalIndexes.INDEX_FIRST_PERSON, TypicalIndexes.INDEX_SECOND_PERSON);
        assertParseSuccess(parser, " 1 t/2", expected);
    }
}
