package com.example.bhavyasikka.myfirebaseauth;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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



        TextView courseCodeText=(TextView)listItemView.findViewById(R.id.coursecode);
        TextView semester=(TextView)listItemView.findViewById(R.id.Semester);
        TextView subject=(TextView)listItemView.findViewById(R.id.subject);


        UserInformation currentDetail = userList.get(position);



        semester.setText(currentDetail.getSemester());
        courseCodeText.setText(currentDetail.getCourseCode());
        subject.setText(currentDetail.getSubjectName());

        return listItemView;
    }
}