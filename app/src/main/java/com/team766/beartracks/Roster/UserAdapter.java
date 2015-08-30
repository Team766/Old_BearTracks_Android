package com.team766.beartracks.Roster;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;
import com.team766.beartracks.R;
import com.team766.beartracks.Roster.Person;

import java.util.ArrayList;

/**
 * Created by tommypacker on 8/5/15.
 */
public class UserAdapter extends ArrayAdapter<Person> {

    private Uri testUri;

    private static class ViewHolder{
        TextView name;
        //ImageView pic;
        SimpleDraweeView pic;
    }


    public UserAdapter(Context context, ArrayList<Person> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Person member = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.roster_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            //viewHolder.pic = (ImageView) convertView.findViewById(R.id.profilePicture);
            viewHolder.pic = (SimpleDraweeView) convertView.findViewById(R.id.my_image_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(member.getName());
        String picURL = member.getPhoto();
        if (picURL == null){
            Toast.makeText(getContext(), "Test", Toast.LENGTH_SHORT).show();
        }else{
            testUri = Uri.parse(picURL);
        }

        viewHolder.pic.setImageURI(testUri);




        return convertView;
    }

}
