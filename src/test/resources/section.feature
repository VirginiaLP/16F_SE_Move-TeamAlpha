Feature: System stores data about a unique course section
  Scenario Outline: System asks for section crn
    Given there is a section with the crn <crn>
    When I ask for the section's crn
    Then I receive the integer <result> from the section

    Examples:
      | crn   | result  |
      | 12345 | 12345   |
      | 54321 | 54321   |
      | 13524 | 13524   |

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

  Scenario Outline: System asks for section term
    Given there is a section with the term <term>
    When I ask for the section's term
    Then I receive the term <result> from the section

    Examples:
      | term   | result |
      | Fall   | Fall   |
      | Spring | Spring |
      | Summer | Summer |

  Scenario Outline: System asks for section term-length
    Given there is a section with the term-length <term-length>
    When I ask for the section's term-length
    Then I receive the term-length <result> from the section

    Examples:
      | term-length               | result                    |
      | FULL_TERM                 | FULL_TERM                 |
      | JANUARY_INTENSIVE_COURSE  | JANUARY_INTENSIVE_COURSE  |
      | ACU_WORLDWIDE_SESSION_1   | ACU_WORLDWIDE_SESSION_1   |
      | SESSION_I                 | SESSION_I                 |
      | SESSION_IV                | SESSION_IV                |

  Scenario Outline: System asks for section year
    Given there is a section with the year <year>
    When I ask for the section's year
    Then I receive the integer <result> from the section

    Examples:
      | year  | result |
      | 2013  | 2013   |
      | 2014  | 2014   |
      | 2015  | 2015   |
      | 2016  | 2016   |
      | 2020  | 2020   |
      | 3000  | 3000   |

  Scenario Outline: Systems asks for section start time
    Time is represented on a 24 hour scale as an integer, with the first
    two digits representing the hour value, and the latter two representing
    the minute value.

    Given there is a section with the start time <time>
    When I ask for the section's start time
    Then I receive the integer <result> from the section

    Examples:
      | time  | result  |
      | 0800  | 0800    |
      | 0900  | 0900    |
      | 1330  | 1330    |

  Scenario Outline: Systems asks for section end time
  Time is represented on a 24 hour scale as an integer, with the first
  two digits representing the hour value, and the latter two representing
  the minute value.

    Given there is a section with the end time <time>
    When I ask for the section's end time
    Then I receive the integer <result> from the section

    Examples:
      | time  | result  |
      | 0950  | 0950    |
      | 1050  | 1050    |
      | 1420  | 1420    |
