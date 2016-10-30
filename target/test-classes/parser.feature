Feature: Converts row of .csv file into usable strings
  The csv module converts a line of text into individual string tokens.

Scenario: The parser successfully reads the lines in file "parse_test_1.csv"
  Given the file "parse_test_1.csv" to parse
  When I request line 1 of the file
  Then I receive the string values:
    | Kevin | Isaak | Virginia |

  Given the file "parse_test_1.csv" to parse
  When I request line 2 of the file
  Then I receive the string values:
    | Blee | Blah | Blue |

  Given the file "parse_test_1.csv" to parse
  When I request line 3 of the file
  Then I receive the string values:
    | A quote: " was escaped | another value | a third value |

  Given the file "parse_test_1.csv" to parse
  When I request line 4 of the file
  Then I receive the string values:
    | A delimeter: , was ignored | another value | a third value |
