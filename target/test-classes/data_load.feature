Feature: System stores and manipulates Student, Professor, and Section data
  Scenario Outline: Systems asks for an arbitrary student
    Given the file "data_load_test_1.csv" to load
      And I load the file into the data loader
     When I request the student with the banner ID <banner id>
      And I ask for the student's first name from the data loader
     Then I receive the string <first name> from the data loader

    Examples:
      | banner id     | first name |
      | 000876727     | Pauline    |
      | 000384171     | Angel      |
      | 000800465     | Joanne     |

  Scenario Outline: System asks for an arbitrary professor
    Given the file "data_load_test_1.csv" to load
      And I load the file into the data loader
     When I request the professor with the professor ID <professor id>
      And I ask for the professor's first name from the data loader
     Then I receive the string <first name> from the data loader

    Examples:
      | professor id | first name |
      | 17123        | Dwayne     |
      | 1831         | Deonna     |
      | 1523         | Paul       |

  Scenario Outline: System asks for an arbitrary section
    Given the file "data_load_test_1.csv" to load
      And I load the file into the data loader
     When I request the section with the CRN <crn>
      And I ask for the section's subject from the data loader
     Then I receive the string <subject> from the data loader

    Examples:
      | crn    | subject |
      | 14037  | IT      |
      | 14764  | CS      |
      | 15030  | PEAC    |
