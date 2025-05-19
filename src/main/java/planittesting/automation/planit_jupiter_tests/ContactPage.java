package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ContactPage extends BasePage {

    @FindBy(id = "forename") private WebElement forenameInput;
    @FindBy(id = "email") private WebElement emailInput;
    @FindBy(id = "message") private WebElement messageInput;
    @FindBy(xpath = "//a[text()='Submit']") private WebElement submitBtn;
    @FindBy(css = ".alert.alert-success") private WebElement successMsg;
    @FindBy(css = ".alert-error li") private WebElement errorMessages;
    @FindBy(css = ".alert-error") private WebElement headerErrorMessage;
    @FindBy(css = "span.help-inline") private List<WebElement> fieldErrorMessages;

    public ContactPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickSubmit() {
        submitBtn.click();
    }

    public void fillMandatoryFields(String forename, String email, String message) {
        forenameInput.clear();
        forenameInput.sendKeys(forename);
        emailInput.clear();
        emailInput.sendKeys(email);
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    public String getHeaderErrorMessage() {
        return headerErrorMessage.getText().trim();
    }

    public List<String> getFieldErrorMessages() {
        return fieldErrorMessages.stream().map(WebElement::getText).toList();
    }

    public List<WebElement> getFieldErrorElements() {
        return fieldErrorMessages;
    }

    public String getSuccessMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement alert = wait.until(ExpectedConditions.visibilityOf(successMsg));
        return alert.getText().trim();
    }
}
