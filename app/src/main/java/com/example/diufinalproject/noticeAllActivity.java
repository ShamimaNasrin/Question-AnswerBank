package com.example.diufinalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import Java_class.Notice;
import Java_class.NoticeAdapter;
import Java_class.Teacher;
import Java_class.TeacherAdapter;

public class noticeAllActivity extends AppCompatActivity {

    RecyclerView noticeRV;
    NoticeAdapter noticeAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_all);
        initial();

        noticeRV.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<Notice> options = new FirebaseRecyclerOptions.Builder<Notice>()
                .setQuery(databaseReference,Notice.class)
                .build();
        noticeAdapter = new NoticeAdapter(options);
        noticeRV.setAdapter(noticeAdapter);
    }

    private void initial() {
        noticeRV=(RecyclerView) findViewById(R.id.noticeRVID);
        databaseReference = FirebaseDatabase.getInstance().getReference("notices");
    }

    @Override
    protected void onStart() {
        super.onStart();
        noticeAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        noticeAdapter.stopListening();
    }
}
