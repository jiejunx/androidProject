package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.Session;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizScoresDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.WordDao;

@Entity(foreignKeys = @ForeignKey(entity = Student.class, parentColumns = "id", childColumns = "studentId"))
public class Quiz {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @NonNull
    private String name = null;
    private Integer studentId = null;
    private String description = null;
    private Integer numberOfWords = null;

    private String incorrectDefinitionsStr = null;

    @Ignore
    private List<String> incorrectDefinitions = null;
    @Ignore
    private List<Word> wordList = null;
    @Ignore
    private List<QuizScore> quizScores = null;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIncorrectDefinitions() {
        if ((incorrectDefinitions == null) || (incorrectDefinitions.isEmpty())) {
            incorrectDefinitions = Arrays.asList(incorrectDefinitionsStr.split(","));
        }

        return incorrectDefinitions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIncorrectDefinitions(List<String> incorrectDefinitions) {

        this.incorrectDefinitions = incorrectDefinitions;
        this.incorrectDefinitionsStr = StringUtils.join(incorrectDefinitions, ",");
    }

    public Integer getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(Integer numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    public List<Word> getWordList() {
        return wordList;
    }

    public void setWordList(List<Word> wordList) {
        this.wordList = wordList;
    }

    public String toString() {
        return name;
    }

    public String getIncorrectDefinitionsStr() {
        return incorrectDefinitionsStr;
    }

    public void setIncorrectDefinitionsStr(String incorrectDefinitionsStr) {
        this.incorrectDefinitionsStr = incorrectDefinitionsStr;
    }

    public boolean doesQuizExists() {
        AppDatabase db = Session.getInstance().getAppDatabase();
        QuizDao quizDao = db.quizDao();
        Quiz currentQuizInDb = quizDao.findQuizByName(name);

        return currentQuizInDb != null;
    }

    public QuizSession createSession() {
        // attach words from db to quiz
        AppDatabase db = Session.getInstance().getAppDatabase();
        WordDao wordDao = db.wordDao();
        List<Word> words = wordDao.findWordsByQuizId(id);

        this.setWordList(words);
        QuizSession quizSession = new QuizSession();
        quizSession.setBaseQuiz(this);

        return quizSession;
    }

    public void delete() {
        AppDatabase db = Session.getInstance().getAppDatabase();

        QuizDao quizDao = db.quizDao();
        WordDao wordDao = db.wordDao();
        QuizScoresDao quizScoresDao = db.quizScoresDao();

        quizDao.deleteQuiz(this.getId());
        wordDao.deleteWordsWithQuizId(this.getId());
        quizScoresDao.deleteQuizScoresWithQuizId(this.getId());
    }

}
