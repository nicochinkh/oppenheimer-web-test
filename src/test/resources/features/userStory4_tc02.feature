Feature: Verify searching by the natid or name, the system can list the specific workingclass heroes

  Background: To launch the browser
    Given Launch the browser and login gov account

  @regression
  Scenario: Verify searching by the natid, the system can list the specific workingclass heroes
    Given Gov user open the Governor Dashboard page
    Then Click on the List All button
    Then Search natid
    And Verify the natid search result matches the database
    And Close the browser

  Scenario: Verify searching by the name, the system can list the specific workingclass heroes
    Given Gov user open the Governor Dashboard page
    Then Click on the List All button
    Then Search name
    And Verify the name search result matches the database
    And Close the browser