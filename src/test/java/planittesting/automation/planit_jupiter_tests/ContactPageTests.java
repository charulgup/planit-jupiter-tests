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

    @Test
    public void testContactFormErrorMessages() {
        ContactPage contact = new HomePage(driver).goToContactPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forename")));

        contact.clickSubmit();

        SoftAssert softAssert = new SoftAssert();

        // Verify header error message
        String actualHeaderMsg = contact.getHeaderErrorMessage();
        softAssert.assertEquals(actualHeaderMsg,
            "We welcome your feedback - but we won't get it unless you complete the form correctly.",
            "Header error message mismatch");

        // Verify individual field error messages
        List<String> actualErrorMessages = contact.getFieldErrorMessages();
        String[] expectedMessages = {"Forename is required", "Email is required", "Message is required"};

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

        // Fill form and verify error messages are cleared
        contact.fillMandatoryFields("Charul", "charul@test.com", "This is a test message");

        List<WebElement> errorElements = contact.getFieldErrorElements();
        for (WebElement errorElement : errorElements) {
            softAssert.assertTrue(errorElement.getText().isEmpty(),
                "Error not cleared for element with text: " + errorElement.getText());
        }

        softAssert.assertAll();
    }

    @Test(invocationCount = 2)
    public void testSuccessfulContactFormSubmission() throws InterruptedException {
        ContactPage contact = new HomePage(driver).goToContactPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forename")));

        contact.fillMandatoryFields("John", "john@example.com", "This is a test message");
        contact.clickSubmit();

        SoftAssert softAssert = new SoftAssert();
        Thread.sleep(5000); // Optional: Replace with a better wait if needed

        String expectedSuccessMsg = "Thanks John, we appreciate your feedback.";
        String actualSuccessMsg = contact.getSuccessMessage();

        softAssert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message mismatch");
        softAssert.assertAll();
    }
}
