package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import StudentAllActivity.StudentDashboardActivity;
import TeacherAllActivity.TeacherDashboardActivity;

public class LoginActivity extends AppCompatActivity {

    EditText emailET,passwordET;
    Button loginButton;
    TextView registerTV,forgotTVid;
    RadioGroup radioGroup;
    RadioButton radioButton;

    AlertDialog.Builder forgotpassDialogue;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();

        registerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,registerActivity.class));
            }
        });
        forgotTVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpassDialogue = new AlertDialog.Builder(LoginActivity.this);
                LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                final View forgotView = inflater.inflate(R.layout.forgot_pass_design,null);
                EditText forgotEmail = (EditText) forgotView.findViewById(R.id.forgotEmailETid);
                Button forgotButton = (Button) forgotView.findViewById(R.id.forgotButtonID);

                forgotButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(forgotEmail.getText().toString().trim().isEmpty()){
                            forgotEmail.setError("Please enter your email first!");
                            forgotEmail.requestFocus();
                            return;
                        }
                        if(!Patterns.EMAIL_ADDRESS.matcher(forgotEmail.getText().toString().trim()).matches()){
                            forgotEmail.setError("Please enter correct email!");
                            forgotEmail.requestFocus();
                            return;
                        }
                        FirebaseAuth.getInstance().sendPasswordResetEmail(forgotEmail.getText().toString().trim())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(LoginActivity.this, "Check your email to reset the password!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                forgotpassDialogue.setView(forgotView);
                forgotpassDialogue.create();
                forgotpassDialogue.show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int radioID = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioID);
                    String loginAs = radioButton.getText().toString();

                    if(loginAs.equals("Login as student")){
                        StudentLogin();
                    }
                    else if(loginAs.equals("Login as teacher")){
                        TeacherLogin();
                    }
                }
                catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Please select your field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void TeacherLogin() {
        if(TextUtils.isEmpty(emailET.getText().toString().trim())){
            emailET.setError("Email must needed!");
            emailET.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString().trim()).matches()){
            emailET.setError("Please enter valid email!");
            emailET.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(passwordET.getText().toString().trim())){
            passwordET.setError("Password must needed!");
            passwordET.requestFocus();
            return;
        }

        else if(passwordET.getText().toString().trim().length()<6){
            passwordET.setError("Password must be more than 6 character!");
            passwordET.requestFocus();
            return;
        }
        else {
            dialog = new ProgressDialog(this);
            dialog.setTitle("just wait few seconds...");
            dialog.show();
            FirebaseDatabase.getInstance().getReference().child("teacher")
                    .orderByChild("teacherEmail")
                    .equalTo(emailET.getText().toString().trim())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailET.getText().toString().trim(),passwordET.getText().toString().trim())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {
                                                String key_Teacher = "teacher";
                                                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("keyTeacher",key_Teacher);
                                                editor.commit();


                                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, TeacherDashboardActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Login failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }
    private void StudentLogin() {

        if(TextUtils.isEmpty(emailET.getText().toString().trim())){
            emailET.setError("Email must needed!");
            emailET.requestFocus();
            return;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailET.getText().toString().trim()).matches()){
            emailET.setError("Please enter valid email!");
            emailET.requestFocus();
            return;
        }
        else if (TextUtils.isEmpty(passwordET.getText().toString().trim())){
            passwordET.setError("Password must needed!");
            passwordET.requestFocus();
            return;
        }
        else if(passwordET.getText().toString().trim().length()<6){
            passwordET.setError("Password must be at least 6 character!");
            passwordET.requestFocus();
            return;
        }
        else {
            dialog = new ProgressDialog(this);
            dialog.setTitle("just wait few seconds...");
            dialog.show();

            FirebaseDatabase.getInstance().getReference().child("student")
                    .orderByChild("studentEmail")
                    .equalTo(emailET.getText().toString().trim())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                FirebaseAuth.getInstance().signInWithEmailAndPassword(emailET.getText().toString().trim(),passwordET.getText().toString().trim())
                                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                            @Override
                                            public void onSuccess(AuthResult authResult) {

                                                String key_Student = "student";
                                                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("keyStudent",key_Student);
                                                editor.commit();

                                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                                startActivity(new Intent(LoginActivity.this, StudentDashboardActivity.class));
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Login failed"+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        }
    }


    private void initialization() {
        emailET = (EditText) findViewById(R.id.emailET);
        passwordET = (EditText) findViewById(R.id.passwordET);
        loginButton = (Button) findViewById(R.id.loginBtnID);
        registerTV = (TextView) findViewById(R.id.registerTVid);
        forgotTVid = (TextView) findViewById(R.id.forgotPassTVid);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
    }
}
