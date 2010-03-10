package com.linkare.validation.identification;

import java.util.Locale;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Paulo Zenida - Linkare TI
 * 
 */
public class IdentificationCardNumberValidatorTest {

    private Locale portugalLocale;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	portugalLocale = new Locale("pt", "PT");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.linkare.validation.identification.IdentificationCardNumberValidator#isValid(java.lang.String)}.
     */
    @Test
    public void testIsValidString() {
	Assert.assertTrue("It should not fail, since the check digit was included", IdentificationCardNumberValidator.isValid("120972778"));
	Assert.assertFalse("It should fail, since the check digit was not included", IdentificationCardNumberValidator.isValid("12097277"));
	Assert.assertFalse("It should fail, since the number is not valid", IdentificationCardNumberValidator.isValid("d12097277"));
	Assert.assertFalse("It should fail, since the number is empty", IdentificationCardNumberValidator.isValid(""));
	Assert.assertFalse("It should fail, since the number is null", IdentificationCardNumberValidator.isValid(null));
    }

    /**
     * Test method for
     * {@link com.linkare.validation.identification.IdentificationCardNumberValidator#isValid(com.linkare.validation.identification.IdentificationCardNumberValidator.IdentificationCardCountry, java.lang.String)}
     * .
     */
    @Test
    public void testIsValidIdentificationCardCountryString() {
	Assert.assertTrue("It should not fail, since the check digit was included", IdentificationCardNumberValidator.isValid(portugalLocale, "120972778"));
	Assert.assertFalse("It should fail, since the check digit was not included", IdentificationCardNumberValidator.isValid(portugalLocale, "12097277"));
	Assert.assertFalse("It should fail, since the number is not valid", IdentificationCardNumberValidator.isValid(portugalLocale, "d12097277"));
	Assert.assertFalse("It should fail, since the number is empty", IdentificationCardNumberValidator.isValid(portugalLocale, ""));
	Assert.assertFalse("It should fail, since the number is null", IdentificationCardNumberValidator.isValid(portugalLocale, null));
	try {
	    IdentificationCardNumberValidator.isValid(new Locale("pt", "BR"), "120972778");
	    Assert.fail("It was supported to throw one UnsupportedOperationException");
	} catch (UnsupportedOperationException expected) {
	    Assert.assertTrue("It should have thrown one UnsupportedOperationException", "Not implemented yet".equals(expected.getMessage()));
	}
    }
}
