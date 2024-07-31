package edu.usta.cs3443.habitquest;

import static android.content.ContentValues.TAG;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import edu.usta.cs3443.habitquest.model.Goal;

public class set_Goal_Activity extends AppCompatActivity {

    private EditText goalNameEditText, goalDescriptionEditText, goalStartDateEditText, goalEndDateEditText;
    private Spinner goalTypeSpinner;
    private Button addHabitButton, goBackButton, startDateButton, endDateButton;
    private DatePickerDialog startDatePickerDialog, endDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        goalNameEditText = findViewById(R.id.goalNameEditText);
        goalDescriptionEditText = findViewById(R.id.goalDescriptionEditText);
        goalStartDateEditText = findViewById(R.id.goalStartDateEditText);
        goalEndDateEditText = findViewById(R.id.goalEndDateEditText);
        startDateButton = findViewById(R.id.startDateButton);
        endDateButton = findViewById(R.id.endDateButton);

        initDatePicker();

        // Set default date to today
        goalStartDateEditText.setText(getTodaysDate());
        goalEndDateEditText.setText(getTodaysDate());

        goalTypeSpinner = findViewById(R.id.goalTypeSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.goal_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalTypeSpinner.setAdapter(adapter);

        addHabitButton = findViewById(R.id.addHabitButton);
        addHabitButton.setOnClickListener(v -> addAndReadHabit());

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(v -> startActivity(new Intent(set_Goal_Activity.this, MainActivity.class)));

        startDateButton.setOnClickListener(v -> startDatePickerDialog.show());
        endDateButton.setOnClickListener(v -> endDatePickerDialog.show());
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener startDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String date = makeDateString(day, month, year);
            goalStartDateEditText.setText(date);
        };

        DatePickerDialog.OnDateSetListener endDateSetListener = (datePicker, year, month, day) -> {
            month++;
            String date = makeDateString(day, month, year);
            goalEndDateEditText.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        startDatePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, startDateSetListener, year, month, day);
        endDatePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, endDateSetListener, year, month, day);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month++;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        // Return appropriate month format
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
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

        // Path to the external storage file
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "goals.csv");
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
