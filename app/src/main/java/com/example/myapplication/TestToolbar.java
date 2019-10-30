package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    EditText new_message;
    String snackbarMsg = "You selected item 1";
    Snackbar snackbar;
    TextView snackBarText;
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
                Snackbar.make(getWindow().getDecorView(), snackbarMsg, Snackbar.LENGTH_LONG).show();
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:

                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                //Do you want to go back? Prompt
                builder.setTitle(R.string.goBack);
                //Buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //User clicked ok, finish activity
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        //User cancelled so do nothing
                    }
                });
                builder.show();
                Log.d("Toolbar", "Option 2 selected");
                break;
            case R.id.action_three:
                AlertDialog.Builder cBuilder = new AlertDialog.Builder(TestToolbar.this);
                LayoutInflater inflater = TestToolbar.this.getLayoutInflater();
                final View v = inflater.inflate(R.layout.custom_dialog_box, null);
                cBuilder.setView(v)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Snackbar.make(getWindow().getDecorView(),new_message.toString(), Snackbar.LENGTH_LONG);
                        //snackBarText = snackbar.getView().findViewById(com.google.android.material.R.id.snackbar_text);
                        //snackbar.setText(new_message.getText().toString());
                        new_message = (EditText)v.findViewById(R.id.new_message);
                        snackbarMsg = new_message.getText().toString();
                    }
                });
                cBuilder.show();
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
