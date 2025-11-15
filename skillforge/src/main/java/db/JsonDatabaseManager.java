/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author Hajer1
 */

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import model.*;
import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class JsonDatabaseManager {

    private final Path usersPath = Paths.get("users.json");
    private final Path coursesPath = Paths.get("courses.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<User> readUsers() {
        try {
            if (!Files.exists(usersPath)) return new ArrayList<>();
            Reader r = Files.newBufferedReader(usersPath);
            Type type = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(r, type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void writeUsers(List<User> users) {
        try (Writer w = Files.newBufferedWriter(usersPath)) {
            gson.toJson(users, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Course> readCourses() {
        try {
            if (!Files.exists(coursesPath)) return new ArrayList<>();
            Reader r = Files.newBufferedReader(coursesPath);
            Type type = new TypeToken<List<Course>>() {}.getType();
            return gson.fromJson(r, type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void writeCourses(List<Course> courses) {
        try (Writer w = Files.newBufferedWriter(coursesPath)) {
            gson.toJson(courses, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUserById(String id) {
        return readUsers().stream().filter(u -> u.getUserId().equals(id)).findFirst().orElse(null);
    }

    public Course findCourseById(String id) {
        return readCourses().stream().filter(c -> c.getCourseId().equals(id)).findFirst().orElse(null);
    }

    public boolean isUserIdUnique(String id) {
        return findUserById(id) == null;
    }

    public boolean isCourseIdUnique(String id) {
        return findCourseById(id) == null;
    }
}
