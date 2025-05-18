package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactPage extends BasePage {

    @FindBy(id = "forename") private WebElement forenameInput;
    @FindBy(id = "email") private WebElement emailInput;
    @FindBy(id = "message") private WebElement messageInput;
    @FindBy(xpath = "//a[text()='Submit']") private WebElement submitBtn;
    @FindBy(css = ".alert.alert-success") private WebElement successMsg;
//    @FindBy(id = "forename-err") private WebElement forenameError;
//    @FindBy(id = "email-err") private WebElement emailError;
//    @FindBy(id = "message-err") private WebElement messageError;
    @FindBy(css = ".alert-error li") private WebElement errorMessages;
    @FindBy(css = ".alert-error")
    WebElement headerErrorMessage;

    @FindBy(css = "span.help-inline")
    List<WebElement> fieldErrorMessages;
    
    public ContactPage(WebDriver driver) {
        super(driver);
    }
    
        public void clickSubmit() {
        submitBtn.click();
    }

    public void fillMandatoryFields(String forename, String email, String message) {
        forenameInput.sendKeys(forename);
        emailInput.sendKeys(email);
        messageInput.sendKeys(message);
    }


    public void verifyInitialErrorMessages(SoftAssert softAssert) {
        softAssert.assertEquals(headerErrorMessage.getText().trim(),
                "We welcome your feedback - but we won't get it unless you complete the form correctly.",
                "Header error message mismatch");

        String[] expectedMessages = { "Forename is required", "Email is required", "Message is required" };
        for (String expected : expectedMessages) {
            boolean found = false;
            for (WebElement error : fieldErrorMessages) {
                if (error.getText().equals(expected)) {
                    found = true;
                    break;
                }
            }
            softAssert.assertTrue(found, "Expected error message not found: " + expected);
        }
    }
    
    public void verifyErrorsAreCleared(SoftAssert softAssert) {
        // After filling the fields, error messages should be cleared
        for (WebElement error : fieldErrorMessages) {
            softAssert.assertTrue(error.getText().isEmpty(), "Error not cleared for: " + error.getAttribute("id"));
        }
    }
    public String getSuccessMessage() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOf(successMsg));
        return successMsg.getText().trim();
    }
    
    
}

    




   