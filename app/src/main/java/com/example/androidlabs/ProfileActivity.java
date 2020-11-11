package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ProfileActivity extends AppCompatActivity {

    private SharedPreferences prefs = null;
    private EditText typeField;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    private ImageView mImageButton;
    private Intent goToChatRoom;
    private Intent goToWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        EditText email = findViewById(R.id.email);
        prefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        String savedEmail = prefs.getString("email", "");
        typeField = email;
        typeField.setText(savedEmail);

        mImageButton = findViewById(R.id.pictureButton);
        mImageButton.setOnClickListener(bt -> dispatchTakePictureIntent());

        Intent fromMain = getIntent();
        fromMain.getStringExtra("email");
        Log.e(ACTIVITY_NAME, "In function:" + "onCreate");

        Button bu = findViewById(R.id.goToChat);
        bu.setOnClickListener(bt -> startActivity(goToChatRoom));
        goToChatRoom = new Intent(ProfileActivity.this, ChatRoomActivity.class);

        Button weatherBut = findViewById(R.id.goToWeather);
        weatherBut.setOnClickListener(bt -> startActivity(goToWeather));
        goToWeather = new Intent(ProfileActivity.this, WeatherForecast.class);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + "onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + "onResume");

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        saveSharedPrefs(typeField.getText().toString());
        Log.e(ACTIVITY_NAME, "In function:" + "onPause");

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + "onStop");

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + "onDestroy");

    }

    public void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", stringToSave);
        editor.apply();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        Log.e(ACTIVITY_NAME, "In function:" + "onActivityResult");
    }


}