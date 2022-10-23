Feature: Verify upload csv with all correct fields will be successful

  Background: To launch the browser
    Given Launch the browser and login clerk account

  @regression
  Scenario: Verify upload csv with all correct fields will be successful
    Given Csv file with correct fields
    Then Upload via the csv uploading page and verify the result
    And Verify the new created correct hero from the working class heroes table record
    And Close the browser