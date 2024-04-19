Feature: Search for products
  As a user
  I want to be able to search for products
  So that I can find the desired items easily

  Scenario: Search for a product by name
    Given I am on the home page for search
    When I search for "Dior Homme"
    Then I should see a list of "Dior Homme" in the search results
