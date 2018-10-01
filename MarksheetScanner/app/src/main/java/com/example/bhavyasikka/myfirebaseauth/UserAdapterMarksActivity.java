package com.example.bhavyasikka.myfirebaseauth;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapterMarksActivity extends ArrayAdapter<MarksModel> {

    private List<MarksModel> userList;

    private Activity context;

    public UserAdapterMarksActivity(Activity context, List<MarksModel> userList) {


        super(context, R.layout.marks_layout, userList);
        this.context = context;
        this.userList = userList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.marks_layout, null, true);
        }

        TextView marks = (TextView) listItemView.findViewById(R.id.marks_individual);

        TextView reg_no = (TextView) listItemView.findViewById(R.id.reg_number);

        MarksModel currentMarks = userList.get(position);

        marks.setText(currentMarks.getmMarks());

        reg_no.setText(currentMarks.getmRegNo());

        return listItemView;
    }
}
