package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRAINER;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReassignClientCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ReassignClientCommand} object.
 */
public class ReassignClientCommandParser implements Parser<ReassignClientCommand> {

    @Override
    public ReassignClientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRAINER);

        if (!arePrefixesPresent(argMultimap, PREFIX_TRAINER)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReassignClientCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TRAINER);

        Index clientIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
        Index trainerIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_TRAINER).get());

        return new ReassignClientCommand(clientIndex, trainerIndex);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
