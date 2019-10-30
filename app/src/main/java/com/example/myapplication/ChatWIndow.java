package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Button send;
    EditText search;
    ListView list;
    ArrayList<String> array;
    ChatAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        send = (Button) findViewById(R.id.button4);
        search = (EditText) findViewById(R.id.textView6);
        list = (ListView) findViewById(R.id.listview);
        array = new ArrayList<String>();
        messageAdapter = new ChatAdapter(this);

        list.setAdapter(messageAdapter);

        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                array.add(search.getText().toString());
                messageAdapter.notifyDataSetChanged();
                search.setText("");
            }
        });
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
}
