package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

/*
 * Nas - Game Org (Feb 12, 2018). How to show toast message on click button in Android Studio [Video]. Retrieved from
 * https://www.youtube.com/watch?v=gAPTK9jORuM
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button bu;
    Switch sw;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu = findViewById(R.id.button);
        bu.setOnClickListener(this);
        Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message) , Toast.LENGTH_LONG).show();

        layout = findViewById(R.id.gridlayout);
        sw = findViewById(R.id.switcher);
        sw.setOnCheckedChangeListener( (whatClicked, newState) -> {
            // Toast.makeText(MainActivity.this, "Your button is:" + newState, Toast.LENGTH_LONG).show();
            Snackbar.make(layout, getResources().getString(R.string.snack_line), Snackbar.LENGTH_LONG)
                    .setAction("Hello class", (v) -> whatClicked.setChecked(false))
                    .show();
    }
}