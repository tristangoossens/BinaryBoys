import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import Validation.GradeValidation;

import org.junit.Test;

public class GradeValidationTest {
    @Test
    public void testValidateGradeis0EnsuresFalse(){
        // Arrange
        int grade = 0;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertFalse("Invalid input (0)", result);
    }

    @Test
    public void testValidateGradeis2EnsuresTrue(){
        // Arrange
        int grade = 2;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is correct (2)", result);
    }

    @Test
    public void testValidateGradeis7EnsuresTrue(){
        // Arrange
        int grade = 7;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is correct (7)", result);
    }

    @Test
    public void testValidateGradeis10EnsuresTrue(){
        // Arrange
        int grade = 10;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertTrue("Grade is correct (10)", result);
    }


    @Test
    public void testValidateGradeIs13EnsuresFalse(){
        // Arrange
        int grade = 11;

        // Act
        Boolean result = GradeValidation.validateGrade(grade);

        // Assert
        assertFalse("Invalid input (13)", result);
    }
}
