package com.team766.beartracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by tommypacker on 7/29/15.
 */
public class login_activity extends AppCompatActivity {

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private EditText mUserName, passwordy;
    private Firebase ref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ref = new Firebase("https://beartracks.firebaseio.com/people");

        settings = getSharedPreferences(welcome_screen.PREFS_NAME, MODE_PRIVATE);
        editor = settings.edit();

        setContentView(R.layout.login_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        setTitle("Login");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mUserName = (EditText) findViewById(R.id.userName);
        passwordy = (EditText) findViewById(R.id.passwordy);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void passwordSubmit(View view){
        ref.authWithPassword(mUserName.getText().toString(), passwordy.getText().toString(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                editor.putBoolean("hasLoggedIn", true);
                editor.putString("userEmail", mUserName.getText().toString());
                editor.commit();
                nextActivity();
            }
            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                makeToast();
            }
        });

    }

    private void makeToast(){
        Toast.makeText(this, "Please enter the correct password", Toast.LENGTH_SHORT).show();
    }

    public void nextActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        this.finish();
    }

}
