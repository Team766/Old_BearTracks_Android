package com.team766.beartracks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by tommypacker on 8/5/15.
 */
public class Person_Details extends AppCompatActivity {

    private String firebaseURL = "https://beartracks.firebaseio.com/people/";
    private String fireKey;
    TextView name;
    TextView email;
    TextView phone;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_details);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);

        Bundle extras = getIntent().getExtras();

        fireKey = extras.getString("FirebaseKey");

        setTexts();

    }

    private void setTexts(){
        Firebase peopleDetsRef = new Firebase(firebaseURL).child(fireKey);
        peopleDetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot detail : dataSnapshot.getChildren()) {
                    String holder = detail.getValue().toString();
                    switch (detail.getKey()){
                        case "name":
                            name.setText(holder);
                            break;
                        case "email":
                            email.setText(holder);
                            break;
                        case "phone":
                            phone.setText(holder);
                            break;
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void makeToast(String name){
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

}
