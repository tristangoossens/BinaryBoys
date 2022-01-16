package Validation;

public class GradeValidation {
    /**
     * @desc Checks if the given grade is properly formatted. This function will return true on succes
     *       And will return false when entering an incorrect grade.
     * 
     * @subcontract valid url
     *   @requires grade >= 1 &&
     *             grade <= 10;
     *   @ensures \result = true
     * 
     *  
     * @subcontract invalid grade
     *   @requires no other valid precondition;
     *   @ensures \ result = false;
     * 
     */
    public static boolean validateGrade(int grade) {
        // Check if the grade is lower than 1 and higher than 10
        if (grade < 1 || grade > 10) {
            return false;
        }

        // If all conditions are met, return true
        return true;
    }
}
