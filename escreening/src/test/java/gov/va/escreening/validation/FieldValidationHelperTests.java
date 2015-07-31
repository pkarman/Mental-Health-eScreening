package gov.va.escreening.validation;

import org.junit.Assert;
import org.junit.Test;

public class FieldValidationHelperTests {

	@Test
	public void isFieldPopulatedTest() {
		boolean actual = FieldValidationHelper.isFieldPopulated(null);
		Assert.assertEquals(false, actual);

		actual = FieldValidationHelper.isFieldPopulated("");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldPopulated("value");
		Assert.assertEquals(true, actual);
	}

	@Test
	public void doesFieldContainAValidEmailAddressTest() {
		boolean actual = FieldValidationHelper.doesFieldContainAValidEmailAddress(null);
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("abc");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("abc@abc");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("@abc");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("@abc.com");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.doesFieldContainAValidEmailAddress("abc@abc.com");
		Assert.assertEquals(true, actual);
	}
	
	/* At least 8 chars
	Contains at least one digit
	Contains at least one lower alpha char and one upper alpha char
	Contains at least one char within a set of special char (@#%$^ etc.)
	Not containing blank, tab etc.  */
	@Test
	public void isFieldValidPasswordTest() {
		boolean actual = FieldValidationHelper.isFieldValidPassword(null);
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("");
		Assert.assertEquals(false, actual);
		
		//At least 8 chars
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$1");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$123");
		Assert.assertEquals(true, actual);
	
		//Contains at least one digit
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$abcd");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$123");
		Assert.assertEquals(true, actual);
		
		//Contains at least one lower alpha char and one upper alpha char
		actual = FieldValidationHelper.isFieldValidPassword("abcd$abcd");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$123");
		Assert.assertEquals(true, actual);		
		
		//Contains at least one char within a set of special char (@#$%^&+=)
		actual = FieldValidationHelper.isFieldValidPassword("abcd1abcd");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd@123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd#123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd$123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd%123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd^123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd&123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd+123");
		Assert.assertEquals(true, actual);	
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd=123");
		Assert.assertEquals(true, actual);	
		
		//Not containing blank
		actual = FieldValidationHelper.isFieldValidPassword("abcd abcd");
		Assert.assertEquals(false, actual);
		
		actual = FieldValidationHelper.isFieldValidPassword("Abcd@123");
		Assert.assertEquals(true, actual);	
	}
}
