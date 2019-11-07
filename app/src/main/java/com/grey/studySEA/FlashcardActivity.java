package com.grey.studySEA;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class FlashcardActivity extends AppCompatActivity {

    studySEAHelper studySEAHelper;
    List<Flashcard> totalFlashcards;

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        studySEAHelper = new studySEAHelper(getApplicationContext());
        totalFlashcards = studySEAHelper.getTotalCards();

        //displays random card
        if (totalFlashcards != null && totalFlashcards.size() > 0) {
            int rand = getRandomNumber(0,totalFlashcards.size()-1);
            ((TextView) findViewById(R.id.flashcard_question)).setText(totalFlashcards.get(rand).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer1)).setText(totalFlashcards.get(rand).getAnswer());
        }

        findViewById(R.id.flashcard_answer1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.color.white));

            }
        });

        //If user clicks eye image, will show answer
        //How to use visibility: https://developer.android.com/reference/android/view/View
        findViewById(R.id.visibilityoff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer1).setVisibility(View.INVISIBLE);
                v.setVisibility(View.INVISIBLE);
                findViewById(R.id.visibilityon).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.visibilityon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_answer1).setVisibility(View.VISIBLE);
                v.setVisibility(View.INVISIBLE);
                findViewById(R.id.visibilityoff).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, addflashcard.class);
                FlashcardActivity.this.startActivityForResult(intent,100);
            }
        });

        //Allows users to change flashcard (both question and answer) after they add it
        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashcardActivity.this, addflashcard.class);
                intent.putExtra("question",((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                intent.putExtra("answer1",((TextView) findViewById(R.id.flashcard_answer1)).getText().toString());
                intent.putExtra("edit","StudySEA");
                FlashcardActivity.this.startActivityForResult(intent,100);
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //First flashcard
                if (totalFlashcards.size() == 0){
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card to get started!");
                    ((TextView) findViewById(R.id.flashcard_answer1)).setText("");
                }else {
                    //sets the question and answer with data from database
                    int rand = getRandomNumber(0, totalFlashcards.size() - 1);
                    ((TextView) findViewById(R.id.flashcard_question)).setText(totalFlashcards.get(rand).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer1)).setText(totalFlashcards.get(rand).getAnswer());
                    }
            }
        });

        //wont delete initial card
        findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studySEAHelper.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                totalFlashcards = studySEAHelper.getTotalCards();

                if (totalFlashcards.size() == 0) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText("Add a card to get started!");
                    ((TextView) findViewById(R.id.flashcard_answer1)).setText("");
                }else{
                    findViewById(R.id.next_button).performClick();
                }

            }
        });

    }

    //TRY-CATCH FOR IF USER MAKES MORE THAN 100 FLASHCARDS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            try {

                String question = data.getExtras().getString("question");
                String answer1 = data.getExtras().getString("answer1");

                if (data.getExtras().getString("edit") != null) {
                    studySEAHelper.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                }

                ((TextView) findViewById(R.id.flashcard_question)).setText(question);
                ((TextView) findViewById(R.id.flashcard_answer1)).setText(answer1);

                studySEAHelper.insertCard(new Flashcard(question, answer1));
                totalFlashcards = studySEAHelper.getTotalCards();
                Snackbar.make(findViewById(R.id.RelativeLayout), "Card created successfully", Snackbar.LENGTH_LONG).show();
            } catch (Exception e) {

            }
        }
    }

}