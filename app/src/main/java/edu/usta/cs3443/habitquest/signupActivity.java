package edu.usta.cs3443.habitquest;

import android.content.Intent;
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

public class signupActivity extends AppCompatActivity {
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


            //go to main activity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
    //gets current date in format yyyy-mm-dd from the system
    private String getCurrentDate() {
        // Get the current date and time
        java.util.Date currentDate = new java.util.Date();
        // Format the date and time as a string in the desired format
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM-dd-yyyy");
        return dateFormat.format(currentDate);
    }

}