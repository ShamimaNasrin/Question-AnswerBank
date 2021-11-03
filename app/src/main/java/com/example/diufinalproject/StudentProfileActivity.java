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

public class StudentProfileActivity extends AppCompatActivity {

    ImageView profileImageview;
    TextView classTV,sectionnTV,rollTV,emailTV;
    EditText nameET,phoneTV;
    Button updateBTN;
    String name,S_class,roll,section,email,phone;

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String userID;
    private static final String Users = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        initial();
        fetchProfile();
        updateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNameChanged() || isPhoneChanged()){
                    Toast.makeText(StudentProfileActivity.this, "Data has updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(StudentProfileActivity.this, "Data update failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isNameChanged(){
        if(!name.equals(nameET.getText().toString())){
            userRef.child(userID).child("studentName").setValue(nameET.getText().toString());
            return true;
        }else {
            return false;
        }
    }
    private boolean isPhoneChanged(){
        if(!phone.equals(phoneTV.getText().toString())){
            userRef.child(userID).child("studentPhone").setValue(phoneTV.getText().toString());
            return true;
        }else {
            return false;
        }
    }


    private void fetchProfile() {
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                if(student != null){
                    name = student.getStudentName();
                    S_class = student.getStudentClass();
                    roll = student.getStudentRoll();
                    section = student.getStudentSection();
                    email = student.getStudentEmail();
                    phone = student.getStudentPhone();


                    nameET.setText(name);
                    classTV.setText(S_class);
                    rollTV.setText(roll);
                    sectionnTV.setText(section);
                    emailTV.setText(email);
                    phoneTV.setText(phone);
                    String photoLink = snapshot.child("profileImage").getValue().toString();
                    Picasso.get().load(photoLink).into(profileImageview);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfileActivity.this, "Something wrong!"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initial() {
        profileImageview=(ImageView) findViewById(R.id.profileIVid);
        sectionnTV=(TextView) findViewById(R.id.profileSectionETid);
        classTV=(TextView) findViewById(R.id.profileClassTVidf);
        rollTV=(TextView) findViewById(R.id.profileRollTVid);
        emailTV=(TextView) findViewById(R.id.profileEmailETid);
        phoneTV=(EditText) findViewById(R.id.profilePhoneTVid);
        nameET=(EditText) findViewById(R.id.profileNameETid);
        updateBTN=(Button) findViewById(R.id.profileUpdateBTNid);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Users);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

    }
}
