Feature: Filter products by gender

  Scenario: Verify that the correct number of men's fragrances is showed
    Given I am on the home page
    When I navigate to men's parfumes catalog
    Then I count how many men's parfumes are in the catalog
    Then I navigate to all parfumes catalog
    Then I verify that the same number of men's parfumes are diplayed


