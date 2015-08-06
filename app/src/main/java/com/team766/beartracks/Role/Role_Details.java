package com.team766.beartracks.Role;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.R;

/**
 * Created by tommypacker on 8/6/15.
 */
public class Role_Details extends AppCompatActivity{

    private TextView name;
    private TextView creator;
    private TextView owner;
    private TextView mentor;
    private TextView supervisor;
    private TextView status;
    private String fireKey, creatorKey, ownerKey;
    private String firebaseURL = "https://beartracks.firebaseio.com/roles/";
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_details);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.role_toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        fireKey = extras.getString("FirebaseKey");

        name = (TextView) findViewById(R.id.name);
        creator = (TextView) findViewById(R.id.creator);
        owner = (TextView) findViewById(R.id.owner);
        mentor = (TextView) findViewById(R.id.mentor);
        supervisor = (TextView) findViewById(R.id.supervisor);
        status = (TextView) findViewById(R.id.status);

        setupTexts();
    }

    private void setupTexts(){
        Firebase roleDetsRef = new Firebase(firebaseURL).child(fireKey);
        roleDetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot detail : dataSnapshot.getChildren()){
                    String holder = detail.getValue().toString();
                    switch (detail.getKey()){
                        case "name":
                            name.setText(holder);
                            break;
                        case "creator":
                            //creatorKey = holder;
                            setupCreator(holder);
                            break;
                        case "owner":
                            //ownerKey = holder;
                            setupOwner(holder);
                            break;
                        case "mentor":
                            mentor.setText(holder);
                            break;
                        case "supervisor":
                            supervisor.setText(holder);
                            break;
                        case "status":
                            status.setText(holder);
                            break;
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setupCreator(String key){
        Firebase creatorRef = new Firebase("https://beartracks.firebaseio.com/people").child(key);

        creatorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot people: dataSnapshot.getChildren()){
                    if(people.getKey().equals("name")){
                        creator.setText(people.getValue().toString());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }

    private void setupOwner(String key){
        Firebase ownerRef = new Firebase("https://beartracks.firebaseio.com/people").child(key);

        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot people: dataSnapshot.getChildren()){
                    if(people.getKey().equals("name")){
                        owner.setText(people.getValue().toString());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

}

