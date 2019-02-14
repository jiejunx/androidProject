package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizScore;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Word;

@Database(entities = {Student.class, Quiz.class, QuizScore.class, Word.class}, version = 7)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    private static final String DB_NAME = "app.db";

    public static AppDatabase getAppDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    public abstract WordDao wordDao();
    public abstract QuizDao quizDao();
    public abstract StudentDao studentDao();
    public abstract QuizScoresDao quizScoresDao();

}
