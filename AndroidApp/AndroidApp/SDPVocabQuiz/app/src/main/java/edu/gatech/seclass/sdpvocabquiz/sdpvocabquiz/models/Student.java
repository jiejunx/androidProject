package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.Session;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizScoresDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.StudentDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.WordDao;

@Entity
public class Student {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private String userName;
    private String major;
    private String seniorityLevel;
    private String email;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSeniorityLevel() {
        return seniorityLevel;
    }

    public void setSeniorityLevel(String seniorityLevel) {
        this.seniorityLevel = seniorityLevel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @NonNull
    public Integer getId() {
        return id;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public boolean isAlreadyRegistered() {
        AppDatabase db = Session.getInstance().getAppDatabase();
        StudentDao studentDao = db.studentDao();
        Student existingStudent = studentDao.findStudentByUsername(userName);
        return existingStudent != null;
    }

    public Student register() {
        AppDatabase db = Session.getInstance().getAppDatabase();
        StudentDao studentDao = db.studentDao();
        studentDao.insert(this);
        final Student newStudent = studentDao.findStudentByUsername(userName);

        return newStudent;
    }

    public void login() {
        AppDatabase db = Session.getInstance().getAppDatabase();
        StudentDao studentDao = db.studentDao();
        Student existingStudent = studentDao.findStudentByUsername(userName);
        Session session = Session.getInstance();
        session.setStudent(existingStudent);
    }

    public void removeQuiz(Quiz quiz){
        quiz.delete();
    }


    public List<Quiz> viewQuizzesAvailableForPractice(){
        AppDatabase db = Session.getInstance().getAppDatabase();
        QuizDao quizDao = db.quizDao();
        return quizDao.getQuizzesNotCreatedByStudent(id);
    }

}
