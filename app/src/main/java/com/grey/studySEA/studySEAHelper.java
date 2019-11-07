package com.grey.studySEA;


import android.content.Context;

import androidx.room.Room;

import java.util.List;

public class studySEAHelper {
    private final AppDatabase studySEAHelper;

    studySEAHelper(Context context) {
        //How to use Room Database: https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase.Builder#allowmainthreadqueries
        studySEAHelper = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "StudySEAHelper")
                .allowMainThreadQueries() //Disables the main thread query check (queries are used to find specific data by filtering specific criteria) for Room
                .fallbackToDestructiveMigration() //Recreates the database instead of crashing
                .build(); //Creates the databases and initializes it.
    }

    public List<Flashcard> getTotalCards() {
        return studySEAHelper.flashcardDao().getAll();
    }

    public void insertCard(Flashcard flashcard) {
        studySEAHelper.flashcardDao().insertAll(flashcard);
    }

    public void deleteCard(String flashcardQuestion) {
        List<Flashcard> TotalCards = studySEAHelper.flashcardDao().getAll();
        for (Flashcard f : TotalCards) {
            if (f.getQuestion().equals(flashcardQuestion)) {
                studySEAHelper.flashcardDao().delete(f);
            }
        }
    }

    public void updateCard(Flashcard flashcard) {
        studySEAHelper.flashcardDao().update(flashcard);
    }
}
