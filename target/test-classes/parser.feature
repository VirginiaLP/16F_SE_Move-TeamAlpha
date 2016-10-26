Feature: Converts row of .csv file into usable strings
  The csv module converts a line of text into individual string tokens.

Scenario:
  Given the file "parse_test_1.csv" to parse
  When I request line 1 of the file
  Then I receive the string values:
    | Kevin    |
	| Isaak    |
	| Virginia |