package com.team766.beartracks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tommypacker on 8/5/15.
 */
public class UserAdapter extends ArrayAdapter<Person> {

    public UserAdapter(Context context, ArrayList<Person> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person member = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.roster_item, parent, false);
        }

        ImageView profPic = (ImageView) convertView.findViewById(R.id.profilePicture);
        String picURL = member.getPhoto();
        Picasso.with(this.getContext()).load(picURL).resize(150,150).centerCrop().into(profPic);


        TextView memberName = (TextView) convertView.findViewById(R.id.name);

        memberName.setText(member.getName());

        return convertView;
    }


}
