package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Word;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.AppDatabase;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.QuizDao;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.persistence.WordDao;

public class AddQuizDefinitionsActivity extends AppCompatActivity {

    protected AddQuizController qc = AddQuizController.getInstance();

    protected Integer nWords = 1;
    protected List incorrectDefinitions = new ArrayList<String>();
    protected Integer currentIdx = 0;
    protected String def1 = "";
    protected String def2 = "";
    protected String def3 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_definitions);

        initView();
        watchInputs();
    }

    private void initView() {
        // get number of questions for quiz
        Quiz quiz = qc.getQuiz();
        nWords = quiz.getNumberOfWords();

        // def1 input
        EditText d1Input = findViewById(R.id.input_def1);
        d1Input.setText(def1);

        // def2 input
        EditText d2Input = findViewById(R.id.input_def2);
        d2Input.setText(def2);

        // def3 input
        EditText d3Input = findViewById(R.id.input_def3);
        d3Input.setText(def3);

        if (currentIdx + 1 == nWords) {
            Button nextButton = findViewById(R.id.next_button);
            nextButton.setText("CREATE");
        }
    }

    private void watchInputs() {

        EditText d1Input = findViewById(R.id.input_def1);

        d1Input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                def1 = str.toString();
            }
        });

        EditText d2Input = findViewById(R.id.input_def2);

        d2Input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                def2 = str.toString();
            }
        });

        EditText d3Input = findViewById(R.id.input_def3);

        d3Input.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                def3 = str.toString();
            }
        });
    }

    private void navigateToDashboardScreen() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private void navigateToAddQuizScreen() {
        Intent intent = new Intent(this, AddQuizActivity.class);
        startActivity(intent);
    }

    public void onAddDefinitionSet(View view) {

        // Form validation
        if(def1.equals("")){
            EditText d1Input = findViewById(R.id.input_def1);
            d1Input.setError("Please enter definition 1");
        }
        else if(def2.equals("")){
            EditText d2Input = findViewById(R.id.input_def2);
            d2Input.setError("Please enter definition 2");
        }
        else if(def3.equals("")){
            EditText d3Input = findViewById(R.id.input_def3);
            d3Input.setError("Please enter definition 3");
        }
        else {
            // add definitions to definitions array
            incorrectDefinitions.add(def1);
            incorrectDefinitions.add(def2);
            incorrectDefinitions.add(def3);

            AppDatabase db = AppDatabase.getAppDatabase(this.getApplicationContext());
            QuizDao quizDao = db.quizDao();
            WordDao wordDao = db.wordDao();


            // we are at the last screen
            if (currentIdx == nWords - 1) {
                // check if a quiz with the same name exist
                final Quiz currentQuizInDb = quizDao.findQuizByName(qc.quiz.getName());

                if (currentQuizInDb != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Error")
                            .setMessage("There is another quiz with the same name!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    qc.cleanController();
                                    navigateToAddQuizScreen();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    // save incorrect definitions
                    qc.quiz.setIncorrectDefinitions(incorrectDefinitions);

                    // store in db
                    quizDao.insert(qc.quiz);
                    Quiz quizFromDB = quizDao.findQuizByName(qc.quiz.getName());
                    qc.quiz.setId(quizFromDB.getId());
                    for (Word word : qc.quiz.getWordList()) {
                        word.setQuizId(quizFromDB.getId());
                        wordDao.insert(word);
                    }
                    // check that everything went right
                    final Quiz newQuiz = quizDao.findQuizByNameAndStudentId(qc.quiz.getName(), qc.quiz.getStudentId());

                    if (newQuiz != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Success")
                                .setMessage("Congratulations " + newQuiz.getName() + " has been created!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 3. clear singleton move to dashboard screen
                                        qc.cleanController();
                                        navigateToDashboardScreen();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Error")
                                .setMessage("Couldn't create quiz :(")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // 3. clear singleton move to dashboard screen
                                        qc.cleanController();
                                        navigateToDashboardScreen();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }
            } else {
                // prepare view for next word
                currentIdx += 1;
                TextView anView = findViewById(R.id.answer_num);
                String nextSetNumber = Integer.toString(currentIdx + 1);
                anView.setText(nextSetNumber);


                EditText d1Input = findViewById(R.id.input_def1);
                d1Input.setText("", TextView.BufferType.EDITABLE);
                d1Input.requestFocus();

                EditText d2Input = findViewById(R.id.input_def2);
                d2Input.setText("", TextView.BufferType.EDITABLE);

                EditText d3Input = findViewById(R.id.input_def3);
                d3Input.setText("", TextView.BufferType.EDITABLE);

                if (currentIdx + 1 == nWords) {
                    Button nextButton = findViewById(R.id.next_button);
                    nextButton.setText("CREATE");
                }
            }
        }
    }
}
