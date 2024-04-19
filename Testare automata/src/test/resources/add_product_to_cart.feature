Feature: Add product to cart
  As a customer
  I want to be able to add a product to my cart
  So that I can purchase it later

  Scenario: Add product to cart
    Given I am on the home page
    When I add a product to the cart
    Then I should see the product in my cart
