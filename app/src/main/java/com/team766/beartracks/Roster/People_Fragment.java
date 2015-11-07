package com.team766.beartracks.Roster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.R;

import java.util.ArrayList;

/**
 * Created by tommypacker on 7/28/15.
 */
public class People_Fragment extends Fragment {

    private ArrayList<Person> roster = new ArrayList<Person>();
    private Person teamMember;
    Person_Adapter adapter;
    private String firebaseURL = "https://beartracks.firebaseio.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.roster, container, false);

        adapter = new Person_Adapter(getActivity(), roster);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        setupRoster();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person item = (Person) adapter.getItem(position);
                String key = item.getKey();
                Bundle b = new Bundle();
                b.putString("FirebaseKey", key);
                Intent intent = new Intent(getActivity(), Person_Details.class);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        return view;
    }


    private void setupRoster(){
        Firebase peopleRef = new Firebase(firebaseURL).child("people");

        peopleRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot people : dataSnapshot.getChildren()) {
                    teamMember = people.getValue(Person.class);
                    teamMember.setKey(people.getKey());
                    roster.add(teamMember);
                    //makeToast(people.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    private void makeToast(String name){
        Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();
    }


}
