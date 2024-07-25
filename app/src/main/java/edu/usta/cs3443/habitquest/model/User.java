package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Attributes:
 * userName - user's name data
 * userBday - user’s birthday data
 * userPronouns - user’s preferred pronouns data
 * userEmail - user’s email data
 * user_passwd - user's password data (to be hidden)
 * goals - stores the user's goals in arraylist
 * analytics - object to pull user analytic data from
 *
 * Methods:
 * getUserName() - retrieves user's name from database.
 * getUserBday() - retrieves user bday.
 * getUserPronouns() - retrieves user’s set pronouns.
 * getUserEmail() - retrieves the user's email.
 * getUserPasswd - retrieve the user password.
 * setUserName() - set profile from database.
 * setUserBday() - set user bday.
 * setUserPronouns() - set user’s set pronouns.
 * setUserEmail() - set the user's email.
 * setUserPasswd() - set the user's password
 * createProfile() - creates a new user profile; the method initializes the user’s attributes and saves the profile to the database.
 * getUser() - retrieves user from database
 * editProfile() - update’s the user’s profile information to include changing: email, * password, pronouns.
 * addGoal(Goals goal) - add a new goal/habit.
 * deleteGoal(Goals goal) - delete an existing goal/habit.
 * editGoal(Goals goal) - edit an existing goal/habit.
 * generateProgressReport() - pulls user data for analytics.
 */

public class User {
    private String userName;
    private String userBday;
    private String userPronouns;
    private String userEmail;
    private final ArrayList<Goal> goals;
    private final Analytics analytics;
    private String user_passwd;
    private String last_login;
    private String date_created;

    public User(String userName, String userBday, String userPronouns, String userEmail, String user_passwd, String last_login, String date_created){
        this.userName = userName;
        this.userBday = userBday;
        this.userPronouns = userPronouns;
        this.userEmail = userEmail;
        this.user_passwd = user_passwd;
        this.last_login = last_login;
        this.date_created = date_created;
        this.goals = new ArrayList<>();
        this.analytics = new Analytics();

    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserBday() {
        return userBday;
    }

    public void setUserBday(String userBday) {
        this.userBday = userBday;
    }

    public String getUserPronouns() {
        return userPronouns;
    }

    public void setUserPronouns(String userPronouns) {
        this.userPronouns = userPronouns;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    //saves user to database/csv
    public void createProfile(Context context) throws IOException {
        //checks if user credentials are valid/ arent in use
        //checks if there is user database file
        List<String> lines;
        try {
            lines = loadAllLinesFromAssets(context, "sample_user.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean userExists = false;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && (parts[0].equals(userName) || parts[3].equals(userEmail))) {
                userExists = true;
                Log.d("User", "User already exists");
                break; // Exit the loop if a match is found
            }
        }

        if (!userExists) {
            Log.d("User", "User created");
            // Add user to database (implementation needed)

            // Add user to CSV file (assuming writeToFile appends)
            String newUserLine = String.join(",", userName, userBday, userPronouns, userEmail, user_passwd, last_login, date_created);
            writeToFile(newUserLine, "sample_user.csv", context);

            //print user data
            Log.d("User", newUserLine);


        }


    }
    private void writeToFile(String data, String filename, Context context ) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
        Log.d("User", "File path: " + file.getAbsolutePath());
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            Log.d("User", "User created");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //read from database/csv
    public String readFile(String filename, Context context) throws IOException {
        // Get the file object using the previously created approach (assuming context is available)
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);

        // Check if the file exists
        if (!file.exists()) {
            // Handle the case where the file doesn't exist (e.g., return an empty string)
            return "";
        }

        // Read the file content
        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }

        // Return the file content as a string
        return content.toString();
    }

    //loads user from database/csv
    public void getUser(Context context) {
        //checks if there is user database file
        List<String> lines;

        try {
            lines = loadAllLinesFromAssets(context, "sample_user.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //prints user data
        for (String line : lines) {
            String[] parts = line.split(",");
            Log.d("User", line);
        }

    }

    public void editProfile(String userName, String userBday, String userPronouns, String userEmail) {
        setUserName(userName);
        setUserBday(userBday);
        setUserPronouns(userPronouns);
        setUserEmail(userEmail);
    }

    //updates user's profile
    public void updateProfile() {

    }
    //loads data from database/csv into a list

    private List<String> loadAllLinesFromAssets(Context context, String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        AssetManager assetManager = context.getAssets();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open(filename)))) {
            //clear first line
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file: " + filename, e);
        }

        return lines;
    }

    public void addGoal(Goal goal) {
        this.goals.add(goal);
        updateAnalytics();
    }

    public void deleteGoal(Goal goal) {
        this.goals.remove(goal);
        updateAnalytics();
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    private void updateAnalytics() {
        ArrayList<Goal> completedGoals = new ArrayList<>();
        ArrayList<Goal> activeGoals = new ArrayList<>();

        for (Goal goal : goals) {
            if (goal.isGoalCompleted()) {  // Changed method name from isCompleted to isGoalCompleted
                completedGoals.add(goal);
            }
            else {
                activeGoals.add(goal);
            }
        }

        analytics.setGoalsCompleted(completedGoals);
        analytics.setActiveGoals(activeGoals);
    }

    public ArrayList<Goal> getCompletedGoals() {
        return analytics.getGoalsCompleted();
    }

    public ArrayList<Goal> getActiveGoals() {
        return analytics.getActiveGoals();
    }

    public ArrayList<Goal> getGoalsNotComplete() {
        return analytics.getGoalsNotComplete();
    }

    public String generateProgressReport() {
        return analytics.generateProgressReport();
    }
}
