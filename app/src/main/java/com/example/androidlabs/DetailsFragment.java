package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class DetailsFragment extends Fragment
{
    private AppCompatActivity parentActivity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle dataFromActivity = getArguments();
        long id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID);
        String message = dataFromActivity.getString(ChatRoomActivity.ITEM_MESSAGE);
        String type = dataFromActivity.getString(ChatRoomActivity.ITEM_TYPE);

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.fragment_details, container, false);

        //show the message
        TextView messageTV = (TextView)result.findViewById(R.id.messageHere);
        messageTV.setText(message);

        //show the id:
        TextView IDisTV = (TextView)result.findViewById(R.id.IDis);
        String idStr = "ID =" + id;
        IDisTV.setText(idStr);

        CheckBox checkIt = (CheckBox)result.findViewById(R.id.checkIt);
        if(type.equals("send")) checkIt.setChecked(true);

        // get the delete button, and add a click listener:
        Button finishButton = (Button)result.findViewById(R.id.Hide);
        finishButton.setOnClickListener( clk -> {
            //Tell the parent activity to remove
            parentActivity.getSupportFragmentManager().beginTransaction().remove(this).commit();
            if(!ChatRoomActivity.isTablet)
            {
                parentActivity.onBackPressed();
            }

        });

        return result;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //context will either be FragmentExample for a tablet, or EmptyActivity for phone
        parentActivity = (AppCompatActivity)context;
    }
}