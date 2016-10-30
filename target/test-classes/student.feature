Feature: System stores information about a unique instance of an ACU student
  Scenario: System asks for a student's first name
    Given there is a student with the first name "Bubba"
    When I ask for the student's first name
    Then I receive the string "Bubba" from the student

  Scenario: System asks for a student's last name
    Given there is a student with the last name "Blinkendorf"
    When I ask for the student's last name
    Then I receive the string "Blinkendorf" from the student

  Scenario: System asks for a student's classification
    Given there is a student with the classification "SR"
    When I ask for the student's classification
    Then I recieve the string "SR" from the student

  Scenario: System asks for a student's sections
    Given there is a student with the following sections:
      | 12345 | 54321 | 13524 | 42531 |
    When I ask for the student's sections
    Then I receive this set from the student:
      | 12345 | 54321 | 13524 | 42531 |