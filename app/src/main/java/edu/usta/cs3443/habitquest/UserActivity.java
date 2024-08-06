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

import java.io.IOException;

import edu.usta.cs3443.habitquest.model.User;

/**
 * UserActivity.java - Controller for user profile settings
 *
 * View and edit user profile information
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class UserActivity extends AppCompatActivity {
    private static final String TAG = "UserActivity";
    Button saveButton, backButton;
    private EditText editTextName, editTextBday, editTextPronouns, editTextEmail, editTextPassword, editTextPasswordConfirm;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState creates a new instance of the activity
     */
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
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);

        // Load current user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String currentName = sharedPreferences.getString("username", "");
        String currentBday = sharedPreferences.getString("birthday", "");
        String currentPronouns = sharedPreferences.getString("pronoun", "");
        String currentEmail = sharedPreferences.getString("email", "");
        String currentPassword = sharedPreferences.getString("password", "");

        // Set current data to EditTexts
        editTextName.setText(currentName);
        editTextBday.setText(currentBday);
        editTextPronouns.setText(currentPronouns);
        editTextEmail.setText(currentEmail);
        editTextPassword.setText(currentPassword);

        // Initialize Buttons
        saveButton = findViewById(R.id.skiptomain);
        saveButton.setOnClickListener(v -> {
            // Capture new data from EditTexts
            String newName = editTextName.getText().toString();
            String newBday = editTextBday.getText().toString();
            String newPronouns = editTextPronouns.getText().toString();
            String newEmail = editTextEmail.getText().toString();
            String newPassword = editTextPassword.getText().toString();
            String confirmPassword = editTextPasswordConfirm.getText().toString();

            // Check if passwords match
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(UserActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Log new data
            Log.d(TAG, "Saving new data:");
            Log.d(TAG, "Name: " + newName);
            Log.d(TAG, "Birthday: " + newBday);
            Log.d(TAG, "Pronouns: " + newPronouns);
            Log.d(TAG, "Email: " + newEmail);
            Log.d(TAG, "Password: " + newPassword);

            // Save new user data to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", newName);
            editor.putString("birthday", newBday);
            editor.putString("pronoun", newPronouns);
            editor.putString("email", newEmail);
            editor.putString("password", newPassword);
            editor.apply();

            // Update user data file
            try {
                User.updateUserFile(newName, newBday, newPronouns, newEmail, newPassword, this);
                Toast.makeText(UserActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(UserActivity.this, "Failed to save changes", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            // Go back to profile settings activity
            Intent intent = new Intent(this, profile_Settings_Activity.class);
            startActivity(intent);
        });

        backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, profile_Settings_Activity.class);
            startActivity(intent);
        });
    }

    
}
