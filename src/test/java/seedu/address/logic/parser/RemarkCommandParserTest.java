package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;
import seedu.address.testutil.TypicalIndexes;

public class RemarkCommandParserTest {

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " c/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " r/Some remark",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyRemark_throwsParseException() {
        assertParseFailure(parser, " c/1 r/   ", Remark.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgs_returnsCommand() {
        RemarkCommand expected = new RemarkCommand(TypicalIndexes.INDEX_FIRST_PERSON, new Remark("Needs follow-up"));
        assertParseSuccess(parser, " c/1 r/Needs follow-up", expected);
    }
}
