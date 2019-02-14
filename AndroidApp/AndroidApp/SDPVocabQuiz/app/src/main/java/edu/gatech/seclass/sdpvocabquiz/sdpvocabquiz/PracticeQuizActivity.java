package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Answer;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizScore;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.QuizSession;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Word;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizScoresDao;

public class PracticeQuizActivity extends AppCompatActivity {
    // PracticeQuiz controller
    private PracticeQuizController pqc = PracticeQuizController.getInstance();
    private QuizSession quizSession = pqc.getQuizSession();
    private Student student = Session.getInstance().getStudent();

    private Integer nWords = 0;
    private Integer currentIdx = 0;
    private Word currentWord = null;
    private List<String> currentDefinitions = null;
    private String selectedDefinition = null;
    private List<Answer> answers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_quiz);

        // get number of words
        nWords = quizSession.getBaseQuiz().getNumberOfWords();
        // get data for current index
        getCurrentIndexData();

        initView(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pqc.cleanController();
    }

    private void getCurrentIndexData() {
        currentWord = quizSession.getWordByIndex(currentIdx);
        currentDefinitions = quizSession.getRandomDefinitionsForWord();
    }

    private void initView(Boolean next) {
        TextView qnView = findViewById(R.id.word_text);
        qnView.setText(currentWord.getText());

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        currentDefinitions.add(currentWord.getDefinition());
        Collections.shuffle(currentDefinitions);


        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            String defText = currentDefinitions.get(i);

            ((RadioButton) radioGroup.getChildAt(i)).setText(defText);
        }


        if (currentIdx == nWords - 1) {
            Button nextButton = findViewById(R.id.next_button);
            nextButton.setText("FINISH");
        }
    }

    private void showNextWord() {
        currentIdx += 1;
        getCurrentIndexData();
        initView(true);
    }

    private void navigateToDashboardScreen() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private void finishQuizSession() {
        // save answers to quizSession
        quizSession.setAnswers(answers);

        // create quiz Score
        QuizScore quizScore = new QuizScore();

        Date dateTime = new Date();
        Integer quizId = quizSession.getBaseQuiz().getId();
        Integer studentId = student.getId();
        Integer score = quizSession.calculateScore();

        quizScore.setQuizId(quizId);
        quizScore.setStudentId(studentId);
        quizScore.setScore(score);
        quizScore.setDateTime(dateTime);

        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        QuizScoresDao quizScoresDao = db.quizScoresDao();
        quizScoresDao.insert(quizScore);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Test Score - " + quizSession.getBaseQuiz().getName())
                .setMessage("Your score is: " + quizSession.calculateScore())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        navigateToDashboardScreen();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();


    }


    public void onSelectDefinition(View view) {
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Integer selectedIdx = radioGroup.indexOfChild(findViewById(radioGroup.getCheckedRadioButtonId()));

        // no answer has been selected
        if (selectedIdx == -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Hey!")
                    .setMessage("You didn't provide an answer")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }

        selectedDefinition =  currentDefinitions.get(selectedIdx);

        // all answers have been selected and next button is repressed
        if (answers.size() == nWords) {
            finishQuizSession();
        }


        Answer answer = new Answer();
        answer.setWord(currentWord);
        answer.setUserAnswer(selectedDefinition);
        answers.add(answer);

        if (currentIdx == nWords - 1) {
            finishQuizSession();
        } else {
            showNextWord();
        }

    }


}
