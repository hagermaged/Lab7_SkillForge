/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Hajer1
 */
import db.*;
import model.*;
import java.util.*;

public class CourseService {

    //attributes
    private JsonDatabaseManager db;
    private List<Course> courses = new ArrayList<>();

    //constructor
    public CourseService() {
        this.db = new JsonDatabaseManager();
        this.courses = db.readCourses();
    }

    //course methods
    //method1 : create course
    public Course createCourse(String courseTitle, String courseDescription, String instructorId) {
        IdGenerator id = new IdGenerator();
        String courseId = id.generateCourseId(courses);
        Course course = new Course(courseId, courseTitle, courseDescription, instructorId);
        courses.add(course);
        db.writeCourses(courses);
        return course;
    }

    //method2 : edit course (given id -> edit new title, new description)
    public boolean editCourse(String courseId, String newTitle, String newDescription) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            course.setCourseTitle(newTitle);
            course.setCourseDescription(newDescription);
            db.writeCourses(courses);
            return true;
        }
        return false;
    }

    //method3: delete course by id
    public boolean deleteCourse(String courseId) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            courses.remove(course);
            db.writeCourses(courses);
            return true;
        }
        return false;
    }

    //lesson methods
    //method1 : addLesson
    public boolean addLesson(String courseId, String lessonId, String title, String content) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            Lesson newLesson = new Lesson(lessonId, title, content);
            course.addLesson(newLesson);
            db.writeCourses(courses);
            return true;
        }
        return false;
    }

    public boolean editLesson(String courseId, String lessonId, String newTitle, String newContent) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            Lesson lesson = findLessonInCourse(course, lessonId);
            if (lesson != null) {
                lesson.setLessonTitle(newTitle);
                lesson.setContent(newContent);
                db.writeCourses(courses);
                return true;
            }
        }
        return false;
    }
    
    //get lessons for a course
        public List<Lesson> getLessonsForCourse(String courseId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            return course.getLessons();
        }
        return new ArrayList<>();
    }

    public boolean deleteLesson(String courseId, String lessonId) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            Lesson lesson = findLessonInCourse(course, lessonId);
            if (lesson != null) {
                course.getLessons().remove(lesson);
                db.writeCourses(courses);
                return true;
            }
        }
        return false;
    }

    //additional method
    private Lesson findLessonInCourse(Course course, String lessonId) {
        return course.getLessons().stream()
                .filter(l -> l.getLessonId().equals(lessonId))
                .findFirst()
                .orElse(null);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course getCourseById(String courseId) {
        return db.findCourseById(courseId);
    }

    //enroll student in course
    public boolean enrollStudent(String courseId, Student student) {
        Course course = db.findCourseById(courseId);
        if (course != null) {
            course.addStudent(student);
            db.writeCourses(courses);
            return true;
        }
        return false;
    }

    //get resources comma seperated
    public String getResourcesAsCommaSeparated(Lesson lesson) {
        var res = lesson.getResources();
        return (res == null || res.isEmpty()) ? "No resources" : String.join(", ", res);
    }

}
