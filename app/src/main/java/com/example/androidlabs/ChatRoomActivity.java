package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
{
    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        ListView myList = findViewById(R.id.theListView);
        myList.setAdapter(myAdapter = new MyListAdapter());

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener( click -> {
            TextView textView = (TextView) findViewById(R.id.message);
            String messageText = textView.getText().toString();
            Message message = new Message("send", messageText);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
        });

        Button recButton = findViewById(R.id.receive);
        recButton.setOnClickListener( click -> {
            TextView textView = (TextView) findViewById(R.id.message);
            String messageText = textView.getText().toString();
            Message message = new Message("receive", messageText);
            elements.add(message);
            myAdapter.notifyDataSetChanged();
        });

        myList.setOnItemClickListener( (parent, view, pos, id) -> {
            elements.remove(pos);
            myAdapter.notifyDataSetChanged();
        }   );
    }

    private class MyListAdapter extends BaseAdapter
    {

        public int getCount() { return elements.size();}

        public Object getItem(int position){return position;}

        public long getItemId(int position) { return (long) position; }

        public View getView(int position, View old, ViewGroup parent)
        {
            View newView;
            Message arEl = elements.get(position);
            LayoutInflater inflater = getLayoutInflater();
            if(arEl.getType().equals("send"))
            {
                newView = inflater.inflate(R.layout.send_layout, parent, false);
                TextView messageText = newView.findViewById(R.id.textGoesHere);
                messageText.setText(arEl.getMessage());
            }
            else
            {
                newView = inflater.inflate(R.layout.receive_layout, parent, false);
                TextView messageText = newView.findViewById(R.id.textGoesHere);
                messageText.setText(arEl.getMessage());
            }
            return newView;
        }
    }

    private static class Message
    {
        String message;
        String type;

        private Message(String type, String message)
        {
            setMessage(message);
            setType(type);
        }

        public String getMessage()
        {
            return message;
        }
        public String getType()
        {
            return type;
        }
        private void setMessage(String message)
        {
            this.message = message;
        }
        private void setType(String type)
        {
            this.type = type;
        }
    }
}