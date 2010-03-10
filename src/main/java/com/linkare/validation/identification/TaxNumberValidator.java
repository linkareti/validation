package com.linkare.validation.identification;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.linkare.validation.identification.IdentificationCardNumberValidator.IdentificationCardCountry;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class TaxNumberValidator {

    private static final int DIVISION_FACTOR = 11;

    private static final char PADDING_CHAR = '0';

    private static final int ID_CARD_NUMBER_LENGTH = 9;

    private static final String DEFAULT_LANGUAGE = "pt";

    private static final String DEFAULT_COUNTRY = "PT";

    private static final Locale DEFAULT_ID_CARD_COUNTRY = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);

    private TaxNumberValidator() {
    }

    /**
     * 
     * @param number
     *            the identification card number to be validated
     * @return true if the <code>number</code> is valid for the <code>DEFAULT_ID_CARD_COUNTRY</code> (PT). It returns false otherwise.
     * 
     * @see TaxNumberValidator#isValid(IdentificationCardCountry, String)
     */
    public static boolean isValid(final String number) {
	return isValid(DEFAULT_ID_CARD_COUNTRY, number);
    }

    /**
     * 
     * This method validates if a given number for an identification document for a given country is valid. For the default country, PT (Portugal), this number
     * is supposed to have 9 digits.
     * 
     * @param locale
     *            the identification card country to be validated.
     * @param number
     *            the identification card number to be validated
     * @return true if the <code>number</code> is valid in the <code>locale</code> passed in. It returns false otherwise.
     * 
     * @throws UnsupportedOperationException
     *             when the <code>locale</code> is not PT, since no other countries are supported yet.
     * 
     */
    public static boolean isValid(final Locale locale, final String number) {
	if (locale != null && DEFAULT_COUNTRY.equals(locale.getCountry())) {
	    return isValidPtIdCard(number);
	} else {
	    throw new UnsupportedOperationException("Not implemented yet");
	}
    }

    /**
     * 
     * @param number
     * @return
     */
    private static boolean isValidPtIdCard(final String number) {
	if (StringUtils.isBlank(number) || !NumberUtils.isNumber(number)) {
	    return false;
	}
	final String paddedNumber = StringUtils.leftPad(number, ID_CARD_NUMBER_LENGTH, PADDING_CHAR);
	if (paddedNumber.length() > 9) {
	    return false;
	}
	final char[] numbers = paddedNumber.toCharArray();
	int result = 0;
	int multiplier = 1;
	for (int i = (numbers.length - 1); i >= 0; i--) {
	    int n = Integer.valueOf(String.valueOf(numbers[i]));
	    result += (n * multiplier++);
	}
	return (result % DIVISION_FACTOR) == 0;
    }
}