package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import edu.usta.cs3443.habitquest.MainActivity;

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
 * getGoalStart() - retrieve start date.   // Changed method name from getStart to getGoalStart
 * getGoalEnd() - retrieve end date.   // Changed method name from getEnd to getGoalEnd
 * isGoalCompleted() - true/false completion data.   // Changed method name from isCompleted to isGoalCompleted
 * setGoalName() - set goal name.
 * setGoalType() - set goal type.
 * setGoalDescription() - set goal description.
 * setGoalStart() - set start date.   // Changed method name from setStart to setGoalStart
 * setGoalEnd() - set end date.   // Changed method name from setEnd to setGoalEnd
 * setGoalCompleted() - set completion status.   // Changed method name from setCompleted to setGoalCompleted
 * editGoal() - edit the goal details.
 * isCompleted() - true/false completion data.
 */

public class Goal {
    private String goalName;
    private String goalType;
    private String goalDescription;
    private String goalStart;
    private String goalEnd;
    private Boolean goalCompleted;

    // Constructor
    public Goal(String goalName, String goalType, String goalDescription, String goalStart, String goalEnd) {
        this.goalName = goalName;
        this.goalType = goalType;
        this.goalDescription = goalDescription;
        this.goalStart = goalStart;
        this.goalEnd = goalEnd;
        this.goalCompleted = false;
    }

    // Getters
    public String getGoalName() { return goalName; }
    public String getGoalType() { return goalType; }
    public String getGoalDescription() { return goalDescription; }
    public String getGoalStart() { return goalStart; }   // Changed method name from getStart to getGoalStart
    public String getGoalEnd() { return goalEnd; }   // Changed method name from getEnd to getGoalEnd
    public Boolean isGoalCompleted() { return goalCompleted; }   // Changed method name from isCompleted to isGoalCompleted

    // Setters
    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setGoalType(String goalType) { this.goalType = goalType; }
    public void setGoalDescription(String goalDescription) { this.goalDescription = goalDescription; }   // Corrected parameter name from goalDescrip to goalDescription
    public void setGoalStart(String goalStart) { this.goalStart = goalStart; }   // Changed method name from setStart to setGoalStart
    public void setGoalEnd(String goalEnd) { this.goalEnd = goalEnd; }   // Changed method name from setEnd to setGoalEnd
    public void setGoalCompleted(Boolean goalCompleted) { this.goalCompleted = goalCompleted; }   // Changed method name from setCompleted to setGoalCompleted

    // Edit goal
    public void editGoal(String goalName, String goalType, String goalDescription, String goalStart, String goalEnd, Boolean goalCompleted) {
        setGoalName(goalName);
        setGoalType(goalType);
        setGoalDescription(goalDescription);
        setGoalStart(goalStart);   // Changed method name from setStart to setGoalStart
        setGoalEnd(goalEnd);   // Changed method name from setEnd to setGoalEnd
        setGoalCompleted(goalCompleted);   // Changed method name from setCompleted to setGoalCompleted
    }

    // Load goals from CSV
    public static List<Goal> loadGoalsFromCSV(Context context) {
        List<Goal> goals = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try {
            InputStream inputStream = assetManager.open("sample_goals.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns.length == 6) {
                    String goalName = columns[0].trim();
                    String goalType = columns[1].trim();
                    String goalDescription = columns[2].trim();
                    String goalStart = columns[3].trim();
                    String goalEnd = columns[4].trim();
                    Boolean goalCompleted = Boolean.parseBoolean(columns[5].trim().toUpperCase());

                    Goal goal = new Goal(goalName, goalType, goalDescription, goalStart, goalEnd);
                    goal.setGoalCompleted(goalCompleted);
                    goals.add(goal);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goals;
    }
}