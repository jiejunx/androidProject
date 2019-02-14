package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;


public class RegisterActivity extends AppCompatActivity {

    protected String username = "";
    protected String major = "";
    protected String seniorityLevel = "";
    protected String email = "";

    protected final String[] seniorityLevelPaths = {
            "Freshman",
            "Sophomore",
            "Junior",
            "Senior",
            "Grad",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init inputs
        initUserNameInput();
        initMajorInput();
        initSeniorityLevelSpinner();
        initEmailInput();

        // watch inputs
        watchUserNameInput();
        watchMajorInput();
        watchSeniorityLevelSpinner();
        watchEmailInput();
    }

    protected void initUserNameInput() {
        EditText input = findViewById(R.id.username_input);
        input.setText(username);
    }

    protected void watchUserNameInput() {
        EditText selectedInput = findViewById(R.id.username_input);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) { handleUserNameInputChange(str); }
        });
    }

    protected void handleUserNameInputChange(Editable str) {
        String inputVal = str.toString();
        username = inputVal;
    }

    protected void initMajorInput() {
        EditText input = findViewById(R.id.major_input);
        input.setText(major);
    }

    protected void watchMajorInput() {
        EditText selectedInput = findViewById(R.id.major_input);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) { handleMajorInputChange(str); }
        });
    }

    protected void handleMajorInputChange(Editable str) {
        String inputVal = str.toString();
        major = inputVal;
    }

    protected void initSeniorityLevelSpinner() {
        Spinner selectedSpinner = findViewById(R.id.seniority_level_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this,
                android.R.layout.simple_spinner_item, seniorityLevelPaths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectedSpinner.setAdapter(adapter);
    }

    protected void watchSeniorityLevelSpinner() {
        Spinner spinner = findViewById(R.id.seniority_level_spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                handleSeniorityLevelChange(seniorityLevelPaths[position]);

            }

            // this is not used, is just to avoid compilation errors
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}
        });
    }

    protected void handleSeniorityLevelChange(String str) {
        seniorityLevel = str;
    }

    protected void initEmailInput() {
        EditText input = findViewById(R.id.email_input);
        input.setText(email);
    }

    protected void watchEmailInput() {
        final EditText selectedInput = findViewById(R.id.email_input);

        selectedInput.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence str, int start, int count, int after) {}
            public void onTextChanged(CharSequence str, int start, int before, int count) {}
            public void afterTextChanged(Editable str) {
                if (str.toString() == "a") {
                    selectedInput.setError("This error man");
                }
                else{
                handleEmailInputChange(str); }}
        });
    }

    protected void handleEmailInputChange(Editable str) {
        String inputVal = str.toString();
        email = inputVal;
    }


    protected void navigateToLoginScreen(String newStudentUserName) {
        Intent intent = new Intent(this, LoginActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", String.valueOf(newStudentUserName));
        intent.putExtras(bundle);

        startActivity(intent);
    }


    public void onRegisterPress(View view) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        //  Validate form errors
        if (username.equals("")) {
            EditText usernameInputField = findViewById(R.id.username_input);
            usernameInputField.setError("Please enter Username");
            return;
        } else if (major.equals("")) {
            EditText majorInputField = findViewById(R.id.major_input);
            majorInputField.setError("Please enter Major");
            return;
        } else if (email.length() == 0 || !email.matches(emailPattern)) {
            EditText emailInputField = findViewById(R.id.email_input);
            emailInputField.setError("Please enter valid email");
            return;
        }


        Student student = new Student();
        student.setUserName(username);
        student.setEmail(email);
        student.setMajor(major);
        student.setSeniorityLevel(seniorityLevel);


        if (student.isAlreadyRegistered()) {
            showAlreadyRegisterErrorAlert(student);
        } else {
            final Student newStudent = student.register();
            if (newStudent != null) {
                showSuccessAlert(newStudent);
            } else {
                showErrorAlert();
            }
        }
    }

    private void showAlreadyRegisterErrorAlert(Student student) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(student.getUserName() + " is already registered!")
                .setPositiveButton(android.R.string.ok, null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Couldn't register student :( Please try again")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSuccessAlert(final Student newStudent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success")
                .setMessage("Congratulations " + newStudent.getUserName() + " is now registered!")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 3. move to the next screen
                        navigateToLoginScreen(newStudent.getUserName());
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}