package Validation;

public class MailTools {
    
    /**
     * @desc Validates if mailAddress is valid. It should be in the form of:
     *       <at least 1 character>@<at least 1 character>.<at least 1 character>
     * 
     * @subcontract no mailbox part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract subdomain-tld delimiter {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".").length > 2;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract no subdomain part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[0].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract no tld part {
     *   @requires !mailAddress.contains("@") ||
     *             mailAddress.split("@")[1].split(".")[1].length < 1;
     *   @ensures \result = false;
     * }
     * 
     * @subcontract valid email {
     *   @requires no other precondition
     *   @ensures \result = true;
     * }
     * 
     */
    public static boolean validateMailAddress(String mailAddress) {
        // No mail character
        if(!mailAddress.contains("@")){
            return false;
        }

        // No mailbox part
        if(!mailAddress.contains(".")){
            return false;
        }

        // Subdomain-tld delimiter
        if(mailAddress.split("@")[1].split("\\.").length > 2){
            return false;
        }

        // No subdomain part
        if(mailAddress.split("@")[1].split("\\.")[0].length() < 1){
            return false;
        }

        // No tld part
        if(mailAddress.split("@")[1].split("\\.", -1)[1].length() < 1){
            return false;
        }

        // If all conditions are met
        return true;
    }
}
