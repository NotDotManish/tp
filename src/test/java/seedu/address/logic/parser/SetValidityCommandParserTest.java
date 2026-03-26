package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VALIDITY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SetValidityCommand;
import seedu.address.model.person.Validity;

public class SetValidityCommandParserTest {

    private SetValidityCommandParser parser = new SetValidityCommandParser();

    @Test
    public void parse_validArgs_returnsSetValidityCommand() {
        Validity validity = new Validity("2026-12-31");
        assertParseSuccess(parser, "1 " + PREFIX_VALIDITY + "2026-12-31",
                new SetValidityCommand(INDEX_FIRST_PERSON, validity));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetValidityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingValidityPrefix_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetValidityCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValidity_throwsParseException() {
        assertParseFailure(parser, "1 " + PREFIX_VALIDITY + "invalid", Validity.MESSAGE_CONSTRAINTS);
    }
}
