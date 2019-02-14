package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizSession;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;


public class PracticeQuizListActivity extends AppCompatActivity {

    // Student from session
    private Student student = Session.getInstance().getStudent();
    // PracticeQuiz controller
    private PracticeQuizController pqc = PracticeQuizController.getInstance();

    private Quiz selectedQuiz = null;
    private Spinner quizzesSpinner;
    private TextView noQuizText;
    private Button startButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz_list);
        // get spinner from view
        quizzesSpinner = findViewById(R.id.get_quiz_practice);
        noQuizText = findViewById(R.id.no_quiz_text);
        startButton = findViewById(R.id.next_button);

        initView();
    }

    private void initView() {
        List<Quiz> quizzesAvailableForPractice = student.viewQuizzesAvailableForPractice();

        ArrayAdapter<Quiz> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, quizzesAvailableForPractice);
        quizzesSpinner.setAdapter(spinnerArrayAdapter);

        if (quizzesAvailableForPractice.size() == 0) {
            quizzesSpinner.setVisibility(View.GONE);
            startButton.setVisibility(View.GONE);
            noQuizText.setVisibility(View.VISIBLE);
        } else {
            quizzesSpinner.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.VISIBLE);
            noQuizText.setVisibility(View.GONE);
        }
    }

    public void practiceQuiz(View view) {
        selectedQuiz = (Quiz) quizzesSpinner.getSelectedItem();

        QuizSession quizSession = selectedQuiz.createSession();

        // set the base quiz to practice
        pqc.setQuizSession(quizSession);

        // navigate to addQuizWordActivity
        Intent intent = new Intent(this, PracticeQuizActivity.class);
        startActivity(intent);


    }
}
