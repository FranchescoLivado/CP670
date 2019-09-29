package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String sharedPreferences = "sharedPreferences";

    Button loginButton;
    EditText email;
    SharedPreferences spf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spf = getSharedPreferences(sharedPreferences, MODE_PRIVATE);

        email = (EditText) findViewById(R.id.textView2);
        email.setText(spf.getString("DefaultEmail","email@domain.com"));
        loginButton = (Button) findViewById(R.id.button2);
        loginButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onLogin(); //stores text in login email field to Shared Preferences
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Log.i(ACTIVITY_NAME,"In onCreate()");
    }

    protected void onLogin(){
        SharedPreferences.Editor editor = spf.edit();
        editor.putString("DefaultEmail", email.getText().toString());
        editor.apply();
    }

    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME,"In onResume()");
    }

    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME,"In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME,"In onPause()");
    }

    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME,"In onStop()");
    }

    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME,"In onDestroy()");
    }
}
