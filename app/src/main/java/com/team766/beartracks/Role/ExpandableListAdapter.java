package com.team766.beartracks.Role;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.team766.beartracks.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by tommypacker on 8/7/15.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private List<String> headlines;
    private HashMap<String, List<String>> childTitles;
    private Context context;


    public ExpandableListAdapter(Context context, List<String> headers, HashMap<String, List<String>> childData){
        this.context = context;
        this.headlines = headers;
        this.childTitles = childData;
    }

    @Override
    public int getGroupCount() {
        return headlines.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childTitles.get(headlines.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return headlines.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childTitles.get(headlines.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String) this.getGroup(groupPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.role_list_header, parent, false);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.listHeader);
        textView.setText(title);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childTitle = (String) this.getChild(groupPosition, childPosition);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.role_item, parent, false);
        }

        TextView childTextView = (TextView) convertView.findViewById(R.id.role_name);
        childTextView.setText(childTitle);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
