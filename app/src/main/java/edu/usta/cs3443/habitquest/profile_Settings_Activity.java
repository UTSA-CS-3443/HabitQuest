package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.usta.cs3443.habitquest.model.CheckLogin;

public class profile_Settings_Activity extends AppCompatActivity {
    private static final String TAG = "profile_Settings_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize TextViews
        TextView userName = findViewById(R.id.userName);
        TextView userBday = findViewById(R.id.userBday);
        TextView userPronouns = findViewById(R.id.userPronouns);
        TextView userEmail = findViewById(R.id.userEmail);

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "No username");
        String birthday = sharedPreferences.getString("birthday", "No birthday");
        String pronoun = sharedPreferences.getString("pronoun", "No pronouns");
        String email = sharedPreferences.getString("email", "No email");

        // Set the retrieved data to TextViews
        userName.setText(username);
        userBday.setText(birthday);
        userPronouns.setText(pronoun);
        userEmail.setText(email);

        // Initialize Buttons
        Button editButton = findViewById(R.id.editbutton);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
        });

        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> finish()); // Go back to the previous activity

        Button logOutButton = findViewById(R.id.button3);
        logOutButton.setOnClickListener(v -> {
            SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish(); // Close current activity
        });
    }
}
