package com.example.bhavyasikka.myfirebaseauth;

public class MarksModel {

    public String mMarks;

    public String mRegNo;

    public MarksModel() {

    }

    public MarksModel(String marks,String regno){
        this.mMarks=marks;
        this.mRegNo=regno;
    }

    public String getmMarks() {
        return mMarks;
    }

    public String getmRegNo() {
        return mRegNo;
    }
}
