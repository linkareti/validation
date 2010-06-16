package com.linkare.validation.email;

/**
 * 
 * Utility class to validate the format of an email address.
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class EmailValidator {

    /**
     * Hide utility class constructor. All methods in this class should be accessed statically.
     */
    private EmailValidator() {
    }

    /**
     * 
     * @param email
     *            the email address whose format we want to validate
     * @return true if the email format is valid. Returns false otherwise. This method delegates its implementation in the
     *         <code>org.apache.commons.validator.EmailValidator</code> validator class from the commons-validator project from Apache.
     */
    public static boolean isValid(final String email) {
	return org.apache.commons.validator.EmailValidator.getInstance().isValid(email);
    }
}