Feature: System stores and manipulates Student, Professor, and Section data
  Scenario Outline: Systems asks for an arbitrary student
    Given the file "data_module_test_1.csv" to load
    When I load the file into the data module
    And I request the student with the banner ID <banner id>
    Then I receive the student <first name> <last name>

    Examples:
      | banner id | first name  | last name   |
      | 12345     | Bob         | Smith       |
      | 54321     | Bubba       | Blinkendorf |
      | 13524     | Blah        | Blee        |

	  
  Scenario Outline: Systems asks for an arbitrary professor
    Given the file "data_module_test_1.csv" to load
    When I load the file into the data module
    And I request the professor with the professor ID <professor id>
    Then I receive the professor <first name> <last name>

    Examples:
      | professor id | first name  | last name  |
      | 12444        | John        | Johnson    |
      | 09876        | Mister      | Hamilton   |
      | 77777        | Jane        | Doe        |
	  
	  
  Scenario Outline: Systems asks for an arbitrary section
    Given the file "data_module_test_1.csv" to load
    When I load the file into the data module
    And I request the section with the CRN <crn>
    Then I receive the class subject and title <subject> <title>

    Examples:
      | crn    | subject | title     |
      | 23678  | Art     | Drawing   |
      | 38383  | CS      | P1        |
      | 60606  | IT      | Databases |