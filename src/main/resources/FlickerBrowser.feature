Feature: Display latest flicker photos

  Scenario: Get latest flicker images from London
    Given I enter London as my search string
    And I press Search button
    Then I should get latest pictures from London
