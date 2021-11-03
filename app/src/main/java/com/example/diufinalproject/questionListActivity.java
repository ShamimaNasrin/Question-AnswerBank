package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Java_class.QuestionAdapter;
import Java_class.Questions;
import TeacherAllActivity.TeacherDashboardActivity;

public class questionListActivity extends AppCompatActivity {

    SearchView questionSearchView;
    RecyclerView questionPdfRV;
    QuestionAdapter questionAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);

        inintialization();
        questionPdfRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Questions> options = new FirebaseRecyclerOptions.Builder<Questions>()
                .setQuery(databaseReference,Questions.class)
                .build();

        questionAdapter = new QuestionAdapter(options);
        questionPdfRV.setAdapter(questionAdapter);
        questionSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                questionSearchMethod(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                questionSearchMethod(s);
                return false;
            }
        });


    }

    private void questionSearchMethod(String s) {
        FirebaseRecyclerOptions<Questions> options = new FirebaseRecyclerOptions.Builder<Questions>()
                .setQuery(databaseReference.orderByChild("questionCourse").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"),Questions.class)
                .build();
        questionAdapter = new QuestionAdapter(options);
        questionAdapter.startListening();
        questionPdfRV.setAdapter(questionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        questionAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        questionAdapter.stopListening();
    }

    private void inintialization() {
        questionPdfRV = (RecyclerView) findViewById(R.id.questionRVID);
        questionSearchView = (SearchView) findViewById(R.id.questionSearchViewID);

        databaseReference = FirebaseDatabase.getInstance().getReference("questions");
    }
}
