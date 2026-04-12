package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CALORIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCalorieTargetCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code SetCalorieTargetCommand} object.
 */
public class SetCalorieTargetCommandParser implements Parser<SetCalorieTargetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code SetCalorieTargetCommand}
     * and returns a {@code SetCalorieTargetCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public SetCalorieTargetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CLIENT, PREFIX_CALORIE);

        if (!argMultimap.getValue(PREFIX_CLIENT).isPresent()
                || !argMultimap.getValue(PREFIX_CALORIE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCalorieTargetCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CLIENT, PREFIX_CALORIE);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CLIENT).get());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCalorieTargetCommand.MESSAGE_USAGE), pe);
        }

        int calorieTarget = ParserUtil.parseCalories(argMultimap.getValue(PREFIX_CALORIE).get());

        return new SetCalorieTargetCommand(index, calorieTarget);
    }
}
