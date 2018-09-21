package com.example.bhavyasikka.myfirebaseauth;

public class UserInformation {

    public String courseCode;

    public String semester;

    public String userId;

    public String examTypeText;

    public String sectionText;

    public String subjectName;

    public UserInformation() {

    }

    public UserInformation(String examType,String section) {

        this.examTypeText=examType;
        this.sectionText=section;

    }
    public UserInformation(String coursecode,String sem,String subject){
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
    public String getUserId(){
        return userId;
    }
    public String getSubjectName() {
        return subjectName;
    }

    public String getExamTypeText() {
        return examTypeText;
    }

    public String getSectionText() {
        return sectionText;
    }
}
