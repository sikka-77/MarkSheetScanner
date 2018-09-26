package com.example.bhavyasikka.myfirebaseauth;

public class UserInformation {

    /*
            java class for storing course code related data in firebase
            this class will also act as a custom class for setting up list view
     */

    public String courseCode;

    public String semester;

    public String courseCodeId;



    public String subjectName;

    public UserInformation() {

    }


    public UserInformation(String courseCodeId,String sem,String coursecode,String subject){
        this.courseCodeId=courseCodeId;
        this.courseCode=coursecode;
        this.semester=sem;
        this.subjectName=subject;

    }

    public String getCourseCode() {
        return courseCode;
    }
    public String getSemester() {
        return semester;
    }
    public String getCourseCodeId(){ return courseCodeId; }
    public String getSubjectName() {
        return subjectName;
    }


}
