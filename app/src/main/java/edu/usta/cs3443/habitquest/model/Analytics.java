package edu.usta.cs3443.habitquest.model;

/**
 * Analytics: The Analytics class represents the analytic data for a user's progress on their goals. The attributes store analytic data and methods to generate reports.
 * Attributes:
 * user_id - the id of the user
 * goals_completed - the goals the user has completed.
 * active_goals - the active goals the user has active.
 * progress_trend - generates a report of the user’s progress.
 * Methods:
 * generateReport() - generates a report of the user’s progress.
 */
public class Analytics {
    private int user_id;
    private int goals_completed;
    private int active_goals;
    private String progress_trend;

    public Analytics(int user_id, int goals_completed, int active_goals) {
        this.user_id = user_id;
        this.goals_completed = goals_completed;
        this.active_goals = active_goals;
        this.progress_trend = generateReport();
    }

    public String generateReport() {

        return "";
    }

}
