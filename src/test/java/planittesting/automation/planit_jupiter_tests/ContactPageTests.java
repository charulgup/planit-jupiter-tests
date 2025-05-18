package planittesting.automation.planit_jupiter_tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ContactPageTests extends BaseTest {

    @Test
             public void testContactFormErrorMessages() {
    	     ContactPage Contact = new HomePage(driver).goToContactPage();
    	    
    	     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	     wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("forename"))); 
    	     Contact.clickSubmit();
            SoftAssert softAssert = new SoftAssert();
            Contact.verifyInitialErrorMessages(softAssert);

            Contact.fillMandatoryFields("Charul", "Charul@test.com", "This is my Test message");
            Contact.verifyErrorsAreCleared(softAssert);

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
    Thread.sleep(5000);
    

    String expectedSuccessMsg = "Thanks John, we appreciate your feedback.";
    String actualSuccessMsg = contact.getSuccessMessage();

    softAssert.assertEquals(actualSuccessMsg, expectedSuccessMsg, "Success message mismatch");

    softAssert.assertAll();
    
}
}
     
