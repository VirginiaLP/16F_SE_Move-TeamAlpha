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
    Given there is a room with the max capacity "34"
	When I ask for the room's max capacity
	Then I receive the integer "34" from the room