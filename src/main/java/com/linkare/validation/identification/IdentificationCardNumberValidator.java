package com.linkare.validation.identification;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class IdentificationCardNumberValidator {

    private static final int DIVISION_FACTOR = 11;

    private static final char PADDING_CHAR = '0';

    private static final int ID_CARD_NUMBER_LENGTH = 9;

    private static final String DEFAULT_LANGUAGE = "pt";

    private static final String DEFAULT_COUNTRY = "PT";

    private static final Locale DEFAULT_ID_CARD_COUNTRY = new Locale(DEFAULT_LANGUAGE, DEFAULT_COUNTRY);

    private static final String MAXIMUM_SCORE_MESSAGE = "The identification card is valid";

    private static final String MINIMUM_SCORE_MESSAGE = "The identification card number is is blank";

    private static final String LEVEL1_SCORE_MESSAGE = "The identification card number is not numeric";

    private static final String LEVEL2_SCORE_MESSAGE = "The identification card number has not the appropriate length (" + ID_CARD_NUMBER_LENGTH + ")";

    private static final String LEVEL3_SCORE_MESSAGE = "The identification card number check digit is not valid";

    public static final int MAXIMUM_SCORE = 4;

    public static final int LEVEL1_SCORE = 1;

    public static final int LEVEL2_SCORE = 2;

    public static final int LEVEL3_SCORE = 3;

    public static final int MINIMUM_SCORE = 0;

    private IdentificationCardNumberValidator() {
    }

    /**
     * 
     * @param number
     *            the identification card number to be validated
     * @return true if the <code>number</code> is valid for the <code>DEFAULT_ID_CARD_COUNTRY</code> (PT). It returns false otherwise.
     * 
     * @see IdentificationCardNumberValidator#isValid(IdentificationCardCountry, String)
     */
    public static boolean isValid(final String number) {
	return isValid(DEFAULT_ID_CARD_COUNTRY, number);
    }

    /**
     * 
     * This method validates if a given number for an identification document for a given country is valid. For the default country, PT (Portugal), this number
     * is supposed to have 9 digits, which includes the control digit.
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
     *            the number to be checked
     * 
     * @return true if the <code>number</code> is a valid identification card number in Portugal.
     */
    private static boolean isValidPtIdCard(final String number) {
	return scorePtIdCard(number) == MAXIMUM_SCORE;
    }

    /**
     * 
     * @param number
     * 
     * @return returns the <code>score</code> for the passed in <code>number</code> for the <code>DEFAULT_ID_CARD_COUNTRY</code> (PT). The score represents the
     *         number of successfully evaluated rules the <code>number</code> has been submitted to.
     * 
     * @see IdentificationCardNumberValidator#scoreIdCard(Locale, String)
     * 
     */
    public static int scoreIdCard(final String number) {
	return scoreIdCard(DEFAULT_ID_CARD_COUNTRY, number);
    }

    /**
     * 
     * @param locale
     *            the identification card country to be validated.
     * @param number
     *            the identification card number to be validated
     * @return returns the <code>score</code> for the passed in <code>number</code> and the <code>locale</code> (PT). The score represents the number of
     *         successfully evaluated rules the <code>number</code> has been submitted to.
     * 
     *         The returned <code>score</code> values are:
     * 
     *         <table width="90%">
     *         <tr>
     *         <th>SCORE</th>
     *         <th>VALUE</th>
     *         <th>DESCRIPTION</th>
     *         </tr>
     *         <tr>
     *         <td>MINIMUM_SCORE</td>
     *         <td>0</td>
     *         <td>MINIMUM_SCORE_MESSAGE</td>
     *         </tr>
     *         <tr>
     *         <td>LEVEL1_SCORE</td>
     *         <td>1</td>
     *         <td>LEVEL1_SCORE_MESSAGE</td>
     *         </tr>
     *         <tr>
     *         <td>LEVEL2_SCORE</td>
     *         <td>2</td>
     *         <td>LEVEL2_SCORE_MESSAGE</td>
     *         </tr>
     *         <tr>
     *         <td>LEVEL3_SCORE</td>
     *         <td>3</td>
     *         <td>LEVEL3_SCORE_MESSAGE</td>
     *         </tr>
     *         <tr>
     *         <td>MAXIMUM_SCORE</td>
     *         <td>4</td>
     *         <th>MAXIMUM_SCORE_MESSAGE</td>
     *         </tr>
     *         </table>
     * 
     * @throws UnsupportedOperationException
     *             when the <code>locale</code> is not PT, since no other countries are supported yet.
     */
    public static int scoreIdCard(final Locale locale, final String number) {
	if (locale != null && DEFAULT_COUNTRY.equals(locale.getCountry())) {
	    return scorePtIdCard(number);
	} else {
	    throw new UnsupportedOperationException("Not implemented yet");
	}
    }

    private static int scorePtIdCard(final String number) {
	int scoreBlank = scoreBlank(number);
	int scoreDigits = scoreBlank == 1 ? scoreDigits(number) : 0;
	final String paddedNumber = StringUtils.leftPad(number, ID_CARD_NUMBER_LENGTH, PADDING_CHAR);
	int scoreLength = scoreDigits == 1 ? scoreLength(paddedNumber) : 0;
	int scoreCheckDigit = scoreLength == 1 ? scoreCheckDigit(paddedNumber) : 0;
	return scoreBlank + scoreDigits + scoreLength + scoreCheckDigit;
    }

    public static boolean isSuccess(final int score) {
	return score == MAXIMUM_SCORE;
    }

    public static boolean isError(final int score) {
	return score == MINIMUM_SCORE;
    }

    public static boolean isWarning(final int score) {
	return !isSuccess(score) && !isError(score);
    }

    public static String getMessage(final int score) {
	switch (score) {
	case MAXIMUM_SCORE:
	    return MAXIMUM_SCORE_MESSAGE;
	case LEVEL1_SCORE:
	    return LEVEL1_SCORE_MESSAGE;
	case LEVEL2_SCORE:
	    return LEVEL2_SCORE_MESSAGE;
	case LEVEL3_SCORE:
	    return LEVEL3_SCORE_MESSAGE;
	case MINIMUM_SCORE:
	    return MINIMUM_SCORE_MESSAGE;
	default:
	    throw new UnsupportedOperationException("Not supported for score " + score);
	}
    }

    /**
     * 
     * @param number
     *            the number to be checked for blanking.
     * 
     * @return 1 if the <code>number</code> is not blank. It returns 0 otherwise.
     */
    private static int scoreBlank(final String number) {
	return StringUtils.isBlank(number) ? 0 : 1;
    }

    /**
     * 
     * @param number
     *            the number to be checked for digits.
     * 
     * @return 1 if the <code>number</code> has only digits. It returns 0 otherwise.
     */
    private static int scoreDigits(final String number) {
	return NumberUtils.isDigits(number) ? 1 : 0;
    }

    /**
     * 
     * @param number
     *            the number to be checked for length.
     * 
     * @return 1 if the <code>number</code> has the length <code>ID_CARD_NUMBER_LENGTH</code>. It returns 0 otherwise.
     */
    private static int scoreLength(final String number) {
	return number.length() == ID_CARD_NUMBER_LENGTH ? 1 : 0;
    }

    /**
     * 
     * @param number
     *            the number to be checked for check digit.
     * 
     * @return 1 if the <code>number</code>'s check digit is ok. It returns 0 otherwise.
     */
    private static int scoreCheckDigit(final String number) {
	final String numberWithoutCheckDigit = number.substring(0, number.length() - 1);
	final int scoreDigit = Integer.valueOf(String.valueOf(number.charAt(number.length() - 1)));
	return scoreDigit == calculateCheckDigit(numberWithoutCheckDigit) ? 1 : 0;
    }

    private static int calculateCheckDigit(final String number) {
	final char[] numbers = number.toCharArray();
	int result = 0;
	int multiplier = 2;
	for (int i = (numbers.length - 1); i >= 0; i--) {
	    int n = Integer.valueOf(String.valueOf(numbers[i]));
	    result += (n * multiplier++);
	}
	int divisionByEleven = (int) Math.ceil((double) result / DIVISION_FACTOR);
	int multiplication = divisionByEleven * DIVISION_FACTOR;
	int finalResult = multiplication - result;
	return finalResult > 9 ? 0 : finalResult;
    }
}