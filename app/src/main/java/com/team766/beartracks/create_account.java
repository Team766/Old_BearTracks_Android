package com.team766.beartracks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * Created by tommypacker on 7/29/15.
 */
public class create_account extends AppCompatActivity {

    private Firebase ref;
    private EditText newUserName, newPassword;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        ref = new Firebase("https://beartracks.firebaseio.com");

        newUserName = (EditText) findViewById(R.id.newEmail);
        newPassword = (EditText) findViewById(R.id.newPassword);
    }

    public void createAccountSubmit(View view){
        ref.createUser(newUserName.getText().toString(), newPassword.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                makeToast();
                nextActivity();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                // there was an error
                errorToast();
            }
        });
    }

    private void makeToast(){
        Toast.makeText(this, "Congrats on your new account", Toast.LENGTH_SHORT).show();
    }

    private void errorToast(){
        Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show();
    }

    public void nextActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGIN");
        sendBroadcast(broadcastIntent);
        this.finish();
    }


}
