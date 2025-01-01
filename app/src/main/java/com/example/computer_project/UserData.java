package com.example.computer_project;

import java.util.Calendar;

import com.google.gson.Gson;

public class UserData {
    private String name;
    private String dob;
    private String imagePath;
    private Integer age;

    public UserData() {
    }

    public UserData(String name, String dob, String imagePath) {
        this.name = name;
        this.dob = dob;
        this.imagePath = imagePath;
        this.age = calculateAge(dob);
    }

    public UserData(String name, String dob, String imagePath, Integer age) {
        this.name = name;
        this.dob = dob;
        this.imagePath = imagePath;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
        this.age = calculateAge(dob);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Integer getAge() {
        return age;
    }

    private Integer calculateAge(String dob) {
        if (dob == null || dob.isEmpty()) {
            throw new IllegalArgumentException("DOB cannot be null or empty");
        }

        String[] dobParts = dob.split("/");
        if (dobParts.length != 3) {
            throw new IllegalArgumentException("DOB must be in the format DD/MM/YYYY");
        }

        int day = Integer.parseInt(dobParts[0]);
        int month = Integer.parseInt(dobParts[1]) - 1;
        int year = Integer.parseInt(dobParts[2]);

        Calendar dobCal = Calendar.getInstance();
        dobCal.set(year, month, day);

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public void print() {
        System.out.println("Name: " + this.name);
        System.out.println("DOB: " + this.dob);
        System.out.println("Image Path: " + this.imagePath);
        System.out.println("Age: " + this.age);
    }

    public String toStringData() {
        return String.join(";", this.name, this.dob, this.imagePath, String.valueOf(this.age));
    }

    public static UserData fromStringData(String data) {
        String[] parts = data.split(";");
        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid data format");
        }

        String name = parts[0];
        String dob = parts[1];
        String imagePath = parts[2];
        int age = Integer.parseInt(parts[3]);

        return new UserData(name, dob, imagePath, age);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static UserData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, UserData.class);
    }
}
