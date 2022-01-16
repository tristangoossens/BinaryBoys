import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Validation.DateTools;

public class DateToolsTest {  
    @Test
    public void testValidateDateRequires31DaysInMonthEnsuresTrue() {
        // Arrange
        int day = 31;
        int month = 1;
        int year = 2022;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue("Date is in a correct format (31 days in a month)", result);
    }  
    
    @Test
    public void testValidateDateRequires32DaysInMonthEnsuresFalse(){
        // Arrange
        int day = 32;
        int month = 01;
        int year = 2022;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse("Date is in an incorrect format (there is only 31 days in a month)", result);
    }

    // @subcontract == 30 days in month
    @Test
    public void testValidateDateRequires30DaysInMonth4EnsuresTrue() {
        // Arrange
        int day = 30;
        int month = 04;
        int year = 2022;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue("Date is in a correct format (30 days in April)", result);
    }

    @Test
    public void testValidateDateRequires31DaysInMonth4EnsuresFalse() {
        // Arrange
        int day = 31;
        int month = 02;
        int year = 2022;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse("Date is in an incorrect format (there is only 30 days in April)", result);
    }

    @Test
    public void testValidateDateRequiresYear2020With29DaysInMonth2EnsuresTrue() {
        // Arrange
        int day = 29;
        int month = 02;
        int year = 2020;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue("Date is in a correct format (29 days in Febuary 2020)", result);
    }
    
    @Test
    public void testValidateDateRequiresYear2020With29DaysInMonth2EnsuresFalse() {
        // Arrange
        int day = 30;
        int month = 02;
        int year = 2020;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse("Date is in an incorrect format (there is only 29 days in Febuary 2020)", result);
    }

    @Test
    public void testValidateDateRequiresYear2022With28DaysInMonth2EnsuresTrue() {
        // Arrange
        int day = 28;
        int month = 02;
        int year = 2020;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertTrue("Date is in a correct format (28 days in Febuary 2022)", result);
    }

    @Test
    public void testValidateDateRequiresYear2022With29DaysInMonth2EnsuresFalse() {
        // Arrange
        int day = 29;
        int month = 02;
        int year = 2022;

        // Act
        Boolean result = DateTools.validateDate(day, month, year);

        // Assert
        assertFalse("Date is in an incorrect format (there are only 28 days in Febuary 2022)", result);
    }
}
