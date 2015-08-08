package com.team766.beartracks.Role;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.team766.beartracks.R;
import com.team766.beartracks.Role.Role;

import java.util.ArrayList;

/**
 * Created by tommypacker on 8/6/15.
 */
public class Role_Adapter extends ArrayAdapter<Role> {

    private static class ViewHolder{
        TextView name;
    }

    public Role_Adapter(Context context, ArrayList<Role> roles) {
        super(context, 0, roles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Role role = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.role_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.role_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(role.getName());

        return convertView;

    }

}
