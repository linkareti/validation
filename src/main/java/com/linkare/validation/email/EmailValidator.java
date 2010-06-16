package com.linkare.validation.email;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class EmailValidator {

    private EmailValidator() {
    }

    public static boolean isValid(final String email) {
	return org.apache.commons.validator.EmailValidator.getInstance().isValid(email);
    }
}