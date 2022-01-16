import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Validation.MailTools;

public class MailToolsTest {
    @Test
    public void testValidateMailAdressRequiresMailboxEnsuresTrue() {
        // Arrange
        String email = "test@mail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertTrue("E-mail contains a valid mailbox", result);
    }
    
    @Test
    public void testValidateMailAdressRequiresNoAtPartEnsuresFalse() {
        // Arrange
        String email = "testmail.com";
        
        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertFalse("E-mail is missing an @", result);
    }

    @Test
    public void testValidateMailAdressRequiresAtEnsuresTrue() {
        // Arrange
        String email = "test@mail.com";
        
        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertTrue("E-mail contains a valid format", result);
    }


    @Test
    public void testValidateMailAdressRequiresMultipleSubdomainsEnsuresFalse() {
        // Arrange
        String email = "test@mail.test.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertFalse("E-mail has too many subdomains", result);
    }

    @Test
    public void testValidateMailAdressRequiresSubdomainEnsuresTrue() {
        // Arrange
        String email = "test@mail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertTrue("E-mail contains a valid subdomain", result);
    }
    

    @Test
    public void testValidateMailAdressRequiresNoSubdomainPartEnsuresFalse() {
        // Arrange
        String email = "test@.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertFalse("E-mail is missing a subdomain", result);
    }

    
    @Test
    public void testValidateMailAdressRequiresNoTLDPartEnsuresFalse() {
        // Arrange
        String email = "test@mail.";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertFalse("E-mail is missing a TLD", result);
    }

    @Test
    public void testValidateMailAdressRequiresTLDEnsuresTrue() {
        // Arrange
        String email = "test@mail.com";

        // Act
        Boolean result = MailTools.validateMailAddress(email);

        // Assert
        assertTrue("E-mail contains a valid TLD", result);
    }
}
