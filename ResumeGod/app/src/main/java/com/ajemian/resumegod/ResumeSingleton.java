package com.ajemian.resumegod;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by Kudo on 10/8/16.
 */
public class ResumeSingleton {
    private static ResumeSingleton ourInstance = new ResumeSingleton();

    public static ResumeSingleton getInstance() {
        return ourInstance;
    }


    public ResumeGodAPI getAPI() {
        return API;
    }

    public void setAPI(ResumeGodAPI API) {
        this.API = API;
    }

    private ResumeGodAPI API;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getDesignation() {
        return designation;
    }

    public void setDesignation(int designation) {
        this.designation = designation;
    }

    private int designation;

    private SQLiteDatabase database;

    public void setDatabase(SQLiteDatabase database){
        this.database = database;
        database.execSQL("CREATE TABLE IF NOT EXISTS JobSeekers (email VARCHAR(250), first_name VARCHAR(250), last_name VARCHAR(250), profile_picture VARCHAR(250), skills VARCHAR(250));");
        database.execSQL("CREATE TABLE IF NOT EXISTS StrongHire (email VARCHAR(250), strong_hire INT);");
    }

    public ArrayList<JobSeekerDatabaseModel> getJobseekersFromDatabase(){
        ArrayList<JobSeekerDatabaseModel> jobseekers = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM JobSeekers", null);
        int columnEmail = c.getColumnIndex("email");
        int columnFirstName = c.getColumnIndex("first_name");
        int columnLastName = c.getColumnIndex("last_name");
        int columnSkills = c.getColumnIndex("skills");
        int columnProfilePicture = c.getColumnIndex("profile_picture");
        c.moveToFirst();
        if (c != null){
            do {
                try {
                    String email = c.getString(columnEmail);
                    String first_name = c.getString(columnFirstName);
                    String last_name = c.getString(columnLastName);
                    String skills = c.getString(columnSkills);
                    String profile_picture = c.getString(columnProfilePicture);
                    jobseekers.add(new JobSeekerDatabaseModel(email, skills, first_name, last_name, profile_picture));
                }catch(Exception e){

                }
            }while(c.moveToNext());
        }
        return jobseekers;
    }

    public ArrayList<StrongHireModel> getStrongHireFromDatabase(){
        ArrayList<StrongHireModel> strongHires = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM StrongHire", null);
        int columnEmail = c.getColumnIndex("email");
        int columnStrongHire = c.getColumnIndex("strong_hire");

        c.moveToFirst();
        if (c != null){
            do {
                try {
                    String email = c.getString(columnEmail);
                    int strongHire = c.getInt(columnStrongHire);
                    strongHires.add(new StrongHireModel(email, strongHire));
                }catch(Exception e){

                }

            }while(c.moveToNext());
        }
        return strongHires;
    }

    public void updateStrongHireWithEmail(String email, int toStrongHire){
        if(database != null){
            database.execSQL("UPDATE StrongHire SET strong_hire=" + toStrongHire+ " WHERE email='"+email + "';");
        }
    }

    public void insertIntoDatabaseJobSeeker(JobseekerResponse profile){
        if (database != null){
            //email, first_name, last_name,  profile_picture, skills
            String email = profile.getEmail();
            String first_name = profile.getFirst_name();
            String last_name = profile.getLast_name();
            String profile_picture = profile.getProfile_picture();
            String skills = profile.getSkill_1() + " and " + profile.getSkill_2() + " and " + profile.getSkill_3();
            database.execSQL("INSERT INTO JobSeekers VALUES ('" + email +
                    "', '" + first_name +
                    "', '" + last_name +
                    "', '" + profile_picture +
                    "', '" + skills + "');");
            database.execSQL("INSERT INTO StrongHire VALUES ('" + email + "', " + "0)");

        }
    }


    public void clearDatabase(){
        if(database != null){
            database.execSQL("DELETE FROM JobSeekers");
            database.execSQL("DELETE FROM StrongHire");
        }

    }




    private String email;
    private String token;

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    private Location lastLocation;

    public JobseekerResponse getJobseekerResponse() {
        return jobseekerResponse;
    }

    public void setJobseekerResponse(JobseekerResponse jobseekerResponse) {
        this.jobseekerResponse = jobseekerResponse;
    }

    public EmployerResponse getEmployerResponse() {
        return employerResponse;
    }

    public void setEmployerResponse(EmployerResponse employerResponse) {
        this.employerResponse = employerResponse;
    }

    private JobseekerResponse jobseekerResponse;
    private EmployerResponse employerResponse;

    private ResumeSingleton() {
        setAPI(new ResumeGodAPI("http://strafeto.win/api"));
    }
}
