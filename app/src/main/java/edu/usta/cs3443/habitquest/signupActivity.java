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
public class signupActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * @param savedInstanceState creates a new instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        EditText email,username,password,password_confirm,birthday,pronoun;
        Button signup;

        email = findViewById(R.id.username);
        username = findViewById(R.id.name);
        password = findViewById(R.id.password);
        password_confirm = findViewById(R.id.password_confirm);
        birthday = findViewById(R.id.birthday);
        pronoun = findViewById(R.id.Pronouns);

        signup = findViewById(R.id.signup);
        //the button was disabled ???
        signup.setOnClickListener(v -> {
//checks if button is clicked
            Log.d("signupActivity", "IT WAS CLICKED");
            if (email.getText().toString().isEmpty() || username.getText().toString().isEmpty() ||pronoun.getText().toString().isEmpty() || password.getText().toString().isEmpty() || password_confirm.getText().toString().isEmpty() || birthday.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.getText().toString().equals(password_confirm.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            //if user

            //    public User(String userName, String userBday, String userPronouns, String userEmail, String user_passwd, String last_login, String date_created){
            User user = new User( username.getText().toString(), birthday.getText().toString(),pronoun.getText().toString(), email.getText().toString(), password.getText().toString(),getCurrentDate(), getCurrentDate());
            try {
                user.createProfile(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show();
            //Set user to logged in
            CheckLogin.setLoggedIn(this,true);

            // Save user data to SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username", user.getUserName());
            editor.putString("birthday", user.getUserBday());
            editor.putString("pronoun", user.getUserPronouns());
            editor.putString("email", user.getUserEmail());
            editor.putBoolean("isLoggedIn", true);  // Set a flag for user logged in status
            editor.apply();


            //go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }

    /**
     * Gets the current date and time in the desired format.
     * @return the current date and time as a string
     */
    private String getCurrentDate() {
        // Get the current date and time
        java.util.Date currentDate = new java.util.Date();
        // Format the date and time as a string in the desired format
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(currentDate);
    }

}