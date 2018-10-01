package com.example.bhavyasikka.myfirebaseauth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapterExamActivity extends ArrayAdapter<NewActivity> {

    /*
            array adapter classs to set up a listview of exam types
     */

    private List<NewActivity> userList;

    private Activity context;

    public UserAdapterExamActivity(Activity context, List<NewActivity> userList) {
        super(context,R.layout.activity_check2,userList);
        this.context= context;
        this.userList=userList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_check2, null, true);
        }

        TextView mExamName = (TextView) listItemView.findViewById(R.id.examName);

        TextView mSectionName = (TextView) listItemView.findViewById(R.id.sectionNames);

        NewActivity currentExam = userList.get(position);

        mExamName.setText(currentExam.getmExamTypeName());

        mSectionName.setText(currentExam.getMsectionName());

        return listItemView;


    }
    }
