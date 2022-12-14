Feature: Shopping
  As a customer, I want to know what the temperature is to decide which product I should buy.

  Scenario: Shopping according to temperature
  Shop for moisturizers if the weather is below 19 degrees.
  Shop for Sunscreens if the weather is above 34 degrees.

  Product adding conditions

  If it is moisturizers
  Shop for moisturizers if the weather is below 19 degrees.
  Add two moisturizers to your cart.
  First, select the least expensive moisturizer
  that contains Aloe. For your second moisturizer,
  select the least expensive moisturizer that contains almond.

  What if it is sunscreens
  Add two sunscreens to your cart.
  First, select the least expensive sunscreen that is SPF-50.
  For your second sunscreen, select the least expensive sunscreen that is SPF-30.
  Click on the cart when you are done.

    Given Random temperature
    When Click the shopping card according temperature
    Then Check the Page is the valid page according to temperature
      | temperature | page         |
      | below 19    | Moisturizers |
      | above 34    | Sunscreens   |
    Then then add product to card according to product adding conditions
    When Click on cart
    Then Verify that the shopping cart looks correct
    When fill out your payment details and submit the form
      | serhat.ozdursun@gmail.com | 4000056655665556 | 123 | 12/25 | 53700 |
    Then An information message should be displayed on page
    And "PAYMENT SUCCESS" title should display on the page
