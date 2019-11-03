package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.myapplication.ChatDatabaseHelper.*;


public class ChatWindow extends AppCompatActivity {
    Button send;
    EditText search;
    ListView list;
    ArrayList<String> array;
    ChatAdapter messageAdapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        send = (Button) findViewById(R.id.button4);
        search = (EditText) findViewById(R.id.textView6);
        list = (ListView) findViewById(R.id.listview);
        array = new ArrayList<String>();
        messageAdapter = new ChatAdapter(this);
        //initialize database helper
        final ChatDatabaseHelper helper = new ChatDatabaseHelper(this);


        list.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //add msg from edit text search bar to array
                array.add(search.getText().toString());
                //Add msg from edit text search bar to database
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(KEY_MESSAGE, search.getText().toString());
                db.insert(TABLE_ITEMS,null, values);

                messageAdapter.notifyDataSetChanged();
                search.setText("");
            }
        });
        // A3 Edits Begin
        ChatDatabaseHelper dhHelper = new ChatDatabaseHelper(this);
        db = dhHelper.getWritableDatabase();

        //use query() or rawQuery() to retrieve any existing chat msgs and add them to the ArrayList "array"
        String query = "SELECT " + KEY_MESSAGE + " FROM " + TABLE_ITEMS;
        //call what current activity is and put it to string for Logs
        String ACTIVITY_NAME = getCallingActivity().toString();

        Cursor c = db.rawQuery(query,null);
        int colIndex = c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE); //Do I access KEY_MESSAGE or TABLE_NAME

        c.moveToFirst();
        while(!c.isAfterLast() ) {
            String msg = c.getString(colIndex);
            array.add(msg);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + c.getString( c.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE)));
            Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + c.getColumnCount());
            c.moveToNext();
        };

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++){
            String column = c.getColumnName(colIndex);
            System.out.print("Column: " + column);
            c.moveToNext();
        }



    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount(){
            return array.size();
        }

        public String getPosition(int position){
            return array.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position%2 == 0){
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView) result.findViewById(R.id.messages);
            message.setText(getPosition(position));

            return result;
        }
    }

    protected void onDestroy(){
        db.close();
        super.onDestroy();
    }
}
