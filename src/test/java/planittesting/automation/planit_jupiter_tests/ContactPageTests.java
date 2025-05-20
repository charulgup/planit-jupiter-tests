package planittesting.automation.planit_jupiter_tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ContactPageTests extends BaseTest {

	// Test case 1:
	// 1. From the home page go to contact page
	// 2. Click submit button
	// 3. Verify error messages
	// 4. Populate mandatory fields
	// 5. Validate errors are gone
	@Test
	public void testContactFormErrorMessages() {
		// Navigate to contact page from home page
		ContactPage contact = new HomePage(driver).goToContactPage();

		// Click submit button without filling form to trigger validation errors
		contact.clickSubmit();

		SoftAssert softAssert = new SoftAssert();

		// Verify header error message is displayed correctly
		String actualHeaderMsg = contact.getHeaderErrorMessage();
		softAssert.assertEquals(actualHeaderMsg,
				"We welcome your feedback - but we won't get it unless you complete the form correctly.",
				"Header error message mismatch");

		// Verify individual mandatory field error messages are displayed
		List<String> actualErrorMessages = contact.getFieldErrorMessages();
		String[] expectedMessages = { "Forename is required", "Email is required", "Message is required" };

		for (String expected : expectedMessages) {
			boolean found = false;
			for (String actual : actualErrorMessages) {
				if (actual.equals(expected)) {
					found = true;
					break;
				}
			}
			softAssert.assertTrue(found, "Expected error message not found: " + expected);
		}

		// Fill in mandatory fields to clear errors
		contact.fillMandatoryFields("Charul", "charul@test.com", "This is a test message");

		// Verify error messages are cleared after filling fields
		List<WebElement> errorElements = contact.getFieldErrorElements();
		for (WebElement errorElement : errorElements) {
			softAssert.assertTrue(errorElement.getText().isEmpty(),
					"Error not cleared for element with text: " + errorElement.getText());
		}

		softAssert.assertAll();
	}

	// Test case 2:
	// 1. From the home page go to contact page
	// 2. Populate mandatory fields
	// 3. Click submit button
	// 4. Validate successful submission message
	// Note: Run this test 5 times to ensure 100% pass rate (adjust invocationCount
	// accordingly)
	@Test(invocationCount = 5)
	public void testSuccessfulContactFormSubmission() throws InterruptedException {
		// Navigate to contact page from home page
		ContactPage contact = new HomePage(driver).goToContactPage();

		// Fill mandatory fields
		contact.fillMandatoryFields("Gupta", "Gupta@test.com", "This is a test message");

		// Click submit button to send form
		contact.clickSubmit();

		SoftAssert softAssert = new SoftAssert();

		// Wait for success message to appear
		contact.getSuccessMessage();

		// Verify success message text
		String expectedSuccessMsg = "Thanks " + contact.getEnteredForename() + ", we appreciate your feedback.";
		String actualSuccessMsg = contact.getSuccessMessage();

		softAssert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message mismatch");
		softAssert.assertAll();
	}
}
