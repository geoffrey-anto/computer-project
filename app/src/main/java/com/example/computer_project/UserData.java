package com.example.computer_project;

import java.util.Calendar;
import java.util.Date;

public class UserData {
    public String Name;
    public String DOB;
    public String ImagePath;
    public Integer Age;

    UserData() {

    }

    UserData(String Name, String DOB, String ImagePath) {
        this.Name = Name;
        this.DOB = DOB;
        this.ImagePath = ImagePath;

        this.Age = calculateAge(DOB);
    }

    UserData(String Name, String DOB, String ImagePath, Integer Age) {
        this.Name = Name;
        this.DOB = DOB;
        this.ImagePath = ImagePath;

        this.Age = Age;
    }

    Integer calculateAge(String dob) {
        String[] dobParts = dob.split("/");
        Integer day = Integer.parseInt(dobParts[0]);
        Integer month = Integer.parseInt(dobParts[1]);
        Integer year = Integer.parseInt(dobParts[2]);

        Calendar dobCal = Calendar.getInstance();
        dobCal.set(year, month, day);

        Calendar today = Calendar.getInstance();

        Integer age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }


    void print() {
        System.out.println(this.Name);
        System.out.println(this.DOB);
        System.out.println(this.ImagePath);
    }

    String toStringData() {
        return this.Name + ";" + this.DOB + ";" + this.ImagePath + ";" + this.Age;
    }

    static UserData fromStringData(String data) {
        String name = data.split(";")[0];
        String dob = data.split(";")[1];
        String imagePath = data.split(";")[2];
        Integer age = Integer.parseInt(data.split(";")[3]);

        return new UserData(name, dob, imagePath, age);
    }
}
