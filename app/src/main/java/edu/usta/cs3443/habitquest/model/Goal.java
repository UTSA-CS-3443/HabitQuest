package edu.usta.cs3443.habitquest.model;

/**
 * Goal: The Goal class represents a goal or habit that a user wants to track. It includes attributes to describe the goal and methods to manage it.
 *
* Attributes:
* goalName - name of goal.
* goalType - type of goal (personal, education, etc).
* goalDescrip - description of the goal.
* goalStart - the date the goal starts.
* goalEnd - the date the goal ends.
* goalCompleted - boolean indicating if the goal was completed.
*
* Methods:
* createGoal() - creates a new goal.
* updateGoal() - updates the goal.
* deleteGoal() - delete the goal.
* getGoal() - retrieves the goal.
* getGoalName() - retrieve goal name.
* getGoalType() - retrieve goal type.
* getGoalDescription() - retrieve goal description.
* getStart() - retrieve start date.
* getEnd() - retrieve end date.
* getCompleted() - retrieve completed goal data.
* getNotCompleted() - retrieve non-completed goal data.
* setGoal() - retrieves the goal.
* setGoalName() - retrieve goal name.
* setGoalType() - retrieve goal type.
* setGoalDescription() - retrieve goal description.
* setStart() - retrieve start date.
* setEnd() - retrieve end date.
* setCompleted() - retrieve completed goal data.
* isCompleted() - true/false completion data.
 * @author
 */

public class Goal {
    private String goalName;
    private String goalType;
    private String goalDescription;
    private String goalStart;
    private String goalEnd;
    private Boolean goalCompleted;
    

public Goal(String goalName, String goalType, String goalDescription, String goalStart, String goalEnd) {
        this.goalName = goalName;
        this.goalType = goalType;
        this.goalDescription = goalDescription;
        this.goalStart = goalStart;
        this.goalEnd = goalEnd;
        this.goalCompleted = false;
    }
    
    public String getGoalName() { 
        return goalName;
    }
    public String getGoalType() { 
        return goalType;
    }
    public String getGoalDescription() { 
        return goalDescription;
    }
    public String getStart() { 
        return goalStart;
    }
    public String getEnd() { 
        return goalEnd;
    }
    public Boolean isCompleted() { 
        return goalCompleted;
    }

    public void setGoalName(String goalName) { 
        this.goalName = goalName;
    }
    public void setGoalType(String goalType) { 
        this.goalType = goalType;
    }
    public void setGoalDescription(String goalDescrip) { 
        this.goalDescription = goalDescription;
    }
    public void setStart(String goalStart) { 
        this.goalStart = goalStart;
    }
    public void setEnd(String goalEnd) { 
        this.goalEnd = goalEnd;
    }
    public void setCompleted(Boolean goalCompleted) { 
        this.goalCompleted = goalCompleted;
    }

    public void editGoal(String goalName, String goalType, String goalDescription, String goalStart, String goalEnd, Boolean goalCompleted) {
        setGoalName(goalName);
        setGoalType(goalType);
        setGoalDescription(goalDescription);
        setStart(goalStart);
        setEnd(goalEnd);
        setCompleted(goalCompleted);
    }

    
}
