Feature: Preview functionality

  Scenario: Preview : successful login
    Given the user is on the E-Library homepage
    When the user navigates to the login page
    And logs in with email "Elibrarydummy@gmail.com" and password "testpass123"
    And navigates to the homepage
    And opens the catalog page
    And the user clicks the preview button on a book
    Then the use clicks the access full text button

  Scenario: Preview : failed login 
    Given the user is on the E-Library homepage
    When the user navigates to the login page
    And logs in with email "Elibrarydummy@" and password "testpass123"
    Then the login fails
