package com.example.diufinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Java_class.QuestionAdapter;
import Java_class.Questions;
import Java_class.Teacher;
import Java_class.TeacherAdapter;

public class TeacherListActivity extends AppCompatActivity {

    RecyclerView teacherRV;
    TeacherAdapter teacherAdapter;
    DatabaseReference databaseReference;
    SearchView teacherSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_list);
        initialization();
        teacherRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Teacher> options = new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(databaseReference,Teacher.class)
                .build();

        teacherAdapter = new TeacherAdapter(options);
        teacherRV.setAdapter(teacherAdapter);

        teacherSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                TeacherSearchMethod(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                TeacherSearchMethod(s);
                return false;
            }
        });


    }

    private void TeacherSearchMethod(String s) {
        FirebaseRecyclerOptions<Teacher> options = new FirebaseRecyclerOptions.Builder<Teacher>()
                .setQuery(databaseReference.orderByChild("teacherName").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"),Teacher.class)
                .build();
        teacherAdapter = new TeacherAdapter(options);
        teacherAdapter.startListening();
        teacherRV.setAdapter(teacherAdapter);
    }

    private void initialization() {
        teacherRV = (RecyclerView) findViewById(R.id.TeacherRVID);
        teacherSV = (SearchView) findViewById(R.id.TeacherSearchViewID);
        databaseReference = FirebaseDatabase.getInstance().getReference("teacher");
    }
    @Override
    protected void onStart() {
        super.onStart();
        teacherAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        teacherAdapter.stopListening();
    }
}
