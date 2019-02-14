package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Word;

@Dao
public interface WordDao {

    @Query("SELECT * FROM word WHERE quizId = :quizId")
    List<Word> findWordsByQuizId(Integer quizId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Word word);

    @Query("DELETE FROM word where quizId = :quizId")
    void deleteWordsWithQuizId(Integer quizId);
}