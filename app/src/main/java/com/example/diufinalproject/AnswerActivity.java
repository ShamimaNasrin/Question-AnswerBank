package com.example.diufinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Java_class.Answer;
import Java_class.AnswerAdapter;
import Java_class.QuestionAdapter;
import Java_class.Questions;

public class AnswerActivity extends AppCompatActivity {

    Button contributeBTN;
    SearchView answerSearchView;
    RecyclerView answerPdfRV;
    AnswerAdapter answerAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        initial();

        answerPdfRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Answer> options = new FirebaseRecyclerOptions.Builder<Answer>()
                .setQuery(databaseReference,Answer.class)
                .build();

        answerAdapter = new AnswerAdapter(options);
        answerPdfRV.setAdapter(answerAdapter);
        answerSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                answerSearchMethod(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                answerSearchMethod(s);
                return false;
            }
        });
        contributeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AnswerActivity.this,contributeAnswerActivity.class));
            }
        });

    }

    private void answerSearchMethod(String s) {
        FirebaseRecyclerOptions<Answer> options = new FirebaseRecyclerOptions.Builder<Answer>()
                .setQuery(databaseReference.orderByChild("questionCourse").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"),Answer.class)
                .build();
        answerAdapter = new AnswerAdapter(options);
        answerAdapter.startListening();
        answerPdfRV.setAdapter(answerAdapter);
    }

    private void initial() {
        contributeBTN = (Button) findViewById(R.id.contributeAnswerID);
        answerPdfRV = (RecyclerView) findViewById(R.id.answerRVID);
        answerSearchView = (SearchView) findViewById(R.id.answerSearchViewID);

        databaseReference = FirebaseDatabase.getInstance().getReference("Answers");

    }
    @Override
    protected void onStart() {
        super.onStart();
        answerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        answerAdapter.stopListening();
    }
}
