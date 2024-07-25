package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

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
        Button editButton;
        editButton = findViewById(R.id.editbutton);
        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, UserActivity.class);
            Log.d("profile_Settings_Activity", "Edit button clicked");
            startActivity(intent);
        });
        Button backButton;
        backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
        Button LogOutButton;
        LogOutButton = findViewById(R.id.button3);
        LogOutButton.setOnClickListener(v -> {
            CheckLogin.setLoggedIn(this, false);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });
    }
}