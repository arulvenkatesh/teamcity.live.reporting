Feature: Welcome

  Scenario: Welcome Scenario 1
    Given First step is executed
    Given Second step is executed

  Scenario: Welcome Scenario 2 should fail
    Given First step is executed
    Given Second step is executed
    Then Test should fail

  Scenario: Welcome Scenario 3
    Given First step is executed
    Given Second step is executed

  Scenario: Welcome Scenario 4
    Given First step is executed
    Given Second step is executed

  Scenario Outline: Welcome Scenario 5
    Given First step is executed
    Given Second step is executed

    Examples:
      | ID | NAME |
      | 1  | d    |
      | 2  | d    |


