Feature: Verify upload csv with some rows contain invalid fields will show the unsuccessful message

  Background: To launch the browser
    Given Launch the browser and login clerk account

  @regression
  Scenario: Verify upload csv with some rows contain invalid fields will show the unsuccessful message
    Given Csv file with one row contains incorrect data
    Then Upload via the csv uploading page and verify the unsuccessful result
    And Verify the new created correct hero from the working class heroes table record
    And Close the browser