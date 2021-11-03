package com.example.diufinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Java_class.Student;
import Java_class.StudentAdapter;
import Java_class.Teacher;
import Java_class.TeacherAdapter;

public class StudentListActivity extends AppCompatActivity {

    RecyclerView studentRV;
    StudentAdapter studentAdapter;
    DatabaseReference databaseReference;
    SearchView studentSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        initial();

        studentRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Student> options = new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(databaseReference,Student.class)
                .build();

        studentAdapter = new StudentAdapter(options);
        studentRV.setAdapter(studentAdapter);

        studentSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                StudentSearchMethod(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                StudentSearchMethod(s);
                return false;
            }
        });
    }

    private void StudentSearchMethod(String s) {
        FirebaseRecyclerOptions<Student> options = new FirebaseRecyclerOptions.Builder<Student>()
                .setQuery(databaseReference.orderByChild("studentName").startAt(s.toUpperCase()).endAt(s.toLowerCase()+"\uf8ff"),Student.class)
                .build();
        studentAdapter = new StudentAdapter(options);
        studentAdapter.startListening();
        studentRV.setAdapter(studentAdapter);
    }

    private void initial() {
        studentRV = (RecyclerView) findViewById(R.id.StudentRVID);
        studentSV = (SearchView) findViewById(R.id.StudentSearchViewID);
        databaseReference = FirebaseDatabase.getInstance().getReference("student");
    }
    @Override
    protected void onStart() {
        super.onStart();
        studentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        studentAdapter.stopListening();
    }
}
