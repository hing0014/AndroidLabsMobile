package com.example.AndroidLabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.BreakIterator;

/*
 * Nas - Game Org (Feb 12, 2018). How to show toast message on click button in Android Studio [Video]. Retrieved from
 * https://www.youtube.com/watch?v=gAPTK9jORuM
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText email;
    private SharedPreferences prefs = null;
    private Button bu;
    private String savedEmail;
    private EditText typeField;
    private BreakIterator emailEditText;
    private Intent goToProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab4);
        email = findViewById(R.id.email);
        prefs = getSharedPreferences("file", Context.MODE_PRIVATE);
        savedEmail = prefs.getString("email", "");
        typeField = email;
        typeField.setText(savedEmail);
        bu = findViewById(R.id.loginButton);
        bu.setOnClickListener(bt -> saveSharedPrefs(typeField.getText().toString()));
        bu.setOnClickListener(bt -> startActivity(goToProfile));
        goToProfile = new Intent(MainActivity.this, ProfileActivity.class);

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        saveSharedPrefs(typeField.getText().toString());
    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

    }

    @Override
    public void onClick(View v)
    {
        goToProfile.putExtra("email", savedEmail);
        emailEditText.setText(savedEmail);
    }

    public void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", stringToSave);
        editor.apply();
    }

}