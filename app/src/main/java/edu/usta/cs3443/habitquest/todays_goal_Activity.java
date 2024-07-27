package edu.usta.cs3443.habitquest;

import android.os.Bundle;
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

public class todays_goal_Activity extends AppCompatActivity {
    private static final String TAG = "todays_goal_Activity";
    private RecyclerView recyclerView;
    private GoalAdapter goalAdapter;
    private List<Goal> goalList;

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
    }

    private void loadGoals() {
        // Load goals from goals.csv file
        goalList = Goal.loadGoalsFromCSV(this);

        // Initialize or update adapter
        if (goalAdapter == null) {
            goalAdapter = new GoalAdapter(goalList);
            recyclerView.setAdapter(goalAdapter);
        } else {
            goalAdapter.notifyDataSetChanged(); // Refresh the adapter if it already exists
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGoals();
    }
}
