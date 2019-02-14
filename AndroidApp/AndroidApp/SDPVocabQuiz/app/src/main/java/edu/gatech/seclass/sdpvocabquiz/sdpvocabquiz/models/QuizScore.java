package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.Converters;

@Entity
public class QuizScore implements Comparable<QuizScore>{
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    private Integer studentId;
    private Integer quizId;
    private Integer score;

    @Ignore
    private Student student;

    @TypeConverters(Converters.class)
    private Date dateTime;


    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    @Override
    public int compareTo(QuizScore o) {
        int compare = dateTime.compareTo(o.getDateTime());
        if (compare == 0){
            //continue to compare in name for alphabetic order
            compare = student.getUserName().compareTo(o.getStudent().getUserName());
        }

        return compare;
    }
}
