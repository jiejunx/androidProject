package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizScore;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizScoresDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.StudentDao;

public class QuizScoresActivity extends AppCompatActivity {

    protected Student student = Session.getInstance().getStudent();

    private TextView quizNameField;
    private TextView userNameField;

    private TextView firstScoreField;
    private TextView bestScoreField;
    private TextView dateOfFirstScoreField;
    private TextView dateOfBestScoreField;

    private TextView firstScoreLabel;
    private TextView bestScoreLabel;
    private TextView dateOfFirstScoreLabel;
    private TextView dateOfBestScoreLabel;

    private TextView studentsWhoScoredFull;


    private Quiz quiz;
    private List<QuizScore> quizScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_score);
        initView();
        // check if the user has taken the quiz, show or hide info
        showStudentScores();
        showFullScore();
    }


    private void initView() {

        Bundle bundle = getIntent().getExtras();
        String quizId = bundle.getString("quizId");
        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        QuizScoresDao quizScoresDao = db.quizScoresDao();

        QuizDao quizDao = db.quizDao();
        quiz = quizDao.findQuizById(Integer.parseInt(quizId));

        quizScores = quizScoresDao.findQuizScoreByQuizId(Integer.parseInt(quizId));

        quizNameField = findViewById(R.id.show_quizname);
        userNameField = findViewById(R.id.show_username);

        firstScoreField = findViewById(R.id.get_first_score);
        bestScoreField = findViewById(R.id.get_highest_score);
        dateOfFirstScoreField = findViewById(R.id.datetime1);
        dateOfBestScoreField = findViewById(R.id.datetime2);


        firstScoreLabel = findViewById(R.id.firstscore_title);
        bestScoreLabel = findViewById(R.id.highest_score);
        dateOfFirstScoreLabel = findViewById(R.id.date_title1);
        dateOfBestScoreLabel = findViewById(R.id.datetitle2);

        studentsWhoScoredFull = findViewById(R.id.student1);

    }


    public void showStudentScores() {
        quizNameField.setText(quiz.getName());
        userNameField.setText(student.getUserName());

        List<QuizScore> quizScoresOfCurrentUser = new ArrayList<>();
        Integer firstScoreOfCurrentStudent = -1;
        Date dateOfFirstScore = new Date();

        Integer bestScoreOfCurrentStudent = -1;
        Date dateOfBestScore = new Date();

        for (QuizScore quizScore : quizScores) {
            if (quizScore.getStudentId() == student.getId()) {
                quizScoresOfCurrentUser.add(quizScore);
                if (quizScore.getScore() >= bestScoreOfCurrentStudent) {
                    bestScoreOfCurrentStudent = quizScore.getScore();
                    dateOfBestScore = quizScore.getDateTime();
                }

                if (dateOfFirstScore.compareTo(quizScore.getDateTime()) > 0) {
                    dateOfFirstScore = quizScore.getDateTime();
                    firstScoreOfCurrentStudent = quizScore.getScore();
                }
            }
        }


        if (quizScoresOfCurrentUser.size() == 0) {
            firstScoreLabel.setVisibility(View.GONE);
            bestScoreLabel.setVisibility(View.GONE);
            dateOfFirstScoreLabel.setVisibility(View.GONE);
            dateOfBestScoreLabel.setVisibility(View.GONE);

            firstScoreField.setVisibility(View.GONE);
            bestScoreField.setVisibility(View.GONE);
            dateOfFirstScoreField.setVisibility(View.GONE);
            dateOfBestScoreField.setVisibility(View.GONE);

        } else {

            firstScoreLabel.setVisibility(View.VISIBLE);
            bestScoreLabel.setVisibility(View.VISIBLE);
            dateOfFirstScoreLabel.setVisibility(View.VISIBLE);
            dateOfBestScoreLabel.setVisibility(View.VISIBLE);

            firstScoreField.setText(String.valueOf(firstScoreOfCurrentStudent));
            bestScoreField.setText(String.valueOf(bestScoreOfCurrentStudent));
            dateOfFirstScoreField.setText(dateOfFirstScore.toString());
            dateOfBestScoreField.setText(dateOfBestScore.toString());
        }


    }

    public List<Integer> fullScoreList() {
        List<QuizScore> scores = quizScores;

        Collections.sort(scores, new Comparator<QuizScore>() {
            @Override
            public int compare(QuizScore o1, QuizScore o2) {
                return o1.getDateTime().compareTo(o2.getDateTime());
            }
        });
        List<Integer> studentsIdsWhoScoredFullMarks = new ArrayList<>();
        for (QuizScore quizScore : scores) {
            if (quizScore.getScore() == 100)
                studentsIdsWhoScoredFullMarks.add(quizScore.getStudentId());
        }

        return studentsIdsWhoScoredFullMarks;
    }


    private void showFullScore() {

        List<Integer> studentsIdsWhoReceivedFullMarks = fullScoreList();
        if (studentsIdsWhoReceivedFullMarks.size() > 3) {
            studentsIdsWhoReceivedFullMarks = studentsIdsWhoReceivedFullMarks.subList(0, 3);
        }
        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        StudentDao studentDao = db.studentDao();
        List<Student> firstStudentsWhoScoredFullMarks = studentDao.getStudentsWithIds(studentsIdsWhoReceivedFullMarks);

        String text  = "No students has scored full marks on this quiz yet!";

        if (firstStudentsWhoScoredFullMarks.size() > 0) {
            List<String> studentsUserNames = new ArrayList<>();
            for (Student student : firstStudentsWhoScoredFullMarks) {
                studentsUserNames.add(student.getUserName());
            }
            Collections.sort(studentsUserNames);
            text = StringUtils.join(studentsUserNames, "\n");
        }
        studentsWhoScoredFull.setText(text);

    }

}



