package Validation;

public class UrlValidation {
    /**
     * @desc Checks if the given url is properly formatted. This function will return true on succes
     *       And will return false when entering an incorrect url.
     * 
     * @subcontract valid url
     *   @requires url.split(".").length < 3 &&
     *             !url.startsWith("https://") && !url.startsWith("http://")
     *             url.split(".")[0].length() < 9 || url.split(".")[0].length() < 8 &&
     *             url.split(".")[1].length() < 1 &&
     *             url.split(".")[2].length() < 1;
     *   @ensures \result = true
     * }
     *  
     * @subcontract invalid url
     *   @requires no other valid precondition;
     *   @ensures \ result = false;
     * }
     * 
     */
    public static boolean validateUrl(String url) {
        String[] slices = url.split("\\.");

        // Check if all parts are given
        if(slices.length < 3){
            return false;
        }
        
        // Check if the Url has a subdomain (www)
        if(slices[0].length() < 9 || slices[0].length() < 8){
            return false;
        }

        // Check if the url has a protocol
        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            return false;
        }

        // Check if the url has a domain name
        if(slices[1].length() < 1){
            return false;
        }

        // Check if there is a tld specified
        if(slices[2].length() < 1){
            return false;
        }

        // If all conditions are met, return true
        return true;
    }
}
