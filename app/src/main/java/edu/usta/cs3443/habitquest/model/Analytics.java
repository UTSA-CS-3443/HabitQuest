package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class Analytics {
    private ArrayList<Goal> goalsCompleted;
    private ArrayList<Goal> activeGoals;
    private static List<String> goals;

    /**
     * Constructor for Analytics
     */
    public Analytics() {
        this.goalsCompleted = new ArrayList<>();
        this.activeGoals = new ArrayList<>();
    }

    /**
     * Getter for goalsCompleted
     * @return Arraylist of goals completed
     */
    public ArrayList<Goal> getGoalsCompleted() {
        return goalsCompleted;
    }

    /**
     * Getter for active goals
     * @return Arraylist of active goals
     */
    public ArrayList<Goal> getGoalsNotComplete() {
        ArrayList<Goal> notCompleteGoals = new ArrayList<>();
        for (Goal goal : activeGoals) {
            if (!goal.isGoalCompleted()) {
                notCompleteGoals.add(goal);
            }
        }
        return notCompleteGoals;
    }

    /**
     * Getter for active goals
     * @return Arraylist of active goals
     */
    public ArrayList<Goal> getActiveGoals() {
        return activeGoals;
    }

    /**
     * Setter for goalsCompleted
     * @param goalsCompleted Arraylist of goals completed
     */
    public void setGoalsCompleted(ArrayList<Goal> goalsCompleted) {
        this.goalsCompleted = goalsCompleted;
    }

    /**
     * Setter for active goals
     * @param activeGoals Arraylist of active goals
     */
    public void setActiveGoals(ArrayList<Goal> activeGoals) {
        this.activeGoals = activeGoals;
    }

    /**
     * Getter for number of goals completed
     * @return Number of goals completed
     */
    public int getNumberOfGoalsCompleted() {
        return getGoalsCompleted().size();
    }

    /**
     * Getter for number of goals not completed
     * @return Number of goals not completed
     */
    public int getNumberOfGoalsNotCompleted() {
        return getGoalsNotComplete().size();
    }

    /**
     * Getter for number of active goals
     * @return Number of active goals
     */
    public int getNumberOfActiveGoals() {
        int count = 0;
        Date today = new Date();
        for (Goal goal : activeGoals) {
            if (!goal.isExpired(today)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Getter for goals
     * @param context Context of the application
     */
    public void loadGoalsFromCSV(Context context) {
        ArrayList<Goal> allGoals = (ArrayList<Goal>) Goal.loadGoalsFromCSV(context);
        ArrayList<Goal> completedGoals = new ArrayList<>();
        ArrayList<Goal> activeGoals = new ArrayList<>();

        for (Goal goal : allGoals) {
            if (goal.isGoalCompleted()) {
                completedGoals.add(goal);
            } else {
                activeGoals.add(goal);
            }
        }

        this.goalsCompleted = completedGoals;
        this.activeGoals = activeGoals;
    }

    /**
     * Getter for goals
     * @param context Context of the application
     * @return List of goals
     * @throws IOException if the file cannot be read
     */
    public static List<String> getGoaldata(Context context) throws IOException {
        List<String> goals2 = new ArrayList<>();
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "goals.csv");
        Log.d("Analytics", "File path: " + file.getAbsolutePath());
        goals2 = loadAllLinesFromInternal(context, "goals.csv");
        goals = goals2;
        return goals2;
    }

    /**
     * Getter for goals
     * @param context Context of the application
     * @param filename Name of the file to load
     * @return List of goals
     * @throws IOException if the file cannot be read
     */
    public static List<String> loadAllLinesFromInternal(Context context, String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(context.getFilesDir(), filename);


        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                // Skip the header line if present
                //br.readLine();
                while ((line = br.readLine()) != null) {
                    Log.d("TAG", "loadAllLinesFromInternal: " + line);
                    lines.add(line);
                }
            }
        } else {
            Log.d("TAG", "loadAllLinesFromInternal: file does not exist");
        }

        return lines;
    }

    /**
     * Getter for goals
     * @param context Context of the application
     * @return List of goals
     * @throws IOException if the file cannot be read
     */
    public String generateProgressReport(Context context) throws IOException {
        // Initialize the report
        //load goals from csv file
        loadGoalsFromCSV(context);
        //delete the goals from the csv file
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "goals.csv");

        Log.d("Analytics", "File path: " + file.getAbsolutePath());
        //show content of goals.csv
        Log.d("Analytics", "File content: " + getGoaldata(context));



        StringBuilder report = new StringBuilder();
        report.append("Progress Report:\n\n\n");

        // Goals Completed
        int completedGoalsCount =  getNumberOfGoalsCompleted();
        report.append("Goals Completed: ").append(completedGoalsCount).append("\n");
        if (completedGoalsCount == 0) {
            report.append("No goals completed yet.\n");
        } else {
            for (Goal goal : getGoalsCompleted()) {
                report.append("• ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }

        // Goals Not Completed
        int notCompletedGoalsCount = getNumberOfGoalsNotCompleted();
        report.append("\nActive Goals: ").append(notCompletedGoalsCount).append("\n");
        if (notCompletedGoalsCount == 0) {
            report.append("All goals are completed or not yet started.\n");
        } else {
            for (Goal goal : getGoalsNotComplete()) {
                report.append("• ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }

        return report.toString();
    }
}