package com.linkare.validation.identification;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class TaxNumberValidator {

    private static final int MINIMUM_REMAINING = 2;

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
     * @see TaxNumberValidator#isValid(Locale, String)
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
	// 1. Validate size (9 digits)
	if (StringUtils.isBlank(number) || !NumberUtils.isDigits(number)) {
	    return false;
	}
	final String paddedNumber = StringUtils.leftPad(number, ID_CARD_NUMBER_LENGTH, PADDING_CHAR);
	if (paddedNumber.length() != ID_CARD_NUMBER_LENGTH) {
	    return false;
	}

	// 2. First digit must be one of 1, 2, 5, 6, 7, 8 ou 9
	if (!paddedNumber.startsWith("1") && !paddedNumber.startsWith("2") && !paddedNumber.startsWith("5") && !paddedNumber.startsWith("6")
		&& !paddedNumber.startsWith("7") && !paddedNumber.startsWith("8") && !paddedNumber.startsWith("9")) {
	    return false;
	}

	// 3. Control sum is calculated with the formula 9xd1 + 8xd2 + 7xd3 + 6xd4 + 5xd5 + 4xd6 + 3xd7 + 2xd8
	final char[] numbers = paddedNumber.toCharArray();
	int result = 0;
	int multiplier = ID_CARD_NUMBER_LENGTH;
	for (int i = 0; i < (numbers.length - 1); i++) {
	    int n = Integer.valueOf(String.valueOf(numbers[i]));
	    result += (n * multiplier--);
	}
	// 4. We calculate the remainder by 11
	int remaining = (result % DIVISION_FACTOR);

	// 5. If the remaining is below 0, the check digit is 0. Otherwise, is 11 subtracted by the remaining
	int checkDigit = (remaining < MINIMUM_REMAINING) ? 0 : (DIVISION_FACTOR - remaining);
	
	// 6. If the checkDigit matches the number's last digit, it is valid. Otherwise, it is not
	return checkDigit == Integer.valueOf(String.valueOf(numbers[ID_CARD_NUMBER_LENGTH - 1]));
    }
}