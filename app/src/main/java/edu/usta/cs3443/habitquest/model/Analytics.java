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
            if (!goal.isCompleted()) {
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

    public String generateReport() {
    // Generate a progress report based on the goals data
        return "";
    }

    public String generateProgressReport() {
        return "";
    }
}
