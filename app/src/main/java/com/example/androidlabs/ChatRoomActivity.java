package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity
{
    private ArrayList<Message> elements = new ArrayList<>();
    private MyListAdapter myAdapter;
    SQLiteDatabase db;
    int count = 0;
    boolean isTablet = false;
    public static final String ITEM_MESSAGE = "MESSAGE";
    public static final String ITEM_TYPE = "TYPE";
    public static final String ITEM_ID = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        FrameLayout frame = findViewById(R.id.frame);
        if(frame != null) isTablet = true;

        ListView myList = findViewById(R.id.theListView);
        loadDataFromDatabase();
        myList.setAdapter(myAdapter = new MyListAdapter());

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener( click -> {
            ++count;
            TextView textView = findViewById(R.id.message);
            String messageText = textView.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MESSAGE, messageText);
            newRowValues.put(MyOpener.COL_SENDER, "send");
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            elements.add(new Message("send", messageText, newId));
            myAdapter.notifyDataSetChanged();
            textView.setText("");
            Toast.makeText(this, "Inserted item id:"+newId, Toast.LENGTH_LONG).show();
        });

        Button recButton = findViewById(R.id.receive);
        recButton.setOnClickListener( click -> {
            TextView textView = findViewById(R.id.message);
            String messageText = textView.getText().toString();
            ContentValues newRowValues = new ContentValues();
            newRowValues.put(MyOpener.COL_MESSAGE, messageText);
            newRowValues.put(MyOpener.COL_SENDER, "receive");
            long newId = db.insert(MyOpener.TABLE_NAME, null, newRowValues);
            elements.add(new Message("receive", messageText, newId));
            myAdapter.notifyDataSetChanged();
            textView.setText("");
        });

        myList.setOnItemClickListener( (parent, view, pos, id) -> {
            Bundle dataToPass = new Bundle();
            Message temp = elements.get(pos);
            dataToPass.putString(ITEM_MESSAGE, temp.getMessage() );
            dataToPass.putString(ITEM_TYPE, temp.getType());
            dataToPass.putLong(ITEM_ID, temp.getId());

            if(isTablet)
            {
                DetailsFragment dFragment = new DetailsFragment(); //add a DetailFragment
                dFragment.setArguments( dataToPass ); //pass it a bundle for information
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame, dFragment) //Add the fragment in FrameLayout
                        .commit(); //actually load the fragment. Calls onCreate() in DetailFragment
            }
            else //isPhone
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass); //send data to next activity
                startActivity(nextActivity); //make the transition
            }

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.do_delete)).setMessage(getResources().getString(R.string.desc1) + pos + "\n" + getResources().getString(R.string.desc2) + getDatabaseID(pos))
                    .setPositiveButton("Yes", (click, arg) ->
                    {
                        Message selectedContact = elements.get(pos);
                        deleteContact(selectedContact);
                        elements.remove(pos);
                        myAdapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", (click, arg) -> {  })
                    .create().show();

        }   );
    }

    private class MyListAdapter extends BaseAdapter
    {

        public int getCount() { return elements.size();}

        public Object getItem(int position){return position;}

        public long getItemId(int position) { return position; }

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

    static class Message
    {
        String message;
        String type;
        long id;

        private Message(String type, String message, long i)
        {
            this.type = type;
            this.message = message;
            this.id = i;

        }

        public String getMessage()
        {
            return message;
        }
        public String getType()
        {
            return type;
        }
        public long getId()
        {
            return id;
        }
    }

    private void loadDataFromDatabase()
    {
        //get a database connection:
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase(); //This calls onCreate() if you've never built the table before, or onUpgrade if the version here is newer
        // We want to get all of the columns. Look at MyOpener.java for the definitions:
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_SENDER};
        //query all the results from the database:
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results, db.getVersion());
        //Now the results object has rows of results that match the query.
        //find the column indices:
        int messageColumnIndex = results.getColumnIndex(MyOpener.COL_MESSAGE);
        int senderColIndex = results.getColumnIndex(MyOpener.COL_SENDER);
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        //iterate over the results, return true if there is a next item:
        while(results.moveToNext())
        {
            String message = results.getString(messageColumnIndex);
            String sender = results.getString(senderColIndex);
            long id = results.getLong(idColIndex);

            //add the new Contact to the array list:
            elements.add(new Message(sender, message, id));
        }
    }

    protected void deleteContact(Message c)
    {
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[] {Long.toString(c.getId())});
    }

    protected void printCursor( Cursor c, int version)
    {
        Log.d("version", Integer.toString(version));
        Log.d("Column Quantity", Integer.toString(c.getColumnCount()));
        Log.d("Column Names", c.getColumnName(0) + " " + c.getColumnName(1) + " " + c.getColumnName(2));
        Log.d("Row Quantity", Integer.toString(c.getCount()));

        while(c.moveToNext())
        {
            String index = c.getString( c.getColumnIndex(MyOpener.COL_ID));
            String sender = c.getString( c.getColumnIndex(MyOpener.COL_SENDER));
            String message = c.getString( c.getColumnIndex(MyOpener.COL_MESSAGE));

            String mesg = "index: " + index + " from: " + sender + " message: " + message;

            Log.d("Row", mesg);
        }
        c.moveToPosition(-1);
    }

    private long getDatabaseID(int id)
    {
        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();
        String [] columns = {MyOpener.COL_ID, MyOpener.COL_MESSAGE, MyOpener.COL_SENDER};
        Cursor results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        printCursor(results, db.getVersion());
        int idColIndex = results.getColumnIndex(MyOpener.COL_ID);

        int i = 0;
        long ido = -1;
        while(results.moveToNext())
        {
            if(i == id) ido = results.getLong(idColIndex);
            i++;
        }
        return ido;
    }
}