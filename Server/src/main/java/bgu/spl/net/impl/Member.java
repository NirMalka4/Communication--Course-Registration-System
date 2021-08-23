package bgu.spl.net.impl;

import java.util.*;

public class Member {
    private final String name;
    private final String password;
    private final SortedSet<Integer> accomplishedCourses;
    private boolean isActive;
    private boolean isAdmin;

    public Member(String name,String password, Boolean isAdmin){
        this.name = name;
        this.password = password;
        this.isActive = false;
        this.isAdmin = isAdmin;
        if (isAdmin){
            this.accomplishedCourses = null;
        }
        else this.accomplishedCourses = new TreeSet<Integer>();
    }

    public SortedSet<Integer> getAccomplishedCourses(){
        return accomplishedCourses;
    }

    public String getName(){
        return name;
    }

    public Boolean comperPassword(String password){
        return (this.password.equals(password));
    }

    public void registerCourse(Integer course){
        accomplishedCourses.add(course);
    }

    public Boolean isAccomplishedCourse(Integer course){
        return accomplishedCourses.contains(course);
    }

    public synchronized void setLoggedIn(){
        isActive = true;
    }

    public synchronized void setLoggedOut(){
        isActive = false;
    }

    public boolean isAdmin(){
        return isAdmin;
    }
    
    public synchronized boolean isActive(){
        return isActive;
    }

    public Boolean hasKdam(Integer kdam){
        return accomplishedCourses.contains(kdam);
    }

}
