import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import Validation.PostalCode;

public class PostalCodeTest {
    // Test if passing null returns a nullpointerException
    @Test(expected = NullPointerException.class)
    public void testformatPostalCodeRequiresNullSignalsNullPointerException() {
        PostalCode.formatPostalCode(null);
    }

    // Test if a valid postal code returns a formatted postal code
    @Test
    public void testformatPostalCodeRequires1234aAEnsures1234_AA(){
        String postalCode = "1234aA";

        String result = PostalCode.formatPostalCode(postalCode);

        assertEquals("1234 AA", result);
    }

    // Test if a valid postal code returns a formatted postal code
    @Test
    public void testformatPostalCodeRequires4321_ZBEnsures4321_ZB(){
        String postalCode = "4321 ZB";

        String result = PostalCode.formatPostalCode(postalCode);

        assertEquals("4321 ZB", result);
    }

    // Test if a passing a invalid postal code will return a illegalargumentexeption
    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires1234A$SignalsIllegalArgumentException() {
        PostalCode.formatPostalCode("1234A$");
    }

    // TODO: Add testcases for
    // Checking out of bounds numbers
    // Checking postal codes with fewer numbers (333 AB)
    // Checking postal codes with fewer letters (3333 A)
    // Checking postal codes with more numbers (33333 AB)
    // Checking postal codes with more letters (3333 ABA)
}
