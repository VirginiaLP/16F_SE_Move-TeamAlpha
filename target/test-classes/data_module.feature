Feature: System stores and manipulates Student, Professor, and Section data
  Scenario Outline: Systems asks for an arbitrary student
    Given the file "data_module_test_1.csv" to load
      And I load the file into the data module
     When I request the student with the banner ID <banner id>
      And I ask for the student's first name
     Then I receive the string <first name> from the data module

    Examples:
      | banner id | first name |
      | 12345     | Bob        |
      | 54321     | Bubba      |
      | 13524     | Blah       |

  Scenario Outline: System asks for an arbitrary professor
    Given the file "data_module_test_1.csv" to load
      And I load the file into the data module
     When I request the professor with the professor ID <professor id>
      And I ask for the professor's first name
     Then I receive the string <first name> from the data module

    Examples:
      | professor id | first name |
      | 12444        | John       |
      | 09876        | Bob        |
      | 77777        | Jake       |

  Scenario Outline: System asks for an arbitrary section
    Given the file "data_module_test_1.csv" to load
      And I load the file into the data module
     When I request the section with the CRN <crn>
      And I ask for the section's subject
     Then I receive the string <subject> from the data module

    Examples:
      | crn    | subject |
      | 13524  | ACCT    |
      | 12345  | CS      |
      | 54321  | IT      |
