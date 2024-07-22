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

import edu.usta.cs3443.habitquest.model.CheckLogin;

/*
 * LoginActivity Controller:
 * User creation / login
 * Transition to MainActivity if user is logged in
*/

public class LoginActivity extends AppCompatActivity {
    Button loginButton, skipButton;
    EditText username;
    EditText password;

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

        loginButton.setOnClickListener(v -> {
            //gets username and password from EditText
            String username = this.username.getText().toString();
            String password = this.password.getText().toString();
            //checks if username and password are valid
            if (username.isEmpty() || password.isEmpty()) {
                //create a toast to tell the user to enter a username and password
                Toast.makeText(this, "Please enter a username and password", Toast.LENGTH_SHORT).show();


            }else{
                //needs to be worked on
                Log.d("LoginActivity", "Username: " + username + ", Password: " + password);
            }

        });

        skipButton.setOnClickListener(v -> {
            //set logged in to true
            CheckLogin.setLoggedIn(this,true);
            //goes to MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        });



    }
}

