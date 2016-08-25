package com.linkare.validation.identification;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * Implementation according to https://www.cartaodecidadao.pt/images/stories/Algoritmo_Num_Documento_CC.pdf, in 25/08/2016
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class CitizenCardNumberValidator {

    private static final int CITIZEN_CARD_LENGTH = 12;

    private static final short MIN_CHAR = (short) 'A';

    private static final short MAX_CHAR = (short) 'Z';

    private static final short FIRST_CHAR_NUMBER = 10;

    private CitizenCardNumberValidator() {
    }

    /**
     * 
     * @param number
     *            the number to be checked
     * @return true if the number is valid. It returns false, otherwise
     * @throws IllegalArgumentException
     *             if the citizen card has not the appropriate length or if contains an invalid char/number
     */
    public static boolean isValid(final String number) {
	int sum = 0;
	boolean secondDigit = false;
	if (number.length() != CITIZEN_CARD_LENGTH) {
	    throw new IllegalArgumentException("The citizen card has hot the appropriate length");
	}
	for (int i = number.length() - 1; i >= 0; --i) {
	    int valor = convertToNumber(number.charAt(i));
	    if (secondDigit) {
		valor *= 2;
		if (valor > 9)
		    valor -= 9;
	    }
	    sum += valor;
	    secondDigit = !secondDigit;
	}
	return (sum % 10) == 0;
    }

    private static int convertToNumber(final char letter) {
	final String s = String.valueOf(letter);
	final short letterNumber = (short) letter;
	if (NumberUtils.isDigits(s)) {
	    return Integer.valueOf(s);
	} else if (letterNumber >= MIN_CHAR && letterNumber <= MAX_CHAR) {
	    return letterNumber - MIN_CHAR + FIRST_CHAR_NUMBER;
	}
	throw new IllegalArgumentException("The citizen card contains an invalid number/char");
    }
}