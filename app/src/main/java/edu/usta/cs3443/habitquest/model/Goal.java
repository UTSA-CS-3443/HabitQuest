package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
 * getGoalStart() - retrieve start date.
 * getGoalEnd() - retrieve end date.
 * isGoalCompleted() - true/false completion data.
 * setGoalName() - set goal name.
 * setGoalType() - set goal type.
 * setGoalDescription() - set goal description.
 * setGoalStart() - set start date.
 * setGoalEnd() - set end date.
 * setGoalCompleted() - set completion status.
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

    // Getters and Setters
    public String getGoalName() { return goalName; }
    public String getGoalType() { return goalType; }
    public String getGoalDescription() { return goalDescription; }
    public String getGoalStart() { return goalStart; }
    public String getGoalEnd() { return goalEnd; }
    public Boolean isGoalCompleted() { return goalCompleted; }

    public void setGoalName(String goalName) { this.goalName = goalName; }
    public void setGoalType(String goalType) { this.goalType = goalType; }
    public void setGoalDescription(String goalDescription) { this.goalDescription = goalDescription; }
    public void setGoalStart(String goalStart) { this.goalStart = goalStart; }
    public void setGoalEnd(String goalEnd) { this.goalEnd = goalEnd; }
    public void setGoalCompleted(Boolean goalCompleted) { this.goalCompleted = goalCompleted; }

    // Check if the goal is expired
    public boolean isExpired(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date endDate = sdf.parse(goalEnd);
            return endDate.before(today);
        } catch (ParseException | java.text.ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Load goals from CSV files
    public static ArrayList<Goal> loadGoalsFromCSV(Context context) {
        ArrayList<Goal> goals = new ArrayList<>();

        // Load pre-loaded goals from sample_goals.csv
        try (InputStream inputStream = context.getAssets().open("sample_goals.csv");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

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
                    Boolean goalCompleted = Boolean.parseBoolean(columns[5].trim());

                    Goal goal = new Goal(goalName, goalType, goalDescription, goalStart, goalEnd);
                    goal.setGoalCompleted(goalCompleted);
                    goals.add(goal);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Load newly created goals from goals.csv
        File file = new File(context.getFilesDir(), "goals.csv");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
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
                        Boolean goalCompleted = Boolean.parseBoolean(columns[5].trim());

                        Goal goal = new Goal(goalName, goalType, goalDescription, goalStart, goalEnd);
                        goal.setGoalCompleted(goalCompleted);
                        // Add the goal only if it doesn't already exist in the list
                        boolean exists = goals.stream().anyMatch(g -> g.getGoalName().equals(goalName));
                        if (!exists) {
                            goals.add(goal);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return goals;
    }
}
