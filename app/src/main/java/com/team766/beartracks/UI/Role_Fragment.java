package com.team766.beartracks.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.team766.beartracks.R;
import com.team766.beartracks.Role.Role;
import com.team766.beartracks.Role.Role_Adapter;

import java.util.ArrayList;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Role_Fragment extends Fragment {

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


        return view;
    }

    private void setupRoleList(){
        Firebase roleRef = new Firebase("https://beartracks.firebaseio.com/roles/");
        roleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot roles: dataSnapshot.getChildren()){
                    teamRole = roles.getValue(Role.class);
                    roleList.add(teamRole);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }


}
