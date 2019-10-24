package com.grey.studySEA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ChoicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
    }

    public void math (View v) {
        Intent intent = new Intent(ChoicesActivity.this, MathActivity.class);
        startActivity(intent);
    }

}

