package edu.usta.cs3443.habitquest;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import edu.usta.cs3443.habitquest.model.Goal;

public class set_Goal_Activity extends AppCompatActivity {

    private static final String TAG = "set_Goal_Activity";

    private EditText goalNameEditText, goalDescriptionEditText, goalStartDateEditText, goalEndDateEditText;
    private CheckBox checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, checkbox6, checkbox7;
    private Button addHabitButton, goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        goalNameEditText = findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = findViewById(R.id.goalDescriptionEditText);
        goalStartDateEditText = findViewById(R.id.goalStartDateEditText);
        goalEndDateEditText = findViewById(R.id.goalEndDateEditText);

        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        checkbox4 = findViewById(R.id.checkbox4);
        checkbox5 = findViewById(R.id.checkbox5);
        checkbox6 = findViewById(R.id.checkbox6);
        checkbox7 = findViewById(R.id.checkbox7);

        addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(v -> addAndReadHabit());

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> finish());
    }

    private void addAndReadHabit() {
        String goalName = goalNameEditText.getText().toString();
        String goalDescription = goalDescriptionEditText.getText().toString();
        String goalStartDate = goalStartDateEditText.getText().toString();
        String goalEndDate = goalEndDateEditText.getText().toString();

        StringBuilder recurrence = new StringBuilder();
        if (checkbox1.isChecked()) recurrence.append("S ");
        if (checkbox2.isChecked()) recurrence.append("M ");
        if (checkbox3.isChecked()) recurrence.append("T ");
        if (checkbox4.isChecked()) recurrence.append("W ");
        if (checkbox5.isChecked()) recurrence.append("TH ");
        if (checkbox6.isChecked()) recurrence.append("F ");
        if (checkbox7.isChecked()) recurrence.append("S ");

        Goal newGoal = new Goal(goalName, "type", goalDescription, goalStartDate, goalEndDate);

        File file = new File(getFilesDir(), "goals.csv");
        Log.d(TAG, "File path: " + file.getAbsolutePath());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(newGoal.getGoalName() + "," + newGoal.getGoalDescription() + "," + newGoal.getGoalStart() + "," + newGoal.getGoalEnd() + "," + recurrence.toString().trim() + "\n");
            Log.d(TAG, "Goal added: " + newGoal.getGoalName());
        } catch (IOException e) {
            Log.e(TAG, "Error writing to CSV", e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "CSV Line: " + line);
            }
        } catch (IOException e) {
            Log.e(TAG, "Error reading CSV", e);
        }

        // Clearing the input fields and checkboxes when goal is created
        goalNameEditText.setText("");
        goalDescriptionEditText.setText("");
        goalStartDateEditText.setText("");
        goalEndDateEditText.setText("");
        checkbox1.setChecked(false);
        checkbox2.setChecked(false);
        checkbox3.setChecked(false);
        checkbox4.setChecked(false);
        checkbox5.setChecked(false);
        checkbox6.setChecked(false);
        checkbox7.setChecked(false);

        // New custom toast message when goal is created
        LinearLayout toastLayout = new LinearLayout(this);
        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
        toastLayout.setBackgroundResource(android.R.drawable.toast_frame)
        toastLayout.setPadding(16, 16, 16, 16);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.habitquest_plum);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(64, 64);
        imageView.setLayoutParams(params);
        imageView.setPadding(0, 0, 16, 0);
        TextView textView = new TextView(this);
        textView.setText("Goal Added!");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(16);

        toastLayout.addView(imageView);
        toastLayout.addView(textView);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
    }
}
