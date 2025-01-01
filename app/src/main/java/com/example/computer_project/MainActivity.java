package com.example.computer_project;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.IOException;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Declaring views
    private Button buttonChoose;
    private Button buttonUpload;
    private ImageView imageView;
    private EditText nameText;
    private  EditText dobDate;

    private Uri filePath;

    //Image request code
    final private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        buttonUpload = (Button) findViewById(R.id.submit_button);
        buttonChoose = (Button) findViewById(R.id.upload_button);
        imageView = (ImageView) findViewById(R.id.imageView);
        nameText = (EditText) findViewById(R.id.editTextText);
        dobDate = (EditText) findViewById(R.id.editTextDate);

        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
    }


    public void submit() {
        if(dobDate.getText().toString().split("/").length != 3) {
            Toast toast = Toast.makeText(this, "Please enter valid DOB (DD/MM/YYYY)!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if(nameText.getText().toString().length() < 3) {
            Toast toast = Toast.makeText(this, "Please enter valid name!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        if(filePath == null || filePath.getPath() == null || filePath.getPath().length() == 0) {
            Toast toast = Toast.makeText(this, "Please upload a ID!", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        UserData userData = new UserData(nameText.getText().toString(), dobDate.getText().toString(), filePath.getPath());

        // Go to home screen
        saveUserData(userData);

        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //Uri to store the image uri
            filePath = data.getData();
            try {
                //Bitmap to get image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if (v == buttonUpload) {
            submit();
        }
    }

    private void saveUserData(UserData userData) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_DATA", userData.toJson());
        editor.apply();
    }
}