 
/*
package lab7;
import java.util.ArrayList;
import java.util.List;

 

public class studentservice {
    public List<Course>browseallcourses()
    {
        return jsondatabasemanager.loadcourses();
    }
    public List<Course>getenrolledcourses(Student s)
    {
        List<Course>all=browseallcourses();
        List<Course>result=new ArrayList<>();
        
        for(Course c:all)
        {
            if(s.getEnrolledCourses().contains(c.getCourseId()));
            result.add(c);
        }
        
        return result;
    }
    public void enroll(Student s, Course c)
    {
        if(!(s.getEnrolledCourses().contains(c.getCourseId())))
            s.getEnrolledCourses().add(c.getCourseId());
        jsondatabasemanager.saveusers();
        jsondatabasemanager.savecourses();
    }
    public List<Lesson>getlessons(Course c)
    {
        return c.getLessons();
    }
    public void completelesson(Student s, Course c, Lesson l)
    {
        s.marklessoncompleted(c.getCourseId(),l.getLessonId());
        jsondatabasemanger.updateprogress(s.getuserid(),c.getCourseId(),l.getLessonId());
        jsondatabasemanager.saveusers();
    }
            
    
}
*/