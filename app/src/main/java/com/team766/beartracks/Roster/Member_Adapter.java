package com.team766.beartracks.Roster;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.team766.beartracks.R;

import java.util.ArrayList;

/**
 * Created by tommypacker on 8/5/15.
 */
public class Member_Adapter extends ArrayAdapter<Member> {

    private Uri testUri;

    private static class ViewHolder{
        TextView name;
        ImageView pic;
    }


    public Member_Adapter(Context context, ArrayList<Member> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Member member = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.roster_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.pic = (ImageView) convertView.findViewById(R.id.profilePicture);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(member.getName());
        String picURL = member.getPhoto();
        Picasso.with(this.getContext()).load(picURL).resize(200,200).centerCrop().into(viewHolder.pic);

        return convertView;
    }

}
