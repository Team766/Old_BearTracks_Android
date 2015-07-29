package com.team766.beartracks.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team766.beartracks.R;

/**
 * Created by tommypacker on 7/28/15.
 */
public class Project_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.project_fragment_layout, container, false);
        return view;
    }
}
