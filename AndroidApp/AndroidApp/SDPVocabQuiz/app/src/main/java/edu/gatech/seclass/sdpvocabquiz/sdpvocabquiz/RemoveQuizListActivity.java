package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;


public class RemoveQuizListActivity extends AppCompatActivity {

    private Student student = Session.getInstance().getStudent();
    private List<Quiz> studentQuizzesList;
    private Quiz selectedQuiz = null;
    private Spinner spinner;
    private TextView noQuizText;
    private Button removeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_quiz_list);
        spinner = findViewById(R.id.get_quiz);
        noQuizText = findViewById(R.id.no_quiz_text);
        removeButton = findViewById(R.id.remove_button);

        initView();
    }


    private void initView() {
        AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
        QuizDao quizDao = db.quizDao();
        studentQuizzesList = quizDao.getQuizzesCreatedByStudent(student.getId());

        ArrayAdapter<Quiz> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, studentQuizzesList);
        spinner.setAdapter(spinnerArrayAdapter);

        if (studentQuizzesList.size() == 0) {
            spinner.setVisibility(View.GONE);
            removeButton.setVisibility(View.GONE);
            noQuizText.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.VISIBLE);
            removeButton.setVisibility(View.VISIBLE);
            noQuizText.setVisibility(View.GONE);
        }
    }


    public void removeQuiz(View view) {
        selectedQuiz = (Quiz) spinner.getSelectedItem();
        student.removeQuiz(selectedQuiz);

        initView();
    }
}
