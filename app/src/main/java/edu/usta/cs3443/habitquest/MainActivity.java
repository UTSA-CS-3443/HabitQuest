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

import edu.usta.cs3443.habitquest.model.User;

/* 
   * MainActivity Controller:
   * Application entry after login
   * Overview user progress
    */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Context context =this;
        User user = new User("username", "password","email","name","phone","address","gender");
        user.getUser(this);
        user.createProfile(context);

        Button profile_settings,progress_report,todays_goal,set_goal;

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
