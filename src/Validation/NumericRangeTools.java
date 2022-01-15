package Validation;

public class NumericRangeTools {
    /**
     * @desc Validates if the input is within range of 0-100%
     * 
     * @subcontract value within valid range {
     *   @requires 0 <= percentage <= 100;
     *   @ensures \result = true;
     * }
     * 
     * @subcontract value out of range low {
     *   @requires percentage < 0;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract value out of range high {
     *   @requires percentage > 100;
     *   @signals \result = false;
     * }
     * 
     */
    public static boolean isValidPercentage(int percentage) {
        // Return true if the percentage is valid
        if(percentage >= 0 && percentage <= 100){
            return true;
        }

        // Return false if int is out of range
        return false;
    }    
}

