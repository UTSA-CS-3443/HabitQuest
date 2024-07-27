package edu.usta.cs3443.habitquest.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

public class Analytics {
    private ArrayList<Goal> goalsCompleted;
    private ArrayList<Goal> activeGoals;

    public Analytics() {
        this.goalsCompleted = new ArrayList<>();
        this.activeGoals = new ArrayList<>();
    }

    public ArrayList<Goal> getGoalsCompleted() {
        return goalsCompleted;
    }

    public ArrayList<Goal> getGoalsNotComplete() {
        ArrayList<Goal> notCompleteGoals = new ArrayList<>();
        for (Goal goal : activeGoals) {
            if (!goal.isGoalCompleted()) {
                notCompleteGoals.add(goal);
            }
        }
        return notCompleteGoals;
    }

    public ArrayList<Goal> getActiveGoals() {
        return activeGoals;
    }

    public void setGoalsCompleted(ArrayList<Goal> goalsCompleted) {
        this.goalsCompleted = goalsCompleted;
    }

    public void setActiveGoals(ArrayList<Goal> activeGoals) {
        this.activeGoals = activeGoals;
    }

    public int getNumberOfGoalsCompleted() {
        return getGoalsCompleted().size();
    }

    public int getNumberOfGoalsNotCompleted() {
        return getGoalsNotComplete().size();
    }

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

    public void loadGoalsFromCSV(Context context) {
        ArrayList<Goal> allGoals = Goal.loadGoalsFromCSV(context);
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

    public String generateProgressReport() {
        StringBuilder report = new StringBuilder();
        report.append("Progress Report:\n\n");

        // Goals Completed
        int completedGoalsCount = getNumberOfGoalsCompleted();
        report.append("Goals Completed: ").append(completedGoalsCount).append("\n");
        if (completedGoalsCount == 0) {
            report.append("No goals completed yet.\n");
        } else {
            for (Goal goal : getGoalsCompleted()) {
                report.append("- ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }

        // Goals Not Completed
        int notCompletedGoalsCount = getNumberOfGoalsNotCompleted();
        report.append("\nGoals Not Completed: ").append(notCompletedGoalsCount).append("\n");
        if (notCompletedGoalsCount == 0) {
            report.append("All goals are completed or not yet started.\n");
        } else {
            for (Goal goal : getGoalsNotComplete()) {
                report.append("- ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }

        // Active Goals
        int activeGoalsCount = getNumberOfActiveGoals();
        report.append("\nActive Goals: ").append(activeGoalsCount).append("\n");
        if (activeGoalsCount == 0) {
            report.append("No active goals.\n");
        } else {
            for (Goal goal : getActiveGoals()) {
                if (!goal.isExpired(new Date())) { // Make sure the goal is active
                    report.append("- ").append(goal.getGoalName()).append(": ")
                            .append(goal.getGoalDescription()).append("\n");
                }
            }
        }
        return report.toString();
    }
}