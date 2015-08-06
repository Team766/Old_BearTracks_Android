package com.team766.beartracks;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

/**
 * Created by tommypacker on 8/5/15.
 */
public class Person_Details extends AppCompatActivity {

    private String firebaseURL = "https://beartracks.firebaseio.com/people/";
    private String fireKey;
    TextView name;
    TextView email;
    TextView phone;
    ImageView pic;
    android.support.v7.widget.Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_details);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.personDetails_toolbar);
        setSupportActionBar(toolbar);


        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        pic = (ImageView) findViewById(R.id.userPicture);

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
                    switch (detail.getKey()) {
                        case "name":
                            name.setText(holder);
                            toolbar.setTitle(holder);
                            break;
                        case "email":
                            email.setText(holder);
                            break;
                        case "phone":
                            phone.setText(holder);
                            break;
                        case "photo":
                            Picasso.with(getApplicationContext()).load(holder).fit().centerCrop().into(pic);
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
