package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.usta.cs3443.habitquest.model.Analytics;
import edu.usta.cs3443.habitquest.model.User;
/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class progress_report_Actvity extends AppCompatActivity {
    private SeekBar seekBarProgress;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState creates a new instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report_actvity);

        TextView progressResultsTextView = findViewById(R.id.textView2);
        Button backButton = findViewById(R.id.button2);
        seekBarProgress = findViewById(R.id.seekBarProgress);

        // Fetch user and analytics data
        User currentUser = getCurrentUser();
        String progressReport = null;
        try {
            progressReport = currentUser.generateProgressReport(this);
            int progressPercentage = calculateProgressPercentage(currentUser);
            updateSeekBar(progressPercentage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set the progress report to the TextView
        progressResultsTextView.setText(progressReport);

        // Set up back button listener
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(progress_report_Actvity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Get the current user from the intent extras.
     * @return the current user
     */
    private User getCurrentUser() {
        // Getting sample user data
        return new User("John Doe", "01/01/1990", "He/Him", "john.doe@example.com", "password", "last_login", "date_created");
    }

    /**
     * Update the SeekBar progress.
     * @param progress the progress value
     */
    private void updateSeekBar(int progress) {
        seekBarProgress.setProgress(progress);
    }

    /**
     * Calculate the progress percentage based on completed goals.
     * @param user the user object
     * @return the progress percentage
     */
    private int calculateProgressPercentage(User user) {
        Analytics analytics = user.getAnalytics();
        int completedGoals = analytics.getNumberOfGoalsCompleted();
        int notCompletedGoalsCount = analytics.getNumberOfGoalsNotCompleted();
        int totalGoals = completedGoals + notCompletedGoalsCount;

        // Check to avoid division by zero
        if (totalGoals == 0) {
            return 0; // default value if no goals are available
        }

        // Calculate percentage
        int percentComplete = (completedGoals * 100) / totalGoals;
        return percentComplete;
    }
}
