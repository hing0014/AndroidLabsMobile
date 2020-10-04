package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.text.BreakIterator;

/*
 * Nas - Game Org (Feb 12, 2018). How to show toast message on click button in Android Studio [Video]. Retrieved from
 * https://www.youtube.com/watch?v=gAPTK9jORuM
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    LinearLayout layout;
    SharedPreferences prefs = null;
    Button bu;
    String savedEmail;
    EditText typeField;
    BreakIterator emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab3);
        email = findViewById(R.id.email);
        prefs = getSharedPreferences("emailFile", Context.MODE_PRIVATE);
        savedEmail = prefs.getString("email", "");
        typeField = email;
        typeField.setText(savedEmail);
        bu = findViewById(R.id.loginButton);
        bu.setOnClickListener(bt -> saveSharedPrefs(typeField.getText().toString()));


//        layout = findViewById(R.id.layout);
//        Switch sw = findViewById(R.id.switcher);
//        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            if (isChecked)
//            {
//                Snackbar.make(layout, getResources().getString(R.string.switch_on), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), (v) -> buttonView.setChecked(false)).show();
//            } else
//            {
//                Snackbar.make(layout, getResources().getString(R.string.switch_off), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), (v) -> buttonView.setChecked(true)).show();
//            }
//        });
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
        Intent goToProfile = new Intent(MainActivity.this, ProfileActivity.class);
        goToProfile.putExtra("email", "… what is written in the edit text");
        emailEditText.setText( "… the string from Intent extras …");
    }

    public void saveSharedPrefs(String stringToSave)
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", stringToSave);
        editor.apply();
    }

}