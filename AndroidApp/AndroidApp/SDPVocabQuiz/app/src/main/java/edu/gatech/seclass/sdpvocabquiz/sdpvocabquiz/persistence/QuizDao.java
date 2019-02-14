package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;

@Dao
public interface QuizDao {
    @Query("SELECT * FROM quiz WHERE id = :id LIMIT 1")
    Quiz findQuizById(Integer id);

    @Query("SELECT * FROM quiz WHERE name = :name LIMIT 1")
    Quiz findQuizByName(String name);

    @Query("SELECT * FROM quiz WHERE name = :name AND studentId = :studentId LIMIT 1")
    Quiz findQuizByNameAndStudentId(String name, Integer studentId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Quiz quiz);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Quiz... quizzes);

    @Query("DELETE FROM quiz where id = :id")
    void deleteQuiz(Integer id);

    @Query("SELECT * FROM quiz where studentId = :studentId")
    List<Quiz> getQuizzesCreatedByStudent(Integer studentId);

    @Query("SELECT * FROM quiz where studentId <> :studentId")
    List<Quiz> getQuizzesNotCreatedByStudent(Integer studentId);

    @Query("SELECT * FROM quiz")
    List<Quiz> getAllQuizzes();

}