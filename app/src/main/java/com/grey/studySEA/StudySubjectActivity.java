package com.grey.studySEA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class StudySubjectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_subject);
    }

    public void study (View v) {
        Intent intent = new Intent(StudySubjectActivity.this, ChoicesActivity.class);
        startActivity(intent);
    }
}
