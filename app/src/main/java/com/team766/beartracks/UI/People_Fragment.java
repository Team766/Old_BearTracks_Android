package com.team766.beartracks.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.Person;
import com.team766.beartracks.R;
import com.team766.beartracks.UserAdapter;

import java.util.ArrayList;

/**
 * Created by tommypacker on 7/28/15.
 */
public class People_Fragment extends Fragment {

    private ArrayList<Person> roster = new ArrayList<Person>();
    private Person teamMember;
    UserAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.roster, container, false);

        adapter = new UserAdapter(getActivity(), roster);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);

        setupRoster();

        return view;
    }

    private void setupRoster(){
        Firebase peopleRef = new Firebase("https://beartracks.firebaseio.com/people/");

        peopleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot people: dataSnapshot.getChildren()){
                    teamMember = people.getValue(Person.class);
                    roster.add(teamMember);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }
}
