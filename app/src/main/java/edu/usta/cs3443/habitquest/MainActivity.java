package edu.usta.cs3443.habitquest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.usta.cs3443.habitquest.model.CheckLogin;

/* 
   * MainActivity Controller:
   * Application entry after login
   * Overview user progress
    */

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CheckLogin.isLoggedIn(this)) {
            // User is not logged in, start LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is logged in, proceed to main content

            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
            Context context = this;
            ///    public User(String userName, String userBday, String userPronouns, String userEmail, String user_passwd, String last_login, String date_created){
            //User user = new User("John Doe", "10/12", "he/him", "john.doe@example.com", "Password123!", "07/25/2024", "07/15/2024");
            //user.getUser(this);
            /*try {
                user.createProfile(context);
                //duplicate to test if it works
                user.createProfile(context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }*/

            Button profile_settings, progress_report, todays_goal, set_goal;

            profile_settings = findViewById(R.id.prof_set);
            progress_report = findViewById(R.id.prog_rep);
            todays_goal = findViewById(R.id.todays_goals);
            set_goal = findViewById(R.id.set_goal);

            profile_settings.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, profile_Settings_Activity.class);
                startActivity(intent);
            });
            progress_report.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, progress_report_Actvity.class);
                startActivity(intent);
            });
            todays_goal.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, todays_goal_Activity.class);
                startActivity(intent);
            });
            set_goal.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, set_Goal_Activity.class);
                startActivity(intent);
            });

        }


    }
}
