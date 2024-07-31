package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SeekBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.usta.cs3443.habitquest.model.User;
import edu.usta.cs3443.habitquest.model.Analytics;

public class progress_report_Actvity extends AppCompatActivity {
    private SeekBar seekBarProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report_actvity);

        TextView progressResultsTextView = findViewById(R.id.textView2);
        Button backButton = findViewById(R.id.button2);
        seekBarProgress = findViewById(R.id.seekBarProgress);

        // Fetch user and analytics data
        User currentUser = getCurrentUser(); // Implement this method to get the current user
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

    // Helper method to retrieve User object from Intent
    private User getCurrentUser() {
        // Assuming User data is passed through Intent extras; adjust as needed
        // For simplicity, creating a new User object here. Replace with your logic.
        return new User("John Doe", "01/01/1990", "He/Him", "john.doe@example.com", "password", "last_login", "date_created");
    }
    // Method to update SeekBar progress
    private void updateSeekBar(int progress) {
        seekBarProgress.setProgress(progress);
    }

    // Method to calculate progress percentage
    private int calculateProgressPercentage(User user) {
        Analytics analytics = user.getAnalytics();
        int completedGoals = analytics.getNumberOfGoalsCompleted();
        int notCompletedGoalsCount = analytics.getNumberOfGoalsNotCompleted();
        int totalGoals = completedGoals + notCompletedGoalsCount;

        // Check to avoid division by zero
        if (totalGoals == 0) {
            return 0; // or some default value if no goals are available
        }

        // Calculate percentage
        int percentComplete = (completedGoals * 100) / totalGoals;
        return percentComplete;
    }
}
