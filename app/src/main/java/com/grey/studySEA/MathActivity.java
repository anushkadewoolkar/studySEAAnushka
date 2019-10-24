package com.grey.studySEA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MathActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);
    }

    public void integral (View v) {
        Intent intent = new Intent(MathActivity.this, Integral.class);
        startActivity(intent);
    }
}
