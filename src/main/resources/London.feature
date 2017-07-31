Feature: London

  Scenario: Get latest flicker images from London
    Given I'm on 'Main' page
    And I enter "London" into search box
    Then I should get latest pictures from London
