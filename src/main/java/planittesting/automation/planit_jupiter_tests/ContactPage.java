package planittesting.automation.planit_jupiter_tests;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ContactPage extends BasePage {

	// WebElements mapped to form fields and messages using @FindBy annotation
	@FindBy(id = "forename")
	private WebElement forenameInput; // Input field for Forename
	@FindBy(id = "email")
	private WebElement emailInput; // Input field for Email
	@FindBy(id = "message")
	private WebElement messageInput; // Input field for Message
	@FindBy(xpath = "//a[text()='Submit']")
	private WebElement submitBtn; // Submit button element
	@FindBy(css = ".alert.alert-success")
	private WebElement successMsg; // Success alert message element
	@FindBy(css = ".alert alert-success")
	private WebElement errorMessages; // List item elements for individual error messages
	@FindBy(css = "div.alert.alert-error.ng-scope")
	private WebElement headerErrorMessage; // Header error message element
	@FindBy(css = "span.help-inline")
	private List<WebElement> fieldErrorMessages; // Inline error messages per field

	// Constructor calls BasePage constructor to initialize WebDriver and elements
	public ContactPage(WebDriver driver) {
		super(driver);
	}

	// Clicks the Submit button on the contact form
	public void clickSubmit() {
		submitBtn.click();
	}

	// Returns the header-level error message displayed on the form (if any)
	public String getHeaderErrorMessage() {
		return headerErrorMessage.getText().trim();
	}

	// Returns a list of strings representing all individual field error messages
	public List<String> getFieldErrorMessages() {
		return fieldErrorMessages.stream().map(WebElement::getText).toList();
	}

	// Returns the list of WebElements representing inline error messages (for
	// validation in tests)
	public List<WebElement> getFieldErrorElements() {
		return fieldErrorMessages;
	}

	// Fills the mandatory fields: forename, email, and message with given values
	private String enteredForename;

	public void fillMandatoryFields(String forename, String email, String message) {
		wait.until(ExpectedConditions.visibilityOf(forenameInput));
		forenameInput.clear();
		forenameInput.sendKeys(forename);
		enteredForename = forename;

		emailInput.clear();
		emailInput.sendKeys(email);

		messageInput.clear();
		messageInput.sendKeys(message);
	}

	// Returns Forename which is used header message dynamically
	public String getEnteredForename() {
		return enteredForename;
	}

	// Waits for the success message to appear and returns its trimmed text
	public String getSuccessMessage() {
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	WebElement alert = wait.until(ExpectedConditions.visibilityOf(successMsg));
		return alert.getText().trim();
	}
	
}
