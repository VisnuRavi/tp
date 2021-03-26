package seedu.address.model.garment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Garment's DressCode in the wardrobe.
 * Guarantees: immutable; is valid as declared in {@link #isValidDressCode(String)}
 */
public class DressCode {

    public static final String MESSAGE_CONSTRAINTS = "DressCode can take 3 values: formal, active, casual";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "formal|active|casual";

    public static final HashMap<String, List<String>> MATCHES = new HashMap<>();

    public static final List<String> FORMAL_MATCHES = Arrays.asList(
            "formal"
    );

    public static final List<String> ACTIVE_MATCHES = Arrays.asList(
            "active"
    );

    public static final List<String> CASUAL_MATCHES = Arrays.asList(
            "casual"
    );


    public final String value;

    /**
     * Constructs an {@code dresscode}.
     *
     * @param dresscode A valid DressCode.
     */
    public DressCode(String dresscode) {
        requireNonNull(dresscode);
        checkArgument(isValidDressCode(dresscode), MESSAGE_CONSTRAINTS);
        value = dresscode;
    }

    /**
     * Returns true if a given string is a valid DressCode.
     */
    public static boolean isValidDressCode(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static void initialiseMatches() {
        MATCHES.put("formal", FORMAL_MATCHES);
        MATCHES.put("active", ACTIVE_MATCHES);
        MATCHES.put("casual", CASUAL_MATCHES);
    }

    public List<String> getMatches() {
        return MATCHES.get(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DressCode // instanceof handles nulls
                && value.equals(((DressCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
