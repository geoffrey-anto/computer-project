package com.example.computer_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private String Name;
    private Integer Age;

    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;
    private ImageButton imageButton5;
    private ImageButton imageButton6;

    private Map<String, Integer> ageMap;

    private TextView logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        UserData userData = loadUserData();
        this.Name = userData.getName();
        this.Age = userData.getAge();

        this.ageMap = Map.of(
                "Instagram", 13,
                "Snapchat", 13,
                "Facebook", 16,
                "Twitter", 16,
                "WhatsApp", 12,
                "Threads", 13
        );

        TextView welcome_text_view = (TextView) findViewById(R.id.welcome_text_view);

        welcome_text_view.setText(String.format("Welcome %s!", this.Name));

        this.imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        this.imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        this.imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        this.imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        this.imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        this.imageButton6 = (ImageButton) findViewById(R.id.imageButton6);

        if(this.Age < this.ageMap.get("Instagram")) {
            this.imageButton1.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView1)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView1)).setImageResource(R.drawable.unlock);

            this.imageButton1.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/")));
            });
        }

        if(this.Age < this.ageMap.get("Snapchat")) {
            this.imageButton2.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView2)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView2)).setImageResource(R.drawable.unlock);

            this.imageButton2.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.snapchat.com/")));
            });
        }

        if(this.Age < this.ageMap.get("Facebook")) {
            this.imageButton3.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView3)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView3)).setImageResource(R.drawable.unlock);

            this.imageButton3.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/")));
            });
        }

        if(this.Age < this.ageMap.get("Twitter")) {
            this.imageButton4.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView4)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView4)).setImageResource(R.drawable.unlock);

            this.imageButton4.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/")));
            });
        }

        if(this.Age < this.ageMap.get("WhatsApp")) {
            this.imageButton5.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView5)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView5)).setImageResource(R.drawable.unlock);

            this.imageButton5.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/")));
            });
        }

        if(this.Age < this.ageMap.get("Threads")) {
            this.imageButton6.setEnabled(false);
            ((ImageView) findViewById(R.id.statusView6)).setImageResource(R.drawable.lock);
        } else {
            ((ImageView) findViewById(R.id.statusView6)).setImageResource(R.drawable.unlock);

            this.imageButton6.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/direct/threads/")));
            });
        }

        this.logoutButton = (TextView) findViewById(R.id.logoutView);

        this.logoutButton.setOnClickListener(v -> {
            clearUserData();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        });
    }

    private UserData loadUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userDataJson = sharedPreferences.getString("USER_DATA", null);
        if (userDataJson != null) {
            return UserData.fromJson(userDataJson);
        }
        return null;
    }

    private void clearUserData() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}