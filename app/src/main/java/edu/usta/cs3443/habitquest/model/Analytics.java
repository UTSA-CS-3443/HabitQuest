package edu.usta.cs3443.habitquest.model;

import java.util.ArrayList;

/**
 * Analytics: The Analytics class represents the analytic data for a user's progress on their goals. The attributes store analytic data and methods to generate reports.
 * Attributes:
* goalsCompleted - the goals the user has completed.
* activeGoals - the active goals the user has.
*
* Methods:
* getGoalsCompleted() - gets goals marked as completed.
* getGoalsNotComplete() - gets past goals not completed.
* getActiveGoals() - gets active goals.
* setGoalsCompleted - sets goals marked as completed.
* setGoalsNotComplete - sets past goals not completed.
* setActiveGoals - sets active goals.
* generateProgressReport() - generates a report of the user’s progress.
 */
public class  Analytics {
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

    public String generateProgressReport() {
        StringBuilder report = new StringBuilder();
        report.append("Progress Report:\n\n");

        // Completed Goals
        report.append("Goals Completed:\n");
        ArrayList<Goal> completedGoals = getGoalsCompleted();
        if (completedGoals.isEmpty()) {
            report.append("No goals completed yet.\n");
        } else {
            for (Goal goal : completedGoals) {
                report.append("- ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }
        // Not Completed Goals
        report.append("\nGoals Not Completed:\n");
        ArrayList<Goal> notCompletedGoals = getGoalsNotComplete();
        if (notCompletedGoals.isEmpty()) {
            report.append("All goals are completed or not yet started.\n");
        } else {
            for (Goal goal : notCompletedGoals) {
                report.append("- ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }

        // Active Goals
        report.append("\nActive Goals:\n");
        ArrayList<Goal> activeGoals = getActiveGoals();
        if (activeGoals.isEmpty()) {
            report.append("No active goals.\n");
        } else {
            for (Goal goal : activeGoals) {
                report.append("- ").append(goal.getGoalName()).append(": ")
                        .append(goal.getGoalDescription()).append("\n");
            }
        }
        return report.toString();
    }
}
