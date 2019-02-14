package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;

public class Session {
    private Student student = null;
    private AppDatabase appDatabase = null;
    private static Session INSTANCE;

    private Session() {
        // empty constructor
    }

    // Instance of the session that lives as a global object in the application
    private static final Session instance = new Session();

    public void setStudent(Student student) {
        instance.student = student;
    }

    public Student getStudent() {
        return instance.student;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    public void setAppDatabase(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public static Session getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Session();
        }

        return INSTANCE;
    }
}
