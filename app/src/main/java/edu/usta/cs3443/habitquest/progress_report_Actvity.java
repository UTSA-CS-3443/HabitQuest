package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.usta.cs3443.habitquest.model.User;

public class progress_report_Actvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_report_actvity);

        TextView progressResultsTextView = findViewById(R.id.textView2);
        Button backButton = findViewById(R.id.button2);

        // Fetch user and analytics data
        User currentUser = getCurrentUser(); // Implement this method to get the current user
        String progressReport = currentUser.generateProgressReport();

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
}
