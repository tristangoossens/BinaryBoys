import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import Validation.PostalCode;

public class PostalCodeTest {
    @Test(expected = NullPointerException.class)
    public void testformatPostalCodeRequiresNullSignalsNullPointerException() {
        // Execute
        PostalCode.formatPostalCode(null);
    }

    @Test
    public void testformatPostalCodeRequires1234aAEnsures1234_AA(){
        // Execute
        String result = PostalCode.formatPostalCode("1234aA");

        // Assert
        assertEquals("1234 AA", result);
    }

    @Test
    public void testformatPostalCodeRequires4321_ZBEnsures4321_ZB(){
        // Execute
        String result = PostalCode.formatPostalCode("4321 ZB");

        // Assert
        assertEquals("4321 ZB", result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires1234A$SignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1234A$");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires10000ABSignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("10000AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires333ABSignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("333AB");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires1000ASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1000A");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testformatPostalCodeRequires1000ABASignalsIllegalArgumentException() {
        // Execute
        PostalCode.formatPostalCode("1000ABA");
    }
}
