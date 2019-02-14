package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;

@Dao
public interface StudentDao {
    @Query("SELECT * FROM student WHERE username = :username LIMIT 1")
    Student findStudentByUsername(String username);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Student student);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Student... students);

    @Query("SELECT * FROM student where id in (:studentIds)")
    List<Student> getStudentsWithIds(List<Integer> studentIds);
}