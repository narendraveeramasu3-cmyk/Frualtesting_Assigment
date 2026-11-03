
# Q3 – Locators and Prompting

## Q3(a) CSS Selectors and XPath

### Given HTML elements

The signup form contains:
1. Password input field
2. Create account button
3. Password validation error message

### 1. CSS Selectors

| Element | CSS Selector |
|---|---|
| Password field | `input[data-testid='password-input']` |
| Submit button | `button[data-testid='signup-btn']` |
| Error message | `span[role='alert']` |

### 2. XPath Selectors

| Element | XPath |
|---|---|
| Password field | `//input[@data-testid='password-input']` |
| Submit button | `//button[@data-testid='signup-btn']` |
| Error message | `//span[@role='alert']` |

### 3. Preferred Locator Strategy

CSS selectors using stable attributes such as `data-testid` are preferred for these elements.

Reasons:
1. They are simple and readable.
2. They are easy to maintain.
3. They do not depend on the page layout.
4. They can directly identify elements using stable attributes.

XPath is useful when locating elements based on relationships with other elements or when more complex conditions are required.

**Conclusion:** CSS selectors are preferred for the given elements because stable attributes are already available in the HTML.

---

## Q3(b) AI Prompt Engineering

### Prompt for Generating Signup Form Test Cases

**Role:**

Act as a Senior QA Automation Engineer with expertise in software testing and test case design.

**Context:**

I am testing a signup form containing:
- Email input field
- Password input field
- Create account submit button
- Password validation error message

The HTML attributes are:

- Email: `id="email"`, `type="email"`
- Password: `data-testid="password-input"`
- Submit button: `data-testid="signup-btn"`
- Error message: `role="alert"`

The displayed validation message is:

"Password must be at least 8 characters"

**Task:**

Generate comprehensive test cases for the signup form, including:

1. Valid email and valid password.
2. Invalid email format.
3. Password shorter than 8 characters.
4. Empty password.
5. Empty email.
6. Both fields empty.
7. Submit button interaction.
8. Password validation error message.

**Constraints:**

1. Do not invent functionality that is not specified.
2. Clearly identify assumptions and missing requirements.
3. Include positive and negative test scenarios.
4. Avoid duplicate test cases.
5. Use clear and professional QA terminology.
6. Make test cases reproducible.

**Output Format:**

Generate a table containing:

| Column | Description |
|---|---|
| Test Case ID | Unique test case identifier |
| Test Scenario | Scenario being tested |
| Preconditions | Required initial conditions |
| Test Steps | Steps to execute |
| Test Data | Input values |
| Expected Result | Expected application behaviour |
| Priority | High, Medium or Low |

Also provide:
- Total number of test cases.
- Positive test case count.
- Negative test case count.
- Assumptions and clarification points.

---

**End of Q3 Answers**
