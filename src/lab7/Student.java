/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author orignal store
 */
public class Student extends User {
    private List<String>enrolledCourses;
    private int progress;
    public Student()
    {
        super();
        this.enrolledCourses=new ArrayList<>();
        this.progress=0;
    }
    public Student(String username,String email,String passwordHash,String userId)
    {
        super(username,email,"student",userId,passwordHash);
        this.enrolledCourses=new ArrayList<>();
        this.progress=0;
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    @Override
    public String getUserType()
    {
        return "student";
    }
    //Helper methods
    public void enrollInCourse(String courseId)
    {
        if(!enrolledCourses.contains(courseId))
        {  //new course added to the list
            enrolledCourses.add(courseId);
            
        }
        //else{
            //add a message to be linked to the frontend that the student is already enrolled to the course
            //return;
        //}  
    }
    public void unenrollFromCourse(String courseId)
    {
        enrolledCourses.remove(courseId);
    }
    
}
