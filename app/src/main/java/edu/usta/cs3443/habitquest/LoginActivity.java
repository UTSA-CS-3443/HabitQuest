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

import edu.usta.cs3443.habitquest.model.CheckLogin;
import edu.usta.cs3443.habitquest.model.User;

/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class LoginActivity extends AppCompatActivity {
    Button loginButton, skipButton, registerButton;
    EditText username;
    EditText password;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState creates a new instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        skipButton = findViewById(R.id.skiptomain);
        registerButton = findViewById(R.id.registerbtn);

        loginButton.setOnClickListener(v -> {
            //gets username and password from EditText
            String username = this.username.getText().toString();
            String password = this.password.getText().toString();
            //checks if username and password are valid
            if (username.isEmpty() || password.isEmpty()) {
                //create a toast to tell the user to enter a username and password
                Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    User user = User.authenticateUser(username, password, this);
                    if (user != null) {
                        Log.d("LoginActivity", "Login successful");
                        // Save user data to SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user.getUserName());
                        editor.putString("birthday", user.getUserBday());
                        editor.putString("pronoun", user.getUserPronouns());
                        editor.putString("email", user.getUserEmail());
                        editor.putBoolean("isLoggedIn", true);  // Set a flag for user logged in status
                        editor.apply();

                        // Set logged in to true
                        CheckLogin.setLoggedIn(this, true);
                        // Pass user data to MainActivity
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("userName", user.getUserName());
                        intent.putExtra("userEmail", user.getUserEmail());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error loading user data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(v -> {
            //goes to registerActivity
            Intent intent = new Intent(this, signupActivity.class);
            startActivity(intent);
        });

        skipButton.setOnClickListener(v -> {
            //set logged in to true
            CheckLogin.setLoggedIn(this, true);
            //goes to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}
