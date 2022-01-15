import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import Validation.NumericRangeTools;

public class NumericRangeToolsTest { 
    @Test
    public void testIsPercentageValidRequiresEnsuresFalse(){
        // Execute
        int percentage = -1;
        Boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse("Percentage is out of range (-1)", result);
    }

    @Test
    public void testIsPercentageValidRequires0EnsuresTrue(){
        // Execute
        int percentage = 0;
        Boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue("Valid percentage (0)", result);
    }

    @Test
    public void testIsPercentageValidRequires65EnsuresTrue(){
        // Execute
        int percentage = 65;
        Boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue("Valid percentage (65)", result);
    }

    @Test
    public void testIsPercentageValidRequires100EnsuresTrue(){
        // Execute
        int percentage = 100;
        Boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertTrue("Valid percentage (100)", result);
    }

    @Test
    public void testIsPercentageValidRequires101EnsuresFalse(){
        // Execute
        int percentage = 101;
        Boolean result = NumericRangeTools.isValidPercentage(percentage);

        // Assert
        assertFalse("Percentage is out of range (101)", result);
    }
}
