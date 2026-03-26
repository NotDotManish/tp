package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ValidityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Validity(null));
    }

    @Test
    public void constructor_invalidValidity_throwsIllegalArgumentException() {
        String invalidValidity = "invalid";
        assertThrows(IllegalArgumentException.class, () -> new Validity(invalidValidity));
    }

    @Test
    public void isValidValidity() {
        // null validity
        assertThrows(NullPointerException.class, () -> Validity.isValidValidity(null));

        // invalid validity format
        assertFalse(Validity.isValidValidity("")); // empty string
        assertFalse(Validity.isValidValidity(" ")); // spaces only
        assertFalse(Validity.isValidValidity("20-12-2026")); // DD-MM-YYYY format
        assertFalse(Validity.isValidValidity("26-12-31")); // YY-MM-DD
        assertFalse(Validity.isValidValidity("2026/12/31")); // slashes
        assertFalse(Validity.isValidValidity("2026-13-01")); // invalid month
        assertFalse(Validity.isValidValidity("2026-12-32")); // invalid day
        assertFalse(Validity.isValidValidity("abcd-ef-gh")); // alphabets

        // valid validity format
        assertTrue(Validity.isValidValidity("2026-12-31")); // correct YYYY-MM-DD format
        assertTrue(Validity.isValidValidity("2024-02-29")); // leap year
    }

    @Test
    public void equals() {
        Validity validity = new Validity("2026-12-31");

        // same values -> returns true
        assertTrue(validity.equals(new Validity("2026-12-31")));

        // same object -> returns true
        assertTrue(validity.equals(validity));

        // null -> returns false
        assertFalse(validity.equals(null));

        // different types -> returns false
        assertFalse(validity.equals(5.0f));

        // different values -> returns false
        assertFalse(validity.equals(new Validity("2027-12-31")));
    }
}
