Feature: Professor stores information about a unique instance of an ACU professor

  Scenario: System asks for a professor's first name
    Given there is a professor with the first name "Brent"
    When I ask for the professor's first name
    Then I receive the string "Brent" from the professor

  Scenario: System asks for a professor's last name
    Given there is a professor with the last name "Reeves"
    When I ask for the professor's last name
    Then I receive the string "Reeves" from the professor

  Scenario: System asks for a professor's sections
    Given there is a professor with the following sections:
      | 12345 | 54321 | 13524 | 42531 |
    When I ask for the professor's sections
    Then I receive this set from the professor:
      | 12345 | 54321 | 13524 | 42531 |
