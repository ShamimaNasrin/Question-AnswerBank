package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import Java_class.Student;
import Java_class.Teacher;

public class TeacherProfileActivity extends AppCompatActivity {

    TextView emailTV;
    EditText nameET,phoneET;
    ImageView imageView;
    String name,phone,email;
    Button updateBTN;

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String userID;
    private static final String Users = "teacher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        initial();

        fetchProfile();

        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNameChanged() || isPhoneChanged()){
                    Toast.makeText(TeacherProfileActivity.this, "Profile has updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TeacherProfileActivity.this, "Profile update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isNameChanged(){
        if(!name.equals(nameET.getText().toString())){
            userRef.child(userID).child("teacherName").setValue(nameET.getText().toString());
            return true;
        }else {
            return false;
        }
    }
    private boolean isPhoneChanged(){
        if(!phone.equals(phoneET.getText().toString())){
            userRef.child(userID).child("teacherPhone").setValue(phoneET.getText().toString());
            return true;
        }else {
            return false;
        }
    }

    private void fetchProfile() {
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.getValue(Teacher.class);
                if(teacher != null){
                    name = teacher.getTeacherName();
                    phone = teacher.getTeacherPhone();
                    email = teacher.getTeacherEmail();

                    nameET.setText(name);
                    phoneET.setText(phone);
                    emailTV.setText(email);
                    String photoLink = snapshot.child("teacherImage").getValue().toString();
                    Picasso.get().load(photoLink).into(imageView);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherProfileActivity.this, "Something wrong!"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initial() {
        emailTV = (TextView) findViewById(R.id.teacherEmailProfileTVid);
        nameET = (EditText) findViewById(R.id.teacherNameProfileETid);
        phoneET = (EditText) findViewById(R.id.teacherPhoneProfileETid);
        imageView = (ImageView) findViewById(R.id.teacherProfileIVid);
        updateBTN = (Button) findViewById(R.id.teacherProfileUpdateBTNid);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Users);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
    }
}
