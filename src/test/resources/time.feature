Feature: System stores information about different class times
  Scenario Outline: System asks for a time's start-time
    Given there is a time with the start-time <start time>
    When I ask for the time's start-time
    Then I receive the integer <result> from the time

    Examples:
      | start time  | result  |
      | 1200        | 1200    |
      | 1300        | 1300    |
      | 1330        | 1330    |

  Scenario Outline: System asks for a time's end-time
    Given there is a time with the end-time <end time>
    When I ask for the time's end-time
    Then I receive the integer <result> from the time

    Examples:
      | end time    | result  |
      | 1200        | 1200    |
      | 1300        | 1300    |
      | 1330        | 1330    |

  Scenario Outline: System asks for a time's year
    Given there is a time with the year <year>
    When I ask for the time's year
    Then I receive the integer <result> from the time

    Examples:
      | year  | result  |
      | 1906  | 1906    |
      | 1999  | 1999    |
      | 2016  | 2016    |
      | 2020  | 2020    |
      | 3000  | 3000    |

  Scenario Outline: System asks for a time's term
    Given there is a time with the term <term>
    When I ask for the time's term
    Then I receive the term <result> from the time

    Examples:
      | term    | result  |
      | FALL    | FALL    |
      | SUMMER  | SUMMER  |
      | SPRING  | SPRING  |

  Scenario Outline: Systems asks for a time's term length
    Given there is a time with the term-length <term length>
    When I ask for the time's term-length
    Then I receive the term-length <result> from the time

    Examples:
      | term length                 | result                      |
      | SESSION_III_EXTENDED_COURSE | SESSION_III_EXTENDED_COURSE |
      | SESSION_I                   | SESSION_I                   |
      | ACU_WORLDWIDE_SESSION_2     | ACU_WORLDWIDE_SESSION_2     |
      | FULL_TERM                   | FULL_TERM                   |
      | JANUARY_INTENSIVE_COURSE    | JANUARY_INTENSIVE_COURSE    |
      | ACU_WORLDWIDE_SESSION_1     | ACU_WORLDWIDE_SESSION_1     |
      | SESSION_I                   | SESSION_I                   |
      | SESSION_IV                  | SESSION_IV                  |

  Scenario Outline: System asks for a time's days
    Given there is a time with the days <days>
	When I ask for the time's days
	Then I receive the string <result> from the time
	
	Examples:
	  | days    | result   |
	  | MWF     | MWF      |
	  | TR      | TR       |
	  | MW      | MW       |
      | MTWRFSU | MTWRFSU  |

