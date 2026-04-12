package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.LogCalorieIntakeCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the LogCalorieIntakeCommand code. The path variations for those cases occur
 * inside the ParserUtil, and therefore should be covered by the ParserUtilTest.
 */
public class LogCalorieIntakeCommandParserTest {

    private static final String VALID_CALORIES = "500";
    private static final String INVALID_CALORIES = "abc";

    private final LogCalorieIntakeCommandParser parser = new LogCalorieIntakeCommandParser();

    @Test
    public void parse_validArgs_returnsLogCalorieIntakeCommand() {
        assertParseSuccess(parser, " " + PREFIX_CLIENT + "1 " + PREFIX_CALORIE + VALID_CALORIES,
                new LogCalorieIntakeCommand(INDEX_FIRST_PERSON, 500));
    }

    @Test
    public void parse_missingCaloriePrefix_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCalorieIntakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CALORIE + VALID_CALORIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCalorieIntakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT + "a " + PREFIX_CALORIE + VALID_CALORIES,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LogCalorieIntakeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidCalories_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT + "1 " + PREFIX_CALORIE + INVALID_CALORIES,
                "Calories must be a positive integer.");
    }

    @Test
    public void parse_zeroCalories_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_CLIENT + "1 " + PREFIX_CALORIE + "0",
                "Calories must be a positive integer.");
    }
}
