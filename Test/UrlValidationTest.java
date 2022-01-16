import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import Validation.UrlValidation;

public class UrlValidationTest {
    @Test
    public void testValidateUrlRequiresNoSubdomainEnsuresFalse(){
        // Arrange
        String url = "https://.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("URL is missing a valid subdomain", result);
    }

    @Test
    public void testValidateUrlRequiresSubdomainEnsuresTrue(){
        // Arrange
        String url = "https://www.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("URL contains a valid subdomain", result);
    }

    @Test
    public void testValidateUrlStartsWithoutProtocolEnsuresFalse() {
        // Arrange
        String url = "www.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("URL is missing a valid protocol", result);
    }

    @Test
    public void testValidateUrlStartsWithProtocolEnsuresTrue() {
        // Arrange
        String url = "http://www.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("URL contains a valid protocol", result);
    }

    @Test
    public void testValidateUrlRequiresNoDomainNameEnsuresFalse(){
        // Arrange
        String url = "http://www..nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("URL is missing a valid domain name", result);
    }

    @Test
    public void testValidateUrlRequiresDomainNameEnsuresTrue(){
        // Arrange
        String url = "http://www.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("URL contains a valid domain name", result);
    }

    @Test
    public void testValidateUrlRequiresNoTLDEnsuresFalse(){
        // Arrange
        String url = "https://www.testsite.";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertFalse("URL is missing a top level domain", result);
    }

    @Test
    public void testValidateUrlRequiresTLDEnsuresTrue(){
        // Arrange
        String url = "https://www.testsite.nl";

        // Act
        Boolean result = UrlValidation.validateUrl(url);

        // Assert
        assertTrue("URL has a top level domain", result);
    }
}
