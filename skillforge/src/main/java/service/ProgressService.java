package service;

import java.util.List;

import db.JsonDatabaseManager;
import model.Student;
import model.User;

public class ProgressService {

    private final JsonDatabaseManager db;

    public ProgressService(JsonDatabaseManager db) {
        this.db = db;
    }

    // Initialize student progress for a course
    public void initializeProgress(String userId, String courseId) {
        List<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;

                // If not enrolled, enroll
                if (!s.getEnrolledCourses().contains(courseId)) {
                    s.getEnrolledCourses().add(courseId);
                }

                // Set progress to 0 at start
                s.setProgress(0);

                db.writeUsers(users);
                return;
            }
        }
    }

    // Update progress as a percentage (0 to 100)
    public void updateProgress(String userId, int newProgress) {
        List<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;

                s.setProgress(Math.min(100, Math.max(0, newProgress)));

                db.writeUsers(users);
                return;
            }
        }
    }

    // Get the student's stored progress
    public int getProgress(String userId) {
        List<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;
                return s.getProgress();
            }
        }
        return 0;
    }
}
