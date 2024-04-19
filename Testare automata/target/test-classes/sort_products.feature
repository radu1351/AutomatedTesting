Feature: Sort products by price

  Scenario: Verify that products are sorted by price in descending order
    Given I am on the Parfumis home page
    And I navigate to the catalog section
    Then I verify that products are sorted correctly

