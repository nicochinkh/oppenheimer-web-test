Feature: Verify clicking the List All button, the system can list all the workingclass heroes

  Background: To launch the browser
    Given Launch the browser and login gov account

  @regression
  Scenario: Verify clicking the List All button, the system can list all the workingclass heroes
    Given Gov user open the Governor Dashboard page
    Then Click on the List All button
    And Verify the List data matches the database
    And Close the browser