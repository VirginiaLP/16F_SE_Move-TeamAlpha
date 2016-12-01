Feature: Room stores information about a unique instance of an ACU room

  Scenario: System asks for a room's building code
    Given there is a room with the building code "MBB"
    When I ask for the room's building code
    Then I receive the string "MBB" from the room

  Scenario: System asks for a room's room number
    Given there is a room with the room number "123"
    When I ask for the room's room number
    Then I receive the string "123" from the room

  Scenario: System asks for a room's max capacity
    Given there is a room with the max capacity 34
	When I ask for the room's max capacity
	Then I receive the integer 34 from the room

  Scenario Outline: System asks if two rooms are equal
    Given the first room has a building code "<first code>"
      And the second room has a building code "<second code>"
      And the first room has a room number of "<first number>"
      And the second room has a room number of "<second number>"
    When I ask if the rooms are equal
    Then I am told that the rooms <result> equal

    Examples:
      | first code  | second code | first number  | second number | result  |
      | MBB         | MBB         | 316           | 316           | are     |
      | MBB         | MBB         | 316           | 315           | are not |
      | MBB         | ADM         | 316           | 316           | are not |
      | MBB         | ADM         | 316           | 315           | are not |
