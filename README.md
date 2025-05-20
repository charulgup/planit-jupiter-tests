# Planit Jupiter Automation Tests

This project contains automated UI tests for the Planit Jupiter Toys demo site. The tests are implemented using **Java**, **Selenium WebDriver**, and **TestNG**, following the **Page Object Model (POM)** framework design. It is built and managed using **Maven**, with integration support for **Jenkins** CI/CD pipelines.

## 🔧 Tech Stack

- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- Page Object Model (POM)  
- Jenkins (CI/CD)  
- Git & GitHub  

## 📂 Project Structure

planit-jupiter-tests/  
├── src/  
│   ├── main/  
│   │   └── java/planittesting/automation/pages/   → Page classes  
│   └── test/  
│       └── java/planittesting/automation/tests/   → Test classes  
├── pom.xml                                         → Maven dependencies  
└── README.md                                       → Project documentation                                    

## ✅ Features Covered

- **Contact Form Validation**  
  - Mandatory field checks  
  - Error message validations  
  - Successful form submission  
- **Shop and Cart Functionality**  
  - Product purchase flow  
  - Price and quantity validation  
  - Cart total calculation and updates  

## 🚀 How to Run Tests

1. Clone the repository:  
   `git clone https://github.com/charulgup/planit-jupiter-tests.git`  
   `cd planit-jupiter-tests`  

2. Build the project:  
   `mvn clean install`  

3. Run the tests:  
   `mvn test`  

## 🧪 CI/CD

This project is compatible with Jenkins for continuous testing. You can configure a Maven job in Jenkins and point it to this repository to enable automated builds and test execution.

## 📌 Notes

- Tests follow clean code principles and are structured to be easily maintainable and extendable.  
- Explicit waits are used for handling dynamic content and ensuring reliable test execution.

## 📬 Contact

**Charul Gupta**  
📧 charulbgupta@gmail.com  
🔗 https://www.linkedin.com/in/charul-gupta/


## 🧾 TestNG Suite Configuration

The TestNG suite is defined in the `testng.xml` file located at the root of the project. It allows grouping and parallel execution of test classes.

### Example Structure of `testng.xml`:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Planit Jupiter Test Suite">
    <test name="Jupiter Tests">
        <classes>
            <class name="planittesting.automation.tests.ContactTests"/>
            <class name="planittesting.automation.tests.ShopTests"/>
        </classes>
    </test>
</suite>
```

- `ContactTests` class verifies validation errors and successful submission for the contact form.
- `ShopTests` class verifies shopping cart functionality and price validations.