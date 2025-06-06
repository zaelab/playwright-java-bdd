Feature: User Login

  @login @smoke
  Scenario: Successful login
    Given the user is on the login page
    When the user enters valid username "testuser" and password "password123"
    And the user clicks the login button
    Then the user should be logged in successfully