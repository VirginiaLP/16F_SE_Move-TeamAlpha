Feature: System stores data about a unique course section
  Scenario: System asks for data about section subject
    Given there is a section with the subject "CS"
    When I ask for the section's subject
    Then I receive the string "CS" from the section

  Scenario: System asks for data about section number
    # Some number actually contain characters
    Given there is a section with the number "374"
    When I ask for the section's number
    Then I receive the string "374" from the section

  Scenario: System asks for data about section title
    Given there is a section with the title "Software Engineering"
    When I ask for the section's title
    Then I receive the string "Software Engineering" from the section

  Scenario Outline: System asks for data about section suffix
    Given there is a section with the suffix "<suffix>"
    When I ask for the section's suffix
    Then I receive the string "<result>" from the section

    Examples:
      | suffix  | result  |
      | 01      | 01      |
      | H2      | H2      |
      | 03      | 03      |

  Scenario Outline: System asks for section time ID
    Given there is a section with the time-ID <time id>
    When I ask for the section's time-ID
    Then I receive the integer <result> from the section

    Examples:
      | time id | result  |
      | 3       | 3       |
      | 30      | 30      |
      | 300     | 300     |

  Scenario Outline: System asks for section room-ID
    Given there is a section with the room-ID <room id>
    When I ask for the section's room-ID
    Then I receive the integer <result> from the section

    Examples:
      | room id | result  |
      | 1       | 1       |
      | 50      | 50      |
      | 100     | 100     |