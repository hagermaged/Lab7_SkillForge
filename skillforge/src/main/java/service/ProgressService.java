package service;

import db.JsonDatabaseManager;
import model.*;
import java.util.*;

public class ProgressService {

    private final JsonDatabaseManager db;

    public ProgressService(JsonDatabaseManager db) {
        this.db = db;
    }

    public void initializeProgress(String userId, String courseId) {
        List<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;
                s.getProgress().putIfAbsent(courseId, new ArrayList<>());
                db.writeUsers(users);
                return;
            }
        }
    }

    public void completeLesson(String userId, String courseId, String lessonId) {
        List<User> users = db.readUsers();

        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;

                s.getProgress().putIfAbsent(courseId, new ArrayList<>());
                List<String> done = s.getProgress().get(courseId);

                if (!done.contains(lessonId)) done.add(lessonId);

                db.writeUsers(users);
                return;
            }
        }
    }

    public int getProgress(String userId, String courseId) {
        Course c = db.findCourseById(courseId);
        if (c == null) return 0;

        List<User> users = db.readUsers();
        for (User u : users) {
            if (u instanceof Student && u.getUserId().equals(userId)) {
                Student s = (Student) u;

                int total = c.getLessons().size();
                int done = s.getProgress().getOrDefault(courseId, new ArrayList<>()).size();

                return total == 0 ? 0 : (done * 100) / total;
            }
        }
        return 0;
    }
}
