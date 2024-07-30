package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/*
 * UserActivity Controller
 * View and edit user profile information
 */

public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    private EditText editTextName, editTextBday, editTextPronouns, editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize EditTexts
        editTextName = findViewById(R.id.editTextText);
        editTextBday = findViewById(R.id.editTextBday);
        editTextPronouns = findViewById(R.id.editTextPronouns);
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);

        // Load current user data
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        editTextName.setText(sharedPreferences.getString("username", ""));
        editTextBday.setText(sharedPreferences.getString("birthday", ""));
        editTextPronouns.setText(sharedPreferences.getString("pronoun", ""));
        editTextEmail.setText(sharedPreferences.getString("email", ""));
        editTextPassword.setText(sharedPreferences.getString("password", ""));

        // Initialize Buttons
        Button saveButton = findViewById(R.id.skiptomain);
        saveButton.setOnClickListener(v -> {
            String username = editTextName.getText().toString();
            String birthday = editTextBday.getText().toString();
            String pronoun = editTextPronouns.getText().toString();
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (validateInputs(username, birthday, pronoun, email, password)) {
                // Save data to SharedPreferences
                SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", username);
                editor.putString("birthday", birthday);
                editor.putString("pronoun", pronoun);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                Toast.makeText(UserActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                // Optionally navigate back or refresh data
                finish(); // or startActivity(new Intent(this, ProfileActivity.class));
            } else {
                Toast.makeText(UserActivity.this, "Invalid inputs", Toast.LENGTH_SHORT).show();
            }
        });

        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> finish()); // Go back to the previous activity
    }

    private boolean validateInputs(String username, String birthday, String pronoun, String email, String password) {
        // Implement validation logic here
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty();
    }
}