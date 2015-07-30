package com.team766.beartracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;

/**
 * Created by tommypacker on 7/29/15.
 */
public class welcome_screen extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "MyPrefsFile";
    //protected Firebase ref = new Firebase("https://beartracks.firebaseio.com");

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (settings.getBoolean("hasLoggedIn", false)) {
            moveToHome();
        }

        setContentView(R.layout.welcome_sreen);
    }

    public void existingLogin(View view){
        Intent intent = new Intent(getApplicationContext(), login_activity.class);
        startActivity(intent);
    }

    public void createNewAccount(View view){
        Intent intent = new Intent(getApplicationContext(), create_account.class);
        startActivity(intent);
    }


    public void moveToHome(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        this.finish();
    }

}
