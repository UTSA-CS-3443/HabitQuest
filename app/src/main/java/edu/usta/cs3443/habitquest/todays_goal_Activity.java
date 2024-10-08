package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.usta.cs3443.habitquest.model.Goal;
import edu.usta.cs3443.habitquest.model.GoalAdapter;
/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class todays_goal_Activity extends AppCompatActivity {
    private static final String TAG = "todays_goal_Activity";
    private RecyclerView recyclerView;
    private GoalAdapter goalAdapter;
    private List<Goal> goalList;
    private Button addHabitButton;
    private Button homeButton;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState creates a new instance of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_todays_goal);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerViewGoals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load goals from CSV and set adapter
        loadGoals();

        homeButton = findViewById(R.id.homeButton);
        addHabitButton = findViewById(R.id.addHabitButton);

        //Home button
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todays_goal_Activity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Add habit button
        addHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(todays_goal_Activity.this, set_Goal_Activity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Loads goals from the CSV file and updates the adapter.
     */
    private void loadGoals() {
        // Load goals from goals.csv file
        goalList = Goal.loadGoalsFromCSV(this);

        // Initialize or update adapter
        if (goalAdapter == null) {
            goalAdapter = new GoalAdapter(goalList, this);
            recyclerView.setAdapter(goalAdapter);
        } else {
            goalAdapter.notifyDataSetChanged(); // Refresh the adapter if it already exists
        }
    }

    /**
     * Called when the activity is resumed.
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadGoals();
    }

}
