package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        String filename = "egsample_goals.csv";

        try {
            String csvContent = readFile(filename, context);
            //test if it works
            Log.d("Goal", csvContent);
            String[] lines = csvContent.split("\n");

            for (String line : lines) {
                String[] columns = line.split(",");
                //test if it works
                Log.d("Goal", "Line: " + line);
                Log.d("Goal", "Columns: " + columns.length);

                if (columns.length == 6) {
                    try {
                        //log for testing
                        Log.d("Goal", "Line: " + line);
                        Log.d("Goal", "Columns: " + columns.length);

                        String goalName = columns[0].trim();
                        String goalType = columns[1].trim();
                        String goalDescription = columns[2].trim();
                        String goalStart = columns[3].trim();
                        String goalEnd = columns[4].trim();
                        Boolean goalCompleted = Boolean.parseBoolean(columns[5].trim().toUpperCase());

                        // Basic data validation
                        // Date format validation (replace with your desired format)

                        Goal goal = new Goal(goalName, goalType, goalDescription, goalStart, goalEnd);
                        goal.setGoalCompleted(goalCompleted);
                        goals.add(goal);
                    } catch (IllegalArgumentException e) {
                        // Handle parsing errors
                        System.err.println("Error parsing line: " + line);
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Handle file not found
        } catch (IOException e) {
            // Handle other IO exceptions
        }

        return goals;
    }
    public static String readFile(String filename, Context context) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);

        if (!file.exists()) {
            return "";
        }

        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }

        return content.toString();
    }
}