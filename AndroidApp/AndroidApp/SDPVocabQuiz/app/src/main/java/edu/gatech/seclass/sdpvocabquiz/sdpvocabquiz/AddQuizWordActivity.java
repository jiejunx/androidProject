package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Quiz;
import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Word;

public class AddQuizWordActivity extends AppCompatActivity {

    private AddQuizController qc = AddQuizController.getInstance();

    private Integer nWords = 1;
    private List words = new ArrayList<Word>();
    private Integer currentIdx = 0;
    private String currentWord = "";
    private String currentDefinition = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quiz_word);

        initView();
        watchWordInput();
        watchDefinitionInput();
    }

    private void initView() {
        // get number of questions for quiz
        Quiz quiz = qc.getQuiz();
        nWords = quiz.getNumberOfWords();

        // word input
        EditText quizNameInput = findViewById(R.id.input_word);
        quizNameInput.setText(currentWord);

        // definition input
        EditText definitionInput = findViewById(R.id.input_definition);
        definitionInput.setText(currentDefinition);
    }

    private void watchWordInput() {
        EditText selectedInput = findViewById(R.id.input_word);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                currentWord = str.toString();
            }
        });
    }

    private void watchDefinitionInput() {
        EditText selectedInput = findViewById(R.id.input_definition);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                currentDefinition = str.toString();
            }
        });
    }

    public void onAddQuizWord(View view) {
        // Validate form
        if(currentWord.equals("")){
            EditText quizNameInput = findViewById(R.id.input_word);
            quizNameInput.setError("Please enter word");
        }
        else if(currentDefinition.equals("")){
            EditText definitionInput = findViewById(R.id.input_definition);
            definitionInput.setError("Please enter definition");
        }
        else {
            // add word to words array
            Word quizWord = new Word();
            quizWord.setText(currentWord);
            quizWord.setDefinition(currentDefinition);
            words.add(quizWord);

            if (currentIdx == nWords - 1) {
                // navigate to addQuizWordActivity
                qc.quiz.setWordList(words);
                Intent intent = new Intent(this, AddQuizDefinitionsActivity.class);
                startActivity(intent);
            } else {
                // prepare view for next word
                currentIdx += 1;
                TextView qnView = findViewById(R.id.question_number);
                String nextQuestionNumber = Integer.toString(currentIdx + 1);
                qnView.setText(nextQuestionNumber);


                EditText wordInput = findViewById(R.id.input_word);
                wordInput.setText("", TextView.BufferType.EDITABLE);
                wordInput.requestFocus();

                EditText definitionInput = findViewById(R.id.input_definition);
                definitionInput.setText("", TextView.BufferType.EDITABLE);
            }
        }
    }
}
