package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizScore;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizScoresDao;

public class QuizScoreListActivity extends AppCompatActivity {

    // Student from session
    protected Student student = Session.getInstance().getStudent();

    private Spinner quizzesSpinner;
    private TextView noQuizText;
    private Button showScores;

    private Quiz selectedQuiz = null;
    private List<Quiz> allQuizzes;
    private List<Quiz> sortedList = new ArrayList<>();
    private Map<Integer, Quiz> allQuizzesMapByQuizId = new HashMap<>();
    private QuizScoresDao quizScoresDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_quiz_score_list);

        quizzesSpinner = findViewById(R.id.get_quiz_practice);
        noQuizText = findViewById(R.id.no_quiz_text);
        showScores = findViewById(R.id.next_button);

        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        quizScoresDao = db.quizScoresDao();
        initView();
    }


    private List<QuizScore> quizzesIdsPlayedByCurrentStudent() {
        Map<Integer, QuizScore> quizScoreByQuizId = new HashMap();
        for (QuizScore quizScore : quizScoresDao.getAllQuizScores()) {
            if (quizScore.getStudentId() == student.getId()) {
                Integer quizId = quizScore.getQuizId();
                if (quizScoreByQuizId.containsKey(quizId)) {
                    Date timeWhenQuizWasTaken = quizScore.getDateTime();
                    QuizScore existingQuizScoreInMap = quizScoreByQuizId.get(quizId);
                    Date timeWhenExistingQuizWasTaken = existingQuizScoreInMap.getDateTime();

                    if (timeWhenQuizWasTaken.compareTo(timeWhenExistingQuizWasTaken) > 0) {
                        quizScoreByQuizId.put(quizId, quizScore);
                    }
                } else {
                    quizScoreByQuizId.put(quizId, quizScore);
                }
            }
        }

        List<QuizScore> latestQuizScoresForEachQuiz = new ArrayList<>(quizScoreByQuizId.values());
        Collections.sort(latestQuizScoresForEachQuiz, new Comparator<QuizScore>() {
            @Override
            public int compare(QuizScore o1, QuizScore o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });

        return latestQuizScoresForEachQuiz;
    }




    private void initView() {
        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        QuizDao quizDao = db.quizDao();
        allQuizzes = quizDao.getAllQuizzes();

        for (Quiz quiz : allQuizzes) {
            Integer quizId = quiz.getId();
            allQuizzesMapByQuizId.put(quizId, quiz);
        }

        List<QuizScore> quizScoresSortedByDatePlayedByCurrentStudent = quizzesIdsPlayedByCurrentStudent();
        for (QuizScore quizScore : quizScoresSortedByDatePlayedByCurrentStudent) {
            Quiz quiz = allQuizzesMapByQuizId.get(quizScore.getQuizId());
            allQuizzesMapByQuizId.remove(quizScore.getQuizId());
            sortedList.add(quiz);
        }


        sortedList.addAll(allQuizzesMapByQuizId.values());

        ArrayAdapter<Quiz> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sortedList);
        quizzesSpinner.setAdapter(spinnerArrayAdapter);

        if (allQuizzes.size() == 0) {
            quizzesSpinner.setVisibility(View.GONE);
            showScores.setVisibility(View.GONE);
            noQuizText.setVisibility(View.VISIBLE);
        } else {
            quizzesSpinner.setVisibility(View.VISIBLE);
            showScores.setVisibility(View.VISIBLE);
            noQuizText.setVisibility(View.GONE);
        }
    }

    public void showQuizScoreInfo(View view) {

        selectedQuiz = (Quiz) quizzesSpinner.getSelectedItem();
        Intent quizScoresIntent = new Intent(this, QuizScoresActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("quizId", String.valueOf(selectedQuiz.getId()));
        quizScoresIntent.putExtras(bundle);

        startActivity(quizScoresIntent);
    }



}

