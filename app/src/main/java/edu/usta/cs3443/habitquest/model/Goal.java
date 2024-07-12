package edu.usta.cs3443.habitquest.model;

/**
 * Goal: The Goal class represents a goal or habit that a user wants to track. It includes attributes to describe the goal and methods to manage it.
 *
 * Attributes:
 * goal_id - unique identifier for a goal or habit.
 * user_id - the id of the user who owns the goal.
 * goal_type - the type of goal (daily, short-term, long-term, etc).
 * goal_descrip - description of the goal.
 * goal_created - timestamp of when the goal was created.
 * goal_completed - boolean indicating if the goal is completed.
 * goal_start - the date the goal starts.
 * goal_end - the date the goal ends.
 * Methods:
 * createGoal() - creates a new goal.
 * updateGoal() - updates the goal.
 * deleteGoal() - delete the goal.
 * @author
 */
public class Goal {
    private String goal_id;
    private String goal_type;
    private String goal_descrip;
    private String goal_created;
    private boolean goal_completed;
    private String goal_start;
    private String goal_end;
    private String user_id;

    public Goal() {
        // TODO Auto-generated constructor stub
    }
    //create goal
    public void createGoal(String goal_id, String goal_type, String goal_descrip, String goal_created, boolean goal_completed, String goal_start, String goal_end, String user_id) {

    }
    //update goal
    public void updateGoal(String goal_id, String goal_type, String goal_descrip, String goal_created, boolean goal_completed, String goal_start, String goal_end, String user_id) {

    }
    //delete goal
    public void deleteGoal(String goal_id, String goal_type, String goal_descrip, String goal_created, boolean goal_completed, String goal_start, String goal_end, String user_id) {

    }



}
