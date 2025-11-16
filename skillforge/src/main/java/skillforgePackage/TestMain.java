/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package skillforgePackage;

import model.*;
import service.*;
import db.*;
import java.util.*;

public class TestMain {
    private static AuthService authService;
    private static ProgressService progressService;
    private static CourseService courseService;
    private static JsonDatabaseManager dbManager;

    public static void main(String[] args) {
        System.out.println("=== SKILL FORGE COMPREHENSIVE TEST ===\n");
        
        // Initialize services
        initializeServices();
        
        // Test different scenarios
        testScenario1("Normal Operation - All Data Consistent");
        testScenario2("Student Not in Course Lists");
        testScenario3("Empty Database");
        testScenario4("Course with No Lessons");
        
        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }
    
    private static void initializeServices() {
        authService = new AuthService();
        progressService = new ProgressService();
        courseService = new CourseService();
        dbManager = new JsonDatabaseManager();
    }
    
    public static void testScenario1(String scenarioName) {
        System.out.println("🎯 " + scenarioName);
        System.out.println("=" .repeat(50));
        
        // Test Auth Service
        System.out.println("\n🔐 AUTH SERVICE TESTS");
        System.out.println("-".repeat(30));
        
        // Test 1: Login with existing user
        System.out.println("1. Testing login with john_doe...");
        User user = authService.login("john@example.com", "password");
        if (user != null) {
            System.out.println("   ✅ Login successful: " + user.getUsername() + " (" + user.getRole() + ")");
        } else {
            System.out.println("   ❌ Login failed");
        }
        
        // Test 2: Login with wrong password
        System.out.println("2. Testing login with wrong password...");
        User wrongPass = authService.login("john@example.com", "wrongpass");
        if (wrongPass == null) {
            System.out.println("   ✅ Correctly rejected wrong password");
        } else {
            System.out.println("   ❌ Should have rejected wrong password");
        }
        
        // Test 3: Sign up new user
        System.out.println("3. Testing new user signup...");
        boolean signupSuccess = authService.signUp("jana", "jana@example.com", "password1234", "student");
        if (signupSuccess) {
            System.out.println("   ✅ New user signup successful");
        } else {
            System.out.println("   ❌ New user signup failed");
        }
        
        // Test Progress Service
        System.out.println("\n📊 PROGRESS SERVICE TESTS");
        System.out.println("-".repeat(30));
        
        // Test 4: Get student information
        System.out.println("4. Testing get student U1...");
        Student student = progressService.getStudent("U1");
        if (student != null) {
            System.out.println("   ✅ Student found: " + student.getUsername());
            System.out.println("      Progress: " + student.getProgress() + "%");
            System.out.println("      Enrolled in: " + student.getEnrolledCourses().size() + " courses");
        } else {
            System.out.println("   ❌ Student not found");
        }
        
        // Test 5: Get enrolled courses
        System.out.println("5. Testing get enrolled courses for U1...");
        List<Course> enrolledCourses = progressService.getEnrolledCourses("U1");
        System.out.println("   ✅ Enrolled in " + enrolledCourses.size() + " courses:");
        for (Course course : enrolledCourses) {
            System.out.println("      - " + course.getCourseTitle() + " (" + course.getCourseId() + ")");
        }
        
        // Test 6: Course progress
        System.out.println("6. Testing course progress...");
        for (Course course : enrolledCourses) {
            int progress = progressService.getProgress("U1", course.getCourseId());
            System.out.println("   ✅ " + course.getCourseTitle() + " progress: " + progress + "%");
        }
        
        // Test 7: Complete lessons
        System.out.println("7. Testing lesson completion...");
        progressService.completeLesson("U1", "C1", "L2");
        int updatedProgress = progressService.getProgress("U1", "C1");
        System.out.println("   ✅ Updated C1 progress: " + updatedProgress + "%");
        
        // Test Course Service
        System.out.println("\n📚 COURSE SERVICE TESTS");
        System.out.println("-".repeat(30));
        
        // Test 8: Get all courses
        System.out.println("8. Testing get all courses...");
        List<Course> allCourses = courseService.getAllCourses();
        System.out.println("   ✅ Total courses: " + allCourses.size());
        for (Course course : allCourses) {
            System.out.println("      - " + course.getCourseTitle() + " (Students: " + course.getStudents().size() + 
                             ", Lessons: " + course.getLessons().size() + ")");
        }
        
        // Test 9: Find course by ID
        System.out.println("9. Testing find course C1...");
        Course foundCourse = courseService.getCourseById("C1");
        if (foundCourse != null) {
            System.out.println("   ✅ Course found: " + foundCourse.getCourseTitle());
        } else {
            System.out.println("   ❌ Course not found");
        }
        
        // Test Database Manager
        System.out.println("\n💾 DATABASE MANAGER TESTS");
        System.out.println("-".repeat(30));
        
        // Test 10: Read users
        System.out.println("10. Testing read users...");
        List<User> users = dbManager.readUsers();
        System.out.println("   ✅ Loaded " + users.size() + " users");
        
        // Test 11: Read courses
        System.out.println("11. Testing read courses...");
        List<Course> courses = dbManager.readCourses();
        System.out.println("   ✅ Loaded " + courses.size() + " courses");
        
        System.out.println();
    }
    
    public static void testScenario2(String scenarioName) {
        System.out.println("🎯 " + scenarioName);
        System.out.println("=" .repeat(50));
        
        // This scenario tests when student is enrolled in courses but not in course student lists
        System.out.println("\n📊 TESTING DATA INCONSISTENCY");
        System.out.println("-".repeat(30));
        
        // Test enrollment when data is inconsistent
        System.out.println("1. Testing enrollment with inconsistent data...");
        boolean enrolled = progressService.enrollInCourse("U1", "C3");
        if (enrolled) {
            System.out.println("   ✅ Successfully enrolled in C3");
        } else {
            System.out.println("   ❌ Enrollment failed");
        }
        
        // Test get enrolled courses after potential fix
        System.out.println("2. Testing enrolled courses after enrollment attempt...");
        List<Course> enrolledCourses = progressService.getEnrolledCourses("U1");
        System.out.println("   ✅ Now enrolled in " + enrolledCourses.size() + " courses");
        
        System.out.println();
    }
    
    public static void testScenario3(String scenarioName) {
        System.out.println("🎯 " + scenarioName);
        System.out.println("=" .repeat(50));
        
        // Test with non-existent user
        System.out.println("\n🔐 TESTING NON-EXISTENT USER");
        System.out.println("-".repeat(30));
        
        System.out.println("1. Testing login with non-existent user...");
        User user = authService.login("nonexistent@example.com", "password");
        if (user == null) {
            System.out.println("   ✅ Correctly rejected non-existent user");
        } else {
            System.out.println("   ❌ Should have rejected non-existent user");
        }
        
        System.out.println("2. Testing progress for non-existent student...");
        int progress = progressService.getOverallProgress("U999");
        System.out.println("   ✅ Progress for non-existent student: " + progress + "%");
        
        System.out.println();
    }
    
    public static void testScenario4(String scenarioName) {
        System.out.println("🎯 " + scenarioName);
        System.out.println("=" .repeat(50));
        
        // Test course operations
        System.out.println("\n📚 TESTING COURSE OPERATIONS");
        System.out.println("-".repeat(30));
        
        System.out.println("1. Testing create new course...");
        Course newCourse = courseService.createCourse("C4", "Test Course", "This is a test course", "U2");
        if (newCourse != null) {
            System.out.println("   ✅ Course created: " + newCourse.getCourseTitle());
        } else {
            System.out.println("   ❌ Course creation failed");
        }
        
        System.out.println("2. Testing add lesson to course...");
        boolean lessonAdded = courseService.addLesson("C4", "L7", "Test Lesson", "Test content");
        if (lessonAdded) {
            System.out.println("   ✅ Lesson added to course");
        } else {
            System.out.println("   ❌ Lesson addition failed");
        }
        
        System.out.println();
    }
}