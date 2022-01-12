package Logic.Validation;

public class PostalCode {

    /**
     * @desc Formats the input postal code to a uniform output in the form
     * nnnn<space>MM, where nnnn is numeric and > 999 and MM are 2 capital letters.
     * Spaces before and after the input string are trimmed.
     * 
     * @subcontract null postalCode {
     *   @requires postalCode == null;
     *   @signals (NullPointerException) postalCode == null;
     * }
     * 
     * @subcontract valid postalCode {
     *   @requires Integer.valueOf(postalCode.trim().substring(0, 4)) > 999 &&
     *             Integer.valueOf(postalCode.trim().substring(0, 4)) <= 9999 &&
     *             postalCode.trim().substring(4).trim().length == 2 &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z' &&
     *             'A' <= postalCode.trim().substring(4).trim().toUpperCase().charAt(0) <= 'Z';
     *   @ensures \result = postalCode.trim().substring(0, 4) + " " +
     *                  postalCode.trim().substring(4).trim().toUpperCase()
     * }
     *  
     * @subcontract invalid postalCode {
     *   @requires no other valid precondition;
     *   @signals (IllegalArgumentException);
     * }
     * 
     */
    public static String formatPostalCode(/* non_null */ String postalCode) throws IllegalArgumentException, NullPointerException {
        if(postalCode == null){
            throw new NullPointerException("Er is geen postcode ingevoerd");
        }

        // Remove all non alphabetical and non numeric values (\\W regex)
        String validPostalCode = postalCode.replaceAll("\\W", "").trim();

        try{
            int postalcodeNumbers = Integer.valueOf(validPostalCode.substring(0, 4));
            String postalcodeLetters = validPostalCode.substring(4).toUpperCase();

            char firstLetter = postalcodeLetters.charAt(0);
            char secondLetter = postalcodeLetters.charAt(1);

            if (postalcodeNumbers > 999 && postalcodeNumbers <= 9999 && postalcodeLetters.length() == 2
                    && ('A' <= firstLetter && firstLetter <= 'Z')
                    && ('A' <= secondLetter && secondLetter <= 'Z')) {

                    
                // Return a formatted string with a space between the numeric values and the uppercase letters
                return String.format("%s %s", validPostalCode.substring(0,4), validPostalCode.trim().substring(4).toUpperCase());
            }else{
                throw new IllegalArgumentException("De ingevulde postcode is incorrect");
            }

        }catch(Exception e){
            // In case of any error, there will be thrown an illegalargumentexception
            throw new IllegalArgumentException("De ingevulde postcode is incorrect");
        }
    }   
}
