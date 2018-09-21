package com.example.bhavyasikka.myfirebaseauth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<UserInformation> {

    private List<UserInformation> userList;

    private Activity context;

    public UserAdapter(Activity context, List<UserInformation> userList) {
        super(context,R.layout.activity_check,userList);
        this.context= context;
        this.userList=userList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_check, null, true);
        }



        TextView nameText=(TextView)listItemView.findViewById(R.id.nameTextView);
       // nameText.setText(currentDetail.getNameOfUser());

        TextView ageText=(TextView)listItemView.findViewById(R.id.ageTextView);
        //ageText.setText(currentDetail.getAgeOfUser());

        UserInformation currentDetail=userList.get(position);

       // nameText.setText(currentDetail.getNameOfUser());
       // ageText.setText(currentDetail.getAgeOfUser());

        return listItemView;
    }
}