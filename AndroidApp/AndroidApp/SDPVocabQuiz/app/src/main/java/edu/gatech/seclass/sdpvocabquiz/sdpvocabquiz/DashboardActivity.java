package edu.gatech.seclass.sdpvocabquiz.sdpvocabquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void onBackPressed() {
        // ignore android back button
        return;
    }

    public void navigateToAddQuiz(View view) {
        Intent intent = new Intent(this, AddQuizActivity.class);
        startActivity(intent);
    }

    public void navigateToRemoveQuizList(View view) {
        Intent intent = new Intent(this, RemoveQuizListActivity.class);
        startActivity(intent);
    }

    public void navigateToPracticeQuizList(View view) {
        Intent intent = new Intent(this, PracticeQuizListActivity.class);
        startActivity(intent);
    }

    public void navigateToViewQuizScoresList(View view) {
        Intent intent = new Intent(this, QuizScoreListActivity.class);
        startActivity(intent);
    }

    public void logout(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
