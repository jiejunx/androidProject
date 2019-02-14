package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz.models.Student;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUserNameInput();
    }

    protected void initUserNameInput() {
        EditText input = findViewById(R.id.login_activity_username_input);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String passedUserName = bundle.getString("username");
            if (passedUserName != null) {
                input.setText(passedUserName);
            }
        }
    }


    public void loginButtonClicked(View view) {
        // Validate form errors
        EditText userNameField = findViewById(R.id.login_activity_username_input);
        String userName = userNameField.getText().toString();
        if(userName.equals("")){
            userNameField.setError("Please enter username");
            return;
        }

        Student student = new Student();
        student.setUserName(userName);

        if (!student.isAlreadyRegistered()) {
            showErrorAlert();
        } else {
            student.login();
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }

    }

    private void showErrorAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Invalid Credentials")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}