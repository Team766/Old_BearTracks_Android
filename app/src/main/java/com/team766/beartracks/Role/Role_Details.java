package com.team766.beartracks.Role;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tommypacker on 8/6/15.
 */
public class Role_Details extends AppCompatActivity{

    private TextView name;
    private TextView creator;
    private TextView owner;
    private TextView mentor;
    private TextView status;
    private ExpandableListView expandableListView;
    private List<String> headers = new ArrayList<String>();
    private HashMap<String, List<String>> listChildData = new HashMap<String, List<String>>();
    private String fireKey;
    private String firebaseURL = "https://beartracks.firebaseio.com/roles";
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
        status = (TextView) findViewById(R.id.status);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableItems);

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, headers,listChildData);
        expandableListView.setAdapter(listAdapter);

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
                            setupCreator(holder);
                            break;
                        case "owner":
                            setupOwner(holder);
                            break;
                        case "mentor":
                            mentor.setText(holder);
                            break;
                        case "status":
                            status.setText(holder);
                            break;
                        case "accountabilities":
                            setupAccountabilities(detail.getKey());
                            break;
                        case "attachments":
                            setupAttachments(detail.getKey());
                            break;
                        case "authorities":
                            setupAuthorities(detail.getKey());
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

    private void setupAccountabilities(String key){
        Firebase accountabilityRef = new Firebase(firebaseURL).child(fireKey).child(key);
        headers.add("Accountabilities");

        accountabilityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> childNames = new ArrayList<String>();
                for(DataSnapshot accountability: dataSnapshot.getChildren()){
                    Accountability roleAccnt = accountability.getValue(Accountability.class);
                    childNames.add(roleAccnt.getDescription());
                }
                listChildData.put("Accountabilities", childNames);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setupAttachments(String key){
        Firebase attachmentRef = new Firebase(firebaseURL).child(fireKey).child(key);
        headers.add("Attachments");

        attachmentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> childNames = new ArrayList<String>();
                for(DataSnapshot attachment : dataSnapshot.getChildren()){
                    Attachment atchmnt = attachment.getValue(Attachment.class);
                    childNames.add(atchmnt.getUrl());
                }
                listChildData.put("Attachments", childNames);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setupAuthorities(String key){
        Firebase authorityRef = new Firebase(firebaseURL).child(fireKey).child(key);
        headers.add("Authorities");

        authorityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> childNames = new ArrayList<String>();
                for(DataSnapshot athrty : dataSnapshot.getChildren()){
                    Authority cartman = athrty.getValue(Authority.class);
                    childNames.add(cartman.getDescription());
                }
                listChildData.put("Authorities", childNames);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

}
