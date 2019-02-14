package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;

public class AddQuizActivity extends AppCompatActivity {

    private Student student = Session.getInstance().getStudent();
    private AddQuizController qc = AddQuizController.getInstance();
    private String quizName = "";
    private String description = "";
    private String[] numberOfQuestions = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addquiz);

        initView();
        watchQuizNameInput();
        watchDescriptionInput();

    }

    private void initView() {
        if (qc.getQuiz() == null) {
            Quiz quiz = new Quiz();
            qc.setQuiz(quiz);
        } else {
            quizName = qc.quiz.getName();
            description = qc.quiz.getDescription();
        }

        // quiz name input
        EditText quizNameInput = findViewById(R.id.quiz_name);
        quizNameInput.setText(quizName);

        // description input
        EditText descriptionInput = findViewById(R.id.description);
        descriptionInput.setText(description);

        // number of questions spinner
        Spinner numberOfQuestionsSpinner = findViewById(R.id.question_number);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddQuizActivity.this,
                android.R.layout.simple_spinner_item, numberOfQuestions);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberOfQuestionsSpinner.setAdapter(adapter);
    }

    private void watchQuizNameInput() {
        EditText selectedInput = findViewById(R.id.quiz_name);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                quizName = str.toString();
            }
        });
    }

    private void watchDescriptionInput() {
        EditText selectedInput = findViewById(R.id.description);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                description = str.toString();
            }
        });
    }

    public void addQuiz(View view) {
        // Validate input
        if(quizName.equals("")){
            EditText quiz_name = findViewById(R.id.quiz_name);
            quiz_name.setError("Please enter quiz name");
            return;
        }

        if(description.equals("")){
            EditText descriptionInput = findViewById(R.id.description);
            descriptionInput.setError("Please enter description");
            return;
        }

        // check if a quiz with the same name exist

        Quiz quiz = new Quiz();
        quiz.setName(quizName);
        if (quiz.doesQuizExists()) {
            showErrorAlert();
            return;
        }

        // get spinner value
        Spinner questionNumberSpinner = findViewById(R.id.question_number);
        String nWordsStr = questionNumberSpinner.getSelectedItem().toString();
        Integer nWords = Integer.parseInt(nWordsStr);

        Integer studentId = student.getId();

        quiz.setDescription(description);
        quiz.setStudentId(studentId);
        quiz.setNumberOfWords(nWords);
        quiz.setStudentId(studentId);

        qc.quiz = quiz;

        // navigate to addQuizWordActivity
        Intent intent = new Intent(this, AddQuizWordActivity.class);
        startActivity(intent);

    }

    private void showErrorAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("There is another quiz with the same name!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
