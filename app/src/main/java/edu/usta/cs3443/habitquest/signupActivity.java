package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.usta.cs3443.habitquest.model.CheckLogin;
import edu.usta.cs3443.habitquest.model.User;

public class signupActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText userBdayEditText;
    private EditText userPronounsEditText;
    private EditText userEmailEditText;
    private EditText userPasswordEditText;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_signup);

        userNameEditText = findViewById(R.id.name);
        userBdayEditText = findViewById(R.id.birthday);
        userPronounsEditText = findViewById(R.id.Pronouns);
        userEmailEditText = findViewById(R.id.username);
        userPasswordEditText = findViewById(R.id.password);
        signupButton = findViewById(R.id.signup);

        signupButton.setOnClickListener(view -> {
            String userName = userNameEditText.getText().toString().trim();
            String userBday = userBdayEditText.getText().toString().trim();
            String userPronouns = userPronounsEditText.getText().toString().trim();
            String userEmail = userEmailEditText.getText().toString().trim();
            String userPassword = userPasswordEditText.getText().toString().trim();
            String lastLogin = getCurrentDate();
            String dateCreated = getCurrentDate();

            if (userName.isEmpty() || userBday.isEmpty() || userPronouns.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(signupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                User newUser = new User(userName, userBday, userPronouns, userEmail, userPassword, lastLogin, dateCreated);
                try {
                    //set user to logged in
                    CheckLogin.setLoggedIn(signupActivity.this, true);
                    newUser.createProfile(signupActivity.this);
                    Toast.makeText(signupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (IOException e) {
                    Log.e("SignupActivity", "Error creating user profile", e);
                    Toast.makeText(signupActivity.this, "Error creating user profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String getCurrentDate() {
        return new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
    }
}
