package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Client;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Trainer;

public class ReassignClientCommandTest {

    @Test
    public void execute_validIndices_success() {
        AddressBook ab = new AddressBook();
        Trainer oldTrainer = new Trainer(new Name("Alice"), new Phone("91234567"),
                new Email("alice@example.com"), new HashSet<>());
        Trainer newTrainer = new Trainer(new Name("Bob"), new Phone("92345678"),
                new Email("bob@example.com"), new HashSet<>());
        Client client = new Client(new Name("Charlie"), new Phone("81234567"),
                oldTrainer.getPhone(), oldTrainer.getName(), new HashSet<>());
        ab.addPerson(oldTrainer);
        ab.addPerson(newTrainer);
        ab.addPerson(client);
        Model model = new ModelManager(ab, new UserPrefs());

        // Client list: [client] — INDEX_FIRST_PERSON → client
        // Trainer list: [oldTrainer, newTrainer] — INDEX_SECOND_PERSON → newTrainer
        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        Client updatedClient = new Client(client.getName(), client.getPhone(),
                newTrainer.getPhone(), newTrainer.getName(), client.getTags(),
                client.getCalorieTarget(), client.getCalorieIntake(),
                client.getWorkoutFocus(), client.getRemark());
        String expectedMessage = String.format(ReassignClientCommand.MESSAGE_SUCCESS,
                updatedClient.getName(), newTrainer.getName());

        AddressBook expectedAb = new AddressBook();
        expectedAb.addPerson(oldTrainer);
        expectedAb.addPerson(newTrainer);
        expectedAb.addPerson(updatedClient);
        Model expectedModel = new ModelManager(expectedAb, new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIndex_failure() {
        AddressBook ab = new AddressBook();
        Trainer trainer = new Trainer(new Name("Alice"), new Phone("91234567"),
                new Email("alice@example.com"), new HashSet<>());
        ab.addPerson(trainer);
        Model model = new ModelManager(ab, new UserPrefs());

        // Client list is empty, so INDEX_FIRST_PERSON is out of bounds
        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        assertCommandFailure(command, model, ReassignClientCommand.MESSAGE_INVALID_CLIENT_INDEX);
    }

    @Test
    public void execute_invalidTrainerIndex_failure() {
        AddressBook ab = new AddressBook();
        Trainer trainer = new Trainer(new Name("Alice"), new Phone("91234567"),
                new Email("alice@example.com"), new HashSet<>());
        Client client = new Client(new Name("Charlie"), new Phone("81234567"),
                trainer.getPhone(), trainer.getName(), new HashSet<>());
        ab.addPerson(trainer);
        ab.addPerson(client);
        Model model = new ModelManager(ab, new UserPrefs());

        // Trainer list has 1 trainer, so index 2 is out of bounds
        Index outOfBoundTrainerIndex = Index.fromOneBased(model.getFilteredTrainerList().size() + 1);
        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, outOfBoundTrainerIndex);
        assertCommandFailure(command, model, ReassignClientCommand.MESSAGE_INVALID_TRAINER_INDEX);
    }

    @Test
    public void execute_filteredClientListHasNonClient_failure() {
        Trainer trainer = new Trainer(new Name("Alice"), new Phone("91234567"),
                new Email("alice@example.com"), new HashSet<>());
        // Stub returns a Trainer in the client list — instanceof Client check fails
        Model model = new ModelStubWithClientList(trainer);

        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        assertThrows(CommandException.class,
                ReassignClientCommand.MESSAGE_INVALID_CLIENT_INDEX, () -> command.execute(model));
    }

    @Test
    public void execute_filteredTrainerListHasNonTrainer_failure() {
        Trainer trainer = new Trainer(new Name("Alice"), new Phone("91234567"),
                new Email("alice@example.com"), new HashSet<>());
        Client client = new Client(new Name("Charlie"), new Phone("81234567"),
                trainer.getPhone(), trainer.getName(), new HashSet<>());
        // Stub: client list has a real Client, trainer list has a Client — instanceof Trainer check fails
        Model model = new ModelStubWithBothLists(client, client);

        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        assertThrows(CommandException.class,
                ReassignClientCommand.MESSAGE_INVALID_TRAINER_INDEX, () -> command.execute(model));
    }

    @Test
    public void toStringMethod() {
        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        String expected = ReassignClientCommand.class.getCanonicalName()
                + "{clientIndex=" + INDEX_FIRST_PERSON + ", trainerIndex=" + INDEX_SECOND_PERSON + "}";
        assertEquals(expected, command.toString());
    }

    @Test
    public void equals() {
        ReassignClientCommand command = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        ReassignClientCommand commandCopy = new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);
        assertTrue(command.equals(commandCopy));
        assertEquals(command.hashCode(), commandCopy.hashCode());

        // null -> returns false
        assertFalse(command.equals(null));

        // different type -> returns false
        assertFalse(command.equals(1));

        // different client index -> returns false
        assertFalse(command.equals(new ReassignClientCommand(INDEX_SECOND_PERSON, INDEX_SECOND_PERSON)));

        // different trainer index -> returns false
        assertFalse(command.equals(new ReassignClientCommand(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON)));
    }

    private static class ModelStubWithClientList extends ModelStub {
        private final ObservableList<Person> filteredClientList;

        ModelStubWithClientList(Person person) {
            filteredClientList = FXCollections.observableArrayList(person);
        }

        @Override
        public ObservableList<Person> getFilteredClientList() {
            return filteredClientList;
        }
    }

    private static class ModelStubWithBothLists extends ModelStub {
        private final ObservableList<Person> filteredClientList;
        private final ObservableList<Person> filteredTrainerList;

        ModelStubWithBothLists(Person clientItem, Person trainerItem) {
            filteredClientList = FXCollections.observableArrayList(clientItem);
            filteredTrainerList = FXCollections.observableArrayList(trainerItem);
        }

        @Override
        public ObservableList<Person> getFilteredClientList() {
            return filteredClientList;
        }

        @Override
        public ObservableList<Person> getFilteredTrainerList() {
            return filteredTrainerList;
        }
    }
}
