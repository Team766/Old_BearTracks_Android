package com.team766.beartracks.Role;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.team766.beartracks.R;

import java.util.ArrayList;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Role_List extends Fragment {

    private ArrayList<Role> roleList = new ArrayList<Role>();
    private Role_Adapter adapter;
    private Role teamRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.role_fragment_layout, container, false);

        adapter = new Role_Adapter(getActivity(), roleList);
        ListView listView = (ListView) view.findViewById(R.id.role_list);
        listView.setAdapter(adapter);

        setupRoleList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Role role = (Role) adapter.getItem(position);
                String key = role.getKey();
                Bundle b = new Bundle();
                b.putString("FirebaseKey", key);
                Intent intent = new Intent(getActivity(), Role_Details.class);
                intent.putExtras(b);
                startActivity(intent);

            }
        });

        return view;
    }

    private void setupRoleList(){
        Firebase roleRef = new Firebase("https://beartracks.firebaseio.com/roles/");
        Query containerSort = roleRef.orderByChild("container");

        containerSort.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                teamRole = dataSnapshot.getValue(Role.class);
                teamRole.setKey(dataSnapshot.getKey());
                roleList.add(teamRole);
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


}
