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
     * Test method for {@link com.linkare.validation.identification.IdentificationCardNumberValidator#isValid(java.util.Locale, java.lang.String)}
     * 
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

    /**
     * Test method for {@link com.linkare.validation.identification.IdentificationCardNumberValidator#scorePtIdCard(java.lang.String)}
     * 
     */
    @Test
    public void testScoreIdCardString() {
	// score 0
	Assert.assertTrue("It should return 0 as score", IdentificationCardNumberValidator.scoreIdCard("") == IdentificationCardNumberValidator.MINIMUM_SCORE);
	Assert
	      .assertTrue("It should return 0 as score", IdentificationCardNumberValidator.scoreIdCard(null) == IdentificationCardNumberValidator.MINIMUM_SCORE);
	Assert.assertTrue("It should return 0 as score", IdentificationCardNumberValidator.scoreIdCard(" ") == IdentificationCardNumberValidator.MINIMUM_SCORE);

	// score 1
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard("13414d") == IdentificationCardNumberValidator.LEVEL1_SCORE);
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard("-113414") == IdentificationCardNumberValidator.LEVEL1_SCORE);
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard("DDDS") == IdentificationCardNumberValidator.LEVEL1_SCORE);

	// score 2
	Assert.assertTrue("It should return 2 as score",
			  IdentificationCardNumberValidator.scoreIdCard("1234567890") == IdentificationCardNumberValidator.LEVEL2_SCORE);

	// score 3
	Assert.assertTrue("It should return 3 as score",
			  IdentificationCardNumberValidator.scoreIdCard("012345678") == IdentificationCardNumberValidator.LEVEL3_SCORE);

	// score 3
	Assert.assertTrue("It should return 4 as score",
			  IdentificationCardNumberValidator.scoreIdCard("120972778") == IdentificationCardNumberValidator.MAXIMUM_SCORE);
    }

    public void testScoreIdCardCountryString() {
	// score 0
	Assert.assertTrue("It should return 0 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "") == IdentificationCardNumberValidator.MINIMUM_SCORE);
	Assert.assertTrue("It should return 0 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, null) == IdentificationCardNumberValidator.MINIMUM_SCORE);
	Assert.assertTrue("It should return 0 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, " ") == IdentificationCardNumberValidator.MINIMUM_SCORE);

	// score 1
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "13414d") == IdentificationCardNumberValidator.LEVEL1_SCORE);
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "-113414") == IdentificationCardNumberValidator.LEVEL1_SCORE);
	Assert.assertTrue("It should return 1 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "DDDS") == IdentificationCardNumberValidator.LEVEL1_SCORE);

	// score 2
	Assert.assertTrue("It should return 2 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "1234567890") == IdentificationCardNumberValidator.LEVEL2_SCORE);

	// score 3
	Assert.assertTrue("It should return 3 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "012345678") == IdentificationCardNumberValidator.LEVEL3_SCORE);

	// score 3
	Assert.assertTrue("It should return 4 as score",
			  IdentificationCardNumberValidator.scoreIdCard(portugalLocale, "120972778") == IdentificationCardNumberValidator.MAXIMUM_SCORE);

	try {
	    IdentificationCardNumberValidator.scoreIdCard(new Locale("pt", "BR"), "120972778");
	    Assert.fail("It was supported to throw one UnsupportedOperationException");
	} catch (UnsupportedOperationException expected) {
	    Assert.assertTrue("It should have thrown one UnsupportedOperationException", "Not implemented yet".equals(expected.getMessage()));
	}
    }
}