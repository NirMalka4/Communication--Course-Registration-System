package bgu.spl.net.impl;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

public class Course {
    private final Integer courseNum;
    private final String courseName;
    private final int courseIndex;
    private List<Integer> preliminaryCoursesList;
    private final int numOfMaxStudents;
    private final ConcurrentSkipListSet<String> registeredStudents;
    private int availableSeats;

    public Course(int courseIndex, int courseNum,String courseName,List<Integer> KdamCoursesList,int numOfMaxStudents){
        this.courseIndex = courseIndex;
        this.courseNum=courseNum;
        this.courseName=courseName;
        this.preliminaryCoursesList = KdamCoursesList;
        this.numOfMaxStudents=numOfMaxStudents;
        registeredStudents= new ConcurrentSkipListSet<>();
        availableSeats =numOfMaxStudents;
    }

    public void setKadm(List<Integer> kdam) {
        preliminaryCoursesList = kdam;
    }

    public String getCourseName(){
        return courseName;
    }

    public  Integer getAvailableSeats() {
        return availableSeats;
    }

    public  String getRegisteredStudents(){
        return registeredStudents.toString().replace(" ", "");
    }

    public  Boolean assignMember(String student){
        if (hasAvailableSeats() && !studentInCourse(student)){
            registeredStudents.add(student);
            availableSeats--;
            return true;
        }
        else return false;
    }

    private  Boolean studentInCourse(String student){
        return registeredStudents.contains(student);
    }

    public  Boolean removeStudent(String student){
        if (registeredStudents.contains(student)){
            registeredStudents.remove(student);
            availableSeats++;
            return true;
        }
        else return false;
    }

    public List<Integer> getPreliminaryCoursesList(){
        return preliminaryCoursesList;
    }

    public Integer getCourseNum(){
        return courseNum;
    }

    public  Boolean isReg(String username){
        return registeredStudents.contains(username);
    }

    private Boolean hasAvailableSeats(){
        return availableSeats>0;
    }

    public Integer getCourseIndex(){
        return courseIndex;
    }

    public Integer getMaxSeats(){
        return numOfMaxStudents;
    }
}