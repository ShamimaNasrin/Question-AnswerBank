package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Java_class.Student;
import Java_class.Teacher;

public class registerActivity extends AppCompatActivity {

    ImageView studentImageView,teacherImageView;
    EditText studentNameET,studentPhoneET,studentEmailET,studentSectionET,studentRollET,studentPasswordET;
    EditText teacherNameET,teacherPhoneET,tacherEmailET,teacherPasswordET;
    Spinner studentClassSpinner;
    Button studentRegisterBTN,TeacherRegisterBTN;
    TextView guideTV;


    RadioGroup radioGroup;
    RadioButton radioButton;
    String classValue;
    Uri filepath;
    Bitmap bitmap;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialization();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioID = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioID);
                String registerType = radioButton.getText().toString();

                if(registerType.equals("Register as a student")){

                    studentNameET.setVisibility(View.VISIBLE);
                    studentPhoneET.setVisibility(View.VISIBLE);
                    studentEmailET.setVisibility(View.VISIBLE);
                    studentSectionET.setVisibility(View.VISIBLE);
                    studentRollET.setVisibility(View.VISIBLE);
                    studentPasswordET.setVisibility(View.VISIBLE);
                    studentRegisterBTN.setVisibility(View.VISIBLE);
                    studentClassSpinner.setVisibility(View.VISIBLE);
                    studentImageView.setVisibility(View.VISIBLE);

                    teacherNameET.setVisibility(View.GONE);
                    teacherPhoneET.setVisibility(View.GONE);
                    tacherEmailET.setVisibility(View.GONE);
                    teacherPasswordET.setVisibility(View.GONE);
                    TeacherRegisterBTN.setVisibility(View.GONE);
                    teacherImageView.setVisibility(View.GONE);
                    guideTV.setVisibility(View.GONE);
                }

                if(registerType.equals("Register as a teacher")){
                    teacherNameET.setVisibility(View.VISIBLE);
                    teacherPhoneET.setVisibility(View.VISIBLE);
                    tacherEmailET.setVisibility(View.VISIBLE);
                    teacherPasswordET.setVisibility(View.VISIBLE);
                    TeacherRegisterBTN.setVisibility(View.VISIBLE);
                    teacherImageView.setVisibility(View.VISIBLE);

                    studentNameET.setVisibility(View.GONE);
                    studentPhoneET.setVisibility(View.GONE);
                    studentEmailET.setVisibility(View.GONE);
                    studentSectionET.setVisibility(View.GONE);
                    studentRollET.setVisibility(View.GONE);
                    studentPasswordET.setVisibility(View.GONE);
                    studentRegisterBTN.setVisibility(View.GONE);
                    studentClassSpinner.setVisibility(View.GONE);
                    studentImageView.setVisibility(View.GONE);
                    guideTV.setVisibility(View.GONE);

                }
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.classes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        studentClassSpinner.setAdapter(adapter);

        studentClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                classValue = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        studentRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentRegisterMethod();
            }
        });
        studentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        teacherImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeacherchooseImage();
            }
        });
        TeacherRegisterBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacherRegisterMethod();
            }
        });



    }

    private void TeacherchooseImage() {
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(objectIntent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,2);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void teacherRegisterMethod() {
        if(filepath==null){
            Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
            teacherImageView.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(teacherNameET.getText().toString().trim())){
            teacherNameET.setError("Your name is mandatory!");
            teacherNameET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(teacherPhoneET.getText().toString().trim())){
            teacherPhoneET.setError("Please enter phone number!");
            teacherPhoneET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(tacherEmailET.getText().toString())){
            teacherPhoneET.setError("Please enter valid email!");
            teacherPhoneET.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(tacherEmailET.getText().toString().trim()).matches()){
            tacherEmailET.setError("Please enter valid email!");
            tacherEmailET.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(teacherPasswordET.getText().toString().trim())){
            teacherPasswordET.setError("Password must needed!");
            teacherPasswordET.requestFocus();
            return;
        }
        if(teacherPasswordET.getText().toString().trim().length()<6){
            teacherPasswordET.setError("Password must be at least 6 character!");
            teacherPasswordET.requestFocus();
            return;
        }else {
            dialog = new ProgressDialog(this);
            dialog.setTitle("Creating your account...");
            dialog.show();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(tacherEmailET.getText().toString(),teacherPasswordET.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference uploader = storage.getReference("teacher"+new Random().nextInt(50));
                            uploader.putFile(filepath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Teacher userTeacher = new Teacher(teacherNameET.getText().toString(),
                                                            teacherPhoneET.getText().toString(),tacherEmailET.getText().toString(),
                                                            teacherPasswordET.getText().toString(),uri.toString());
                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("teacher")
                                                            .child(FirebaseAuth.getInstance().getUid())
                                                            .setValue(userTeacher);

                                                    dialog.dismiss();
                                                    teacherPhoneET.setText("");
                                                    tacherEmailET.setText("");
                                                    teacherPasswordET.setText("");
                                                    teacherImageView.setImageResource(R.drawable.person);
                                                    Toast.makeText(registerActivity.this, "Congratulation! You've registered successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(registerActivity.this,LoginActivity.class));
                                                }
                                            });
                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                    dialog.setMessage("uploaded: "+(int)percent+"%");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(registerActivity.this, "Failed to register!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }



    }
    private void chooseImage() {
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("image/*");

            objectIntent.setAction(objectIntent.ACTION_GET_CONTENT);
            startActivityForResult(objectIntent,1);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            filepath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                studentImageView.setImageBitmap(bitmap);
            }catch (Exception e){

            }
        }else{
            if(requestCode==2 && resultCode==RESULT_OK){
                filepath = data.getData();
                try{
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    teacherImageView.setImageBitmap(bitmap);
                }catch (Exception e){

                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void studentRegisterMethod() {
        if(filepath==null){
            Toast.makeText(this, "Please choose an Image", Toast.LENGTH_SHORT).show();
            studentImageView.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentNameET.getText().toString().trim())){
            studentNameET.setError("Your name is mandatory!");
            studentNameET.requestFocus();
            return;
        }
        if(classValue.equals("Select Class")){
            Toast.makeText(this, "Please select your class", Toast.LENGTH_SHORT).show();
            studentClassSpinner.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentSectionET.getText().toString().trim())){
            studentSectionET.setError("Please enter your section!");
            studentSectionET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentRollET.getText().toString().trim())){
            studentRollET.setError("Please enter your roll!");
            studentRollET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentPhoneET.getText().toString().trim())){
            studentPhoneET.setError("Please enter your roll!");
            studentPhoneET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentEmailET.getText().toString().trim())){
            studentEmailET.setError("Please enter your roll!");
            studentEmailET.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(studentEmailET.getText().toString().trim()).matches()){
            studentEmailET.setError("Please enter valid email!");
            studentEmailET.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(studentPasswordET.getText().toString().trim())){
            studentPasswordET.setError("Please enter your roll!");
            studentPasswordET.requestFocus();
            return;
        }
        if(studentPasswordET.getText().toString().trim().length()<6){
            studentPasswordET.setError("Password must be at least 6 character!");
            studentPasswordET.requestFocus();
            return;
        }
        else{
            dialog = new ProgressDialog(this);
            dialog.setTitle("Creating your account...");
            dialog.show();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(studentEmailET.getText().toString().trim(),studentPasswordET.getText().toString().trim())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference uploader = storage.getReference("student"+new Random().nextInt(50));
                            uploader.putFile(filepath)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    Student userStudent = new Student(studentNameET.getText().toString(),classValue,studentSectionET.getText().toString(),studentRollET.getText().toString(),studentPhoneET.getText().toString(),studentEmailET.getText().toString(),studentPasswordET.getText().toString(),uri.toString());
                                                    FirebaseDatabase.getInstance().getReference()
                                                            .child("student")
                                                            .child(FirebaseAuth.getInstance().getUid())
                                                            .setValue(userStudent);

                                                    dialog.dismiss();
                                                    studentNameET.setText("");
                                                    studentRollET.setText("");
                                                    studentPhoneET.setText("");
                                                    studentSectionET.setText("");
                                                    studentEmailET.setText("");
                                                    studentPasswordET.setText("");
                                                    studentClassSpinner.setSelection(0);
                                                    studentImageView.setImageResource(R.drawable.person);
                                                    Toast.makeText(registerActivity.this, "Congratulation! You've registered successfully", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(registerActivity.this,LoginActivity.class));
                                                }
                                            });
                                        }
                                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                    float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                    dialog.setMessage("uploaded: "+(int)percent+"%");
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
                    Toast.makeText(registerActivity.this, "Failed to register!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void initialization() {

        studentNameET = (EditText) findViewById(R.id.studentNameETid);
        studentPhoneET = (EditText) findViewById(R.id.studentPhoneETid);
        studentEmailET = (EditText) findViewById(R.id.studentEmailETid);
        studentSectionET = (EditText) findViewById(R.id.studentSectionETid);
        studentRollET = (EditText) findViewById(R.id.studentRollETid);
        studentPasswordET = (EditText) findViewById(R.id.studentPassETid);
        teacherNameET = (EditText) findViewById(R.id.teacherNameETid);
        teacherPhoneET = (EditText) findViewById(R.id.teacherPhoneETid);
        tacherEmailET = (EditText) findViewById(R.id.teacherEmailETid);
        teacherPasswordET = (EditText) findViewById(R.id.teacherPassETid);
        radioGroup = (RadioGroup) findViewById(R.id.RegisterradioGroupID);
        studentRegisterBTN = (Button) findViewById(R.id.studentRegisterBTNid);
        TeacherRegisterBTN = (Button) findViewById(R.id.teacherRegisterBTNid);
        studentClassSpinner = (Spinner) findViewById(R.id.studentClassSpinnerID);
        studentImageView = (ImageView) findViewById(R.id.studentImageID);
        teacherImageView = (ImageView) findViewById(R.id.teacherImageID);
        guideTV = (TextView) findViewById(R.id.guideTVID);
    }
}
