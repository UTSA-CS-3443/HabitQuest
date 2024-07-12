package edu.usta.cs3443.habitquest.model;

/**
 * Attributes:
 * user_id - unique identifier for the user.
 * user_bday - user’s birthday.
 * user_pronouns - user’s preferred pronouns.
 * user_email - user’s email.
 * user_passwd - user’s password (stored secretly).
 * last_login - timestamp of the user’s last login.
 * date_created - timestamp of when the user’s profile was created.
 * Methods:
 * createProfile() - creates a new user profile; the method initializes the user’s attributes and saves the profile to the database.
 * updateProfile() - update’s the user’s profile information to include changing: email, password, pronouns.
 * deleteProfile() - deletes the users profile from the database.
 * getDailyGoals() - retrieves the user’s daily goals.
 * getShorttermGoals() - retrieves the user’s short-term goals.
 * getLongtermGoals() - retrieves the user’s long-term goals.
 * getUser()- retrieves profile from database
 */
public class User {
    private String user_id;
    private String user_bday;
    private String user_pronouns;
    private String user_email;
    private String user_passwd;
    private String last_login;
    private String date_created;

    public User(String user_id, String user_bday, String user_pronouns, String user_email, String user_passwd, String last_login, String date_created){
        this.user_id = user_id;
        this.user_bday = user_bday;
        this.user_pronouns = user_pronouns;
        this.user_email = user_email;
        this.user_passwd = user_passwd;
        this.last_login = last_login;
        this.date_created = date_created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_bday() {
        return user_bday;
    }

    public void setUser_bday(String user_bday) {
        this.user_bday = user_bday;
    }

    public String getUser_pronouns() {
        return user_pronouns;
    }

    public void setUser_pronouns(String user_pronouns) {
        this.user_pronouns = user_pronouns;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_passwd() {
        return user_passwd;
    }

    public void setUser_passwd(String user_passwd) {
        this.user_passwd = user_passwd;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
