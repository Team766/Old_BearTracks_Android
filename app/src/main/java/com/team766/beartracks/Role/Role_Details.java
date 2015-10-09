package com.team766.beartracks.Role;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private List<String> headers = new ArrayList<String>();
    private List<Attachment> attachments = new ArrayList<Attachment>();
    private ExpandableListAdapter listAdapter;
    private HashMap<String, List<String>> listChildData = new HashMap<String, List<String>>();
    private String fireKey;
    private String firebaseURL = "https://beartracks.firebaseio.com/roles";
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.role_details);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Role Info");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();
        fireKey = extras.getString("FirebaseKey");

        name = (TextView) findViewById(R.id.name);
        creator = (TextView) findViewById(R.id.creator);
        owner = (TextView) findViewById(R.id.owner);
        mentor = (TextView) findViewById(R.id.mentor);
        status = (TextView) findViewById(R.id.status);
        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableItems);

        listAdapter = new ExpandableListAdapter(this, headers,listChildData);
        expandableListView.setAdapter(listAdapter);

        setupTexts();

        expandableListView.setScrollContainer(false);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (headers.get(groupPosition).equals("Attachments")) {
                    String attachmentURL = attachments.get(childPosition).getUrl();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(attachmentURL));
                    startActivity(intent);
                }
                return false;
            }
        });

    }

    private void makeToast(String name){
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    private void setupTexts(){
        Firebase roleDetsRef = new Firebase(firebaseURL).child(fireKey);
        roleDetsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot detail : dataSnapshot.getChildren()) {
                    String holder = detail.getValue().toString();
                    switch (detail.getKey()) {
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
                            setupMentor(holder);
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

    private void setupMentor(String key){
        Firebase ownerRef = new Firebase("https://beartracks.firebaseio.com/people").child(key);

        ownerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot people: dataSnapshot.getChildren()){
                    if(people.getKey().equals("name")){
                        mentor.setText(people.getValue().toString());
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
                listAdapter.notifyDataSetChanged();
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
                    atchmnt.setURL(atchmnt.getUrl());
                    attachments.add(atchmnt);
                    childNames.add(atchmnt.getName());
                }
                listChildData.put("Attachments", childNames);
                listAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                NavUtils.navigateUpTo(this, intent);
        }
        return super.onOptionsItemSelected(item);
    }

}

