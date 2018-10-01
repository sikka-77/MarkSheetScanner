package com.example.bhavyasikka.myfirebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NewActivity {

    public String mExamTypeName;

    public String mExamTypeId;

    public String msectionName;

    public NewActivity() {

    }

    public NewActivity(String examId,String examType,String section) {
        this.mExamTypeId=examId;
        this.mExamTypeName=examType;
        this.msectionName=section;
    }

    public String getmExamTypeName() {
        return mExamTypeName;
    }

    public String getmExamTypeId() {
        return mExamTypeId;
    }

    public String getMsectionName() {
        return msectionName;
    }
}
