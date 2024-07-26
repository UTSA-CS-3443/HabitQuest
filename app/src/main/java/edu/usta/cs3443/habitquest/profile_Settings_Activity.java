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
        String username = sharedPreferences.getString("username", "");
        String birthday = sharedPreferences.getString("birthday", "");
        String pronoun = sharedPreferences.getString("pronoun", "");
        String email = sharedPreferences.getString("email", "");

        // Set the retrieved data to TextViews
        userName.setText(username);
        userBday.setText(birthday);
        userPronouns.setText(pronoun);
        userEmail.setText(email);

        // Initialize Buttons
        Button editButton = findViewById(R.id.editbutton);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            Log.d("profile_Settings_Activity", "Edit button clicked");
            startActivity(intent);
        });

        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        Button logOutButton = findViewById(R.id.button3);
        logOutButton.setOnClickListener(v -> {
            CheckLogin.setLoggedIn(this, false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}
