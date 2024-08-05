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
/**
 *
 * @author Katarah (Kat) Griffin,Alistair Chambers, Muskan Devi,Christian (Ian) Fernandez
 *
 * Cs 3443 Summer 2024 - Group Project
 */
public class User {
    private String userName;
    private String userBday;
    private String userPronouns;
    private String userEmail;
    private final ArrayList<Goal> goals;
    private final Analytics analytics;
    private final String user_passwd;
    private final String last_login;
    private final String date_created;

    /**
     * Constructor for User class
     * @param userName username of user
     * @param userBday birthday of user
     * @param userPronouns pronouns of user
     * @param userEmail email of user
     * @param user_passwd password of user
     * @param last_login last login of user
     * @param date_created date created of user
     */
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

    /**
     * Getter for username
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for username
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for birthday
     * @return birthday
     */
    public String getUserBday() {
        return userBday;
    }

    /**
     * Setter for birthday
     * @param userBday birthday
     */
    public void setUserBday(String userBday) {
        this.userBday = userBday;
    }

    /**
     * Getter for pronouns
     * @return pronouns
     */
    public String getUserPronouns() {
        return userPronouns;
    }

    /**
     * Setter for pronouns
     * @param userPronouns pronouns
     */
    public void setUserPronouns(String userPronouns) {
        this.userPronouns = userPronouns;
    }

    /**
     * Getter for email
     * @return email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Setter for email
     * @param userEmail email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Creates a profile for the user
     * @param context gets the context of the application
     * @throws IOException if the file cannot be read
     */
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
            if (parts.length >= 2 && ( parts[3].equals(userEmail))) {
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

    /**
     * Loads all lines from a file
     * @param context gets the context of the application
     * @param filename the name of the file
     * @return a list of lines
     * @throws IOException if the file cannot be read
     */
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

    /**
     * Writes to a file
     * @param data data to write
     * @param filename name of the file
     * @param context gets the context of the application
     */
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

    /**
     * Reads from a file
     * @param filename name of the file
     * @param context gets the context of the application
     * @return a string of the file
     * @throws IOException if the file cannot be read
     */
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

    /**
     * Gets the user from the file
     * @param context gets the context of the application
     */
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

    /**
     * Authenticates the user
     * @param email email of the user
     * @param password password of the user
     * @param context gets the context of the application
     * @return a User object if the user is authenticated
     * @throws IOException if the file cannot be read
     */
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
        return null;
        // Return null if no matching user is found
    }

    /**
     * Loads all lines from internal storage
     * @param context gets the context of the application
     * @param filename name of the file
     * @return a list of lines
     * @throws IOException if the file cannot be read
     */
    public static List<String> loadAllLinesFromInternalStorage(Context context, String filename) throws IOException {
        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), filename);
        return Files.readAllLines(file.toPath());
    }


    /**
     * Copies the users file to internal storage
     * @param context gets the context of the application
     */
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

    /**
     * Gets the goals of the user
     * @return a list of goals
     */
    public ArrayList<Goal> getGoals() {
        return goals;
    }

    /**
     * Gets the analytics of the user
     * @return the analytics
     */
    public Analytics getAnalytics() {
        return analytics;
    }

    /**
     * Generates a progress report for the user
     * @param context gets the context of the application
     * @return a string of the progress report
     * @throws IOException if the file cannot be read
     */
    public String generateProgressReport(Context context) throws IOException {
        return analytics.generateProgressReport(context);
    }
}
