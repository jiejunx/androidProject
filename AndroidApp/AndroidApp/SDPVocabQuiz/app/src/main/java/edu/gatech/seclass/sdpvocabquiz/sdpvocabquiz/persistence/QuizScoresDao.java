package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizScore;

@Dao
public interface QuizScoresDao {

    @Query("SELECT * FROM quizscore where  quizId = :quizId")
    List<QuizScore> findQuizScoreByQuizId(Integer quizId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(QuizScore quizScore);

    @Query("SELECT * from quizscore")
    List<QuizScore> getAllQuizScores();

    @Query("DELETE from quizscore where quizId = :quizId")
    void deleteQuizScoresWithQuizId(Integer quizId);
}