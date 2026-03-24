package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Person> personListView;

    private Consumer<Person> onSelectedPersonChanged;

    private final ObservableList<Person> allPersons;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList, ObservableList<Person> allPersons) {
        super(FXML);
        this.allPersons = allPersons;
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        // Trainer cards show derived fields (client count) based on the full address book list.
        // Refresh the visible list when the underlying list changes (e.g. client added/reassigned).
        this.allPersons.addListener((ListChangeListener<Person>) change -> personListView.refresh());

        personListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (onSelectedPersonChanged != null) {
                onSelectedPersonChanged.accept(newSelection);
            }
        });
    }

    /** Sets a callback to be invoked whenever the selected person changes. */
    public void setOnSelectedPersonChanged(Consumer<Person> onSelectedPersonChanged) {
        this.onSelectedPersonChanged = onSelectedPersonChanged;
    }

    /** Clears the current selection, if any. */
    public void clearSelection() {
        personListView.getSelectionModel().clearSelection();
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1, allPersons).getRoot());
            }
        }
    }

}
