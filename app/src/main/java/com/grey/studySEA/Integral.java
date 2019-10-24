package com.grey.studySEA;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Integral extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
    }

    String[] integrals = {"a", "x^n", "1/x", "e^x", "a^x", "lnx", "sinx", "cosx", "tanx", "cotx", "cscx", "sec^2(x)", "secxtanx", "csc^2(x)", "cscxcotx","tan^2(x)", "1/(a^2 + x^2)", "1/((a^2-x^2)^1/2)", "1/(x((x^2-a^2)^1/2))"};
    String[] top20 = {"ax+c", "x^n+1/n+1+c","ln|x|+c","e^x+c", "a^x/ln|a|+c","xlnx-x+c", "-cosx+c", "sinx+c", "ln|secx|+c", "ln|sinx|+c", "ln|secx+tanx|+c", "-ln|cscx+cotx|+c", "tanx+c", "secx+c", "-cotx+c", "-cscx+c", "tanx-x+c", "(1/a)tan^-1(x/a)+c", "sin^-1(x/a)+c","(1/a)sec^-1(|x/a|)+c"};


}
