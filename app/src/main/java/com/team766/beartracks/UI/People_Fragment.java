package com.team766.beartracks.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person item = (Person) adapter.getItem(position);
                makeToast(item.getKey());
            }
        });

        return view;
    }


    private void setupRoster(){
        Firebase peopleRef = new Firebase("https://beartracks.firebaseio.com/people/");

        peopleRef.addValueEventListener(new ValueEventListener() {
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
