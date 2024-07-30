package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.net.ParseException;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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


    public String toCSVString() {
        return goalName + "," + goalType + "," + goalDescription + "," + goalStart + "," + goalEnd + "," + goalCompleted;
    }


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

    // Load goals from CSV
    public static List<Goal> loadGoalsFromCSV(Context context) {
        List<Goal> goals = new ArrayList<>();
        String filename = "goals.csv";

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
        try {
            String csvContent = readFile(filename, context);
            Log.d("Goal", csvContent);
            String[] lines = csvContent.split("\n");

            for (String line : lines) {
                String[] columns = line.split(",");
                Log.d("Goal", "Line: " + line);
                Log.d("Goal", "Columns: " + columns.length);

                if (columns.length == 6) {
                    try {
                        Log.d("Goal", "Line: " + line);
                        Log.d("Goal", "Columns Length: " + columns.length);
                        String goalName = columns[0].trim();
                        String goalType = columns[1].trim();
                        String goalDescription = columns[2].trim();
                        String goalStart = columns[3].trim();
                        String goalEnd = columns[4].trim();
                        Boolean goalCompleted = Boolean.parseBoolean(columns[5].trim());

                        Goal goal = new Goal(goalName, goalType, goalDescription, goalStart, goalEnd);
                        goal.setGoalCompleted(goalCompleted);
                        goals.add(goal);
                    } catch (Exception e) {
                        Log.e("Goal", "Error parsing line: " + line, e);
                    }
                } else {
                    Log.w("Goal", "Skipping line due to incorrect format: " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return goals;
    }

    // Read file method
    private static String readFile(String filename, Context context) throws IOException {
        File file = new File(context.getFilesDir(), filename);
        StringBuilder stringBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine()).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    // Delete a goal from CSV
    public static void deleteGoalFromCSV(Goal goal, Context context) throws IOException {
        File file = new File(context.getFilesDir(), "goals.csv");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith(goal.getGoalName())) {
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Update a goal in CSV
    public static void updateGoalInCSV(Goal goal, Context context) throws IOException {
        File file = new File(context.getFilesDir(), "goals.csv");
        List<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith(goal.getGoalName())) {
                    lines.add(goal.toCSVString());
                } else {
                    lines.add(line);
                }
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    // Mark a goal as completed
    public void markGoalCompleted(Goal goal, Context context) throws IOException {
        goal.setGoalCompleted(true);
        updateGoalInCSV(goal, context);
    }
}
