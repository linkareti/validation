package com.linkare.validation.identification;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class TaxNumberValidator {

    private static final int DIVISION_FACTOR = 11;

    private static final char PADDING_CHAR = '0';

    private static final int ID_CARD_NUMBER_LENGTH = 9;

    private static final IdentificationCardCountry DEFAULT_ID_CARD_COUNTRY = IdentificationCardCountry.PT;

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
     * @param identificationCardCountry
     *            the identification card country to be validated.
     * @param number
     *            the identification card number to be validated
     * @return true if the <code>number</code> is valid in the <code>identificationCardCountry</code> passed in. It returns false otherwise.
     * 
     * @throws UnsupportedOperationException
     *             when the <code>identificationCardCountry</code> is not PT, since no other countries are supported yet.
     * 
     */
    public static boolean isValid(final IdentificationCardCountry identificationCardCountry, final String number) {
	switch (identificationCardCountry) {
	case PT:
	    return isValidPtIdCard(number);
	default:
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

    public enum IdentificationCardCountry {
	PT;
    }

    public static void main(String[] args) {
	System.out.println(TaxNumberValidator.isValid("217186246"));
	System.out.println(TaxNumberValidator.isValid("213156024"));
    }
}