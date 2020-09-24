package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;

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
    GridLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bu = findViewById(R.id.button);
        bu.setOnClickListener(this);



        layout = findViewById(R.id.gridlayout);
        Switch sw = findViewById(R.id.switcher);
        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                Snackbar.make(layout, getResources().getString(R.string.switch_on), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), (v) -> buttonView.setChecked(false)).show();
            } else
            {
                Snackbar.make(layout, getResources().getString(R.string.switch_off), Snackbar.LENGTH_LONG).setAction(getResources().getString(R.string.undo), (v) -> buttonView.setChecked(true)).show();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_message), Toast.LENGTH_LONG).show();
    }
}