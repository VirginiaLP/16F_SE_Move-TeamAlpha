Feature: System stores and manipulates Student, Professor, and Section data
  Scenario Outline: Systems asks for an arbitrary student
    Given the file "data_module_test_1.csv" to load
    When I load the file into the data module
    And I request the student with the banner ID <banner id>
    Then I recieve the student <first name> <last name>

    Examples:
      | banner id | first name  | last name   |
      | 12345     | Bob         | Smith       |
      | 54321     | Bubba       | Blinkendorf |
      | 13524     | Blah        | Blee        |
