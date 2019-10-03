package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {
    Button send;
    EditText search;
    ListView list;
    ArrayList<String> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        send = (Button) findViewById(R.id.button4);
        search = (EditText) findViewById(R.id.textView6);
        list = (ListView) findViewById(R.id.listView);

        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String listItem = search.getText().toString();
                array.add(listItem);
            }
        });

    }
}
