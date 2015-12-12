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

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.team766.beartracks.R;

import java.util.ArrayList;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Roster_List extends Fragment {

    private ArrayList<Member> roster = new ArrayList<Member>();
    private Member teamMember;
    Member_Adapter adapter;
    private String firebaseURL = "https://beartracks.firebaseio.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.roster, container, false);

        adapter = new Member_Adapter(getActivity(), roster);
        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);
        setupRoster();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member item = (Member) adapter.getItem(position);
                String key = item.getKey();
                Bundle b = new Bundle();
                b.putString("FirebaseKey", key);
                Intent intent = new Intent(getActivity(), Member_Info.class);
                intent.putExtras(b);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        return view;
    }


    private void setupRoster(){
        Firebase peopleRef = new Firebase(firebaseURL).child("people");

        peopleRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                teamMember = dataSnapshot.getValue(Member.class);
                teamMember.setKey(dataSnapshot.getKey());
                roster.add(teamMember);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
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
