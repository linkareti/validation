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
public class TaxNumberValidatorTest {

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
     * Test method for {@link com.linkare.validation.identification.TaxNumberValidator#isValid(java.lang.String)}.
     */
    @Test
    public void testIsValidString() {
	Assert.assertTrue("It should not fail, since the check digit was included", TaxNumberValidator.isValid("217186246"));
	Assert.assertFalse("It should fail, since the number is not valid", TaxNumberValidator.isValid("d17186246"));
	Assert.assertFalse("It should fail, since the number is empty", TaxNumberValidator.isValid(""));
	Assert.assertFalse("It should fail, since the number is null", TaxNumberValidator.isValid(null));
    }

    /**
     * Test method for
     * {@link com.linkare.validation.identification.TaxNumberValidator#isValid(com.linkare.validation.identification.TaxNumberValidator.IdentificationCardCountry, java.lang.String)}
     * .
     */
    @Test
    public void testIsValidIdentificationCardCountryString() {
	Assert.assertTrue("It should not fail, since the check digit was included", TaxNumberValidator.isValid(portugalLocale, "217186246"));
	Assert.assertFalse("It should fail, since the number is not valid", TaxNumberValidator.isValid(portugalLocale, "d12097277"));
	Assert.assertFalse("It should fail, since the number is empty", TaxNumberValidator.isValid(portugalLocale, ""));
	Assert.assertFalse("It should fail, since the number is null", TaxNumberValidator.isValid(portugalLocale, null));
	try {
	    TaxNumberValidator.isValid(new Locale("pt", "BR"), "120972778");
	    Assert.fail("It was supported to throw one UnsupportedOperationException");
	} catch (UnsupportedOperationException expected) {
	    Assert.assertTrue("It should have thrown one UnsupportedOperationException", "Not implemented yet".equals(expected.getMessage()));
	}
    }
}