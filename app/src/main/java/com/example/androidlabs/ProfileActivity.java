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

    EditText email;
    LinearLayout layout;
    SharedPreferences prefs = null;
    Button bu;
    String savedEmail;
    EditText typeField;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageButton imgBu;
    ImageView mImageButton;
    Intent fromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        email = findViewById(R.id.email);
        prefs = getSharedPreferences("emailFile", Context.MODE_PRIVATE);
        savedEmail = prefs.getString("email", "");
        typeField = email;
        typeField.setText(savedEmail);

        imgBu = findViewById(R.id.pictureButton);
        imgBu.setOnClickListener(bt -> dispatchTakePictureIntent());

        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);

        fromMain = getIntent();
        fromMain.getStringExtra("email");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        saveSharedPrefs(typeField.getText().toString());
        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function:" + /* replace with function name */);

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
            Bitmap imageBitmap = (Bitmap) extras.get("emailFile");
            mImageButton.setImageBitmap(imageBitmap);
        }
    }


}