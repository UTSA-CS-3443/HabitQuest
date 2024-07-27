package edu.usta.cs3443.habitquest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import edu.usta.cs3443.habitquest.model.Goal;

public class set_Goal_Activity extends AppCompatActivity {

    private static final String TAG = "set_Goal_Activity";

    private EditText goalNameEditText, goalDescriptionEditText, goalStartDateEditText, goalEndDateEditText;
    private CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7;
    private Spinner goalTypeSpinner;
    private Button addHabitButton, goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        goalNameEditText = findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = findViewById(R.id.goalDescriptionEditText);
        goalStartDateEditText = findViewById(R.id.goalStartDateEditText);
        goalEndDateEditText = findViewById(R.id.goalEndDateEditText);
        goalTypeSpinner = findViewById(R.id.goalTypeSpinner);

        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        checkbox4 = findViewById(R.id.checkbox4);
        checkbox5 = findViewById(R.id.checkbox5);
        checkbox6 = findViewById(R.id.checkbox6);
        checkbox7 = findViewById(R.id.checkbox7);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.goal_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalTypeSpinner.setAdapter(adapter);

        addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(v -> addAndReadHabit());

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> {
                    Intent intent = new Intent(set_Goal_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
        );
    }

    private void addAndReadHabit() {
        String goalName = goalNameEditText.getText().toString();
        String goalDescription = goalDescriptionEditText.getText().toString();
        String goalStartDate = goalStartDateEditText.getText().toString();
        String goalEndDate = goalEndDateEditText.getText().toString();
        String goalType = (String) goalTypeSpinner.getSelectedItem();

        if (goalName.isEmpty() || goalDescription.isEmpty() || goalStartDate.isEmpty() || goalEndDate.isEmpty() || goalType.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Goal object
        Goal newGoal = new Goal(goalName, goalType, goalDescription, goalStartDate, goalEndDate);

        // Path to the internal storage file
        File file = new File(getFilesDir(), "goals.csv");
        Log.d(TAG, "File path: " + file.getAbsolutePath());

        // Add the habit to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(newGoal.getGoalName() + "," + newGoal.getGoalType() + "," + newGoal.getGoalDescription() + "," + newGoal.getGoalStart() + "," + newGoal.getGoalEnd() + "," + "false" + "\n");
            Log.d(TAG, "Goal added: " + newGoal.getGoalName());
            Toast.makeText(this, "Goal added successfully!", Toast.LENGTH_SHORT).show();

            // Redirect to today's goals activity
            Intent intent = new Intent(set_Goal_Activity.this, todays_goal_Activity.class);
            startActivity(intent);
        } catch (IOException e) {
            Log.e(TAG, "Error writing to CSV", e);
            Toast.makeText(this, "Error adding goal", Toast.LENGTH_SHORT).show();
        }
    }
}
