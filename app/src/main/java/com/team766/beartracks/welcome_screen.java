package com.team766.beartracks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    private BroadcastReceiver receiver;

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

    @Override
    public void onPause(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.package.ACTION_LOGIN");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("onReceive", "Login in progress");
                finish();
            }
        };
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onStop(){
        super.onStop();
        unregisterReceiver(receiver);
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
