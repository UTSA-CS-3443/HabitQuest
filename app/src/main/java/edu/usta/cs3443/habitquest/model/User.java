package edu.usta.cs3443.habitquest.model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public User(String userName, String userBday, String userPronouns, String userEmail, String user_passwd, String last_login, String date_created) {
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

    public String getUser_passwd() {
        return user_passwd;
    }

    public String getUserLastLogin() {
        return last_login;
    }

    public String getDateCreated() {
        return date_created;
    }

    public void createProfile(Context context) throws IOException {
        List<String> lines;
        try {
            lines = loadAllLinesFromFile(context, "users.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean userExists = false;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && (parts[0].equals(userName) || parts[3].equals(userEmail))) {
                userExists = true;
                Log.d("User", "User already exists");
                break;
            }
        }

        if (!userExists) {
            Log.d("User", "User created");
            String newUserLine = String.join(",", userName, userBday, userPronouns, userEmail, user_passwd, last_login, date_created);
            writeToFile(newUserLine, "users.csv", context);
            Log.d("User", newUserLine);
        }
    }

    private static List<String> loadAllLinesFromFile(Context context, String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                // Skip the header line if present
                br.readLine();
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    private void writeToFile(String data, String filename, Context context) {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
        Log.d("User", "File path: " + file.getAbsolutePath());

        try (FileOutputStream outputStream = new FileOutputStream(file, true)) {
            outputStream.write(( data + "\n").getBytes());
            Log.d("User", "User data written to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFile(String filename, Context context) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);

        if (!file.exists()) {
            return "";
        }

        StringBuilder content = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        }

        return content.toString();
    }

    public void getUser(Context context) {
        List<String> lines;

        try {
            //lines = loadAllLinesFromAssets(context, "sample_user.csv");
            lines = loadAllLinesFromInternalStorage(context, "users.csv");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            Log.d("User", line);
        }
    }

    public void editProfile(String userName, String userBday, String userPronouns, String userEmail) {
        setUserName(userName);
        setUserBday(userBday);
        setUserPronouns(userPronouns);
        setUserEmail(userEmail);
    }

    public void updateProfile() {
        // Implementation for updating the profile in the database
    }

    public static User authenticateUser(String email, String password, Context context) throws IOException {
        List<String> lines = loadAllLinesFromFile(context, "users.csv");
        for (String line : lines) {
            String[] parts = line.split(",");
            Log.d("User", "Line: " + line);
            Log.d("User", "Parts: " + parts.length);
            if (parts.length < 7) {
                Log.d("User", "User not authenticated");

            }else if (parts[3].equals(email) && parts[4].equals(password) ) {
                Log.d("User", "User authenticated");
                // If credentials match, return a new User object
                return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
            }else {
                Log.d("User", "User not authenticated");
                // If credentials don't match, return null
            }
        }
        return null; // Return null if no matching user is found
    }

    public static List<String> loadAllLinesFromInternalStorage(Context context, String filename) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
        return Files.readAllLines(file.toPath());
    }

    private static List<String> loadAllLinesFromAssets(Context context, String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)))) {
            // Clear the first line
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private void copyUsersFileToInternalStorage(Context context) {
        try {
            InputStream in = context.getAssets().open("users.csv");
            File outFile = new File(context.getFilesDir(), "users.csv");
            FileOutputStream out = new FileOutputStream(outFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if (goal.isGoalCompleted()) {
                completedGoals.add(goal);
            } else {
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
