package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "New Message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id;
        id = mi.getItemId();

        switch(id){
            case R.id.action_one:
                Snackbar.make(getWindow().getDecorView(), "You selected item 1", Snackbar.LENGTH_LONG).show();
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                Log.d("Toolbar", "Option 2 selected");
                break;
            case R.id.action_three:
                Log.d("Toolbar", "Option 3 selected");
                break;
            case R.id.action_four:
                Toast.makeText(this, "Version 1.0, by Franchesco", Toast.LENGTH_LONG).show();
                Log.d("Toolbar", "Option 4 selected");
                break;
        }

        return true;
    }

}
