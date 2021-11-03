package com.example.diufinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import Java_class.Answer;
import Java_class.Questions;

public class contributeAnswerActivity extends AppCompatActivity {
    EditText emailET,passET,courseET,yearET;
    Button nextBTN;
    ImageView pdfIV;
    ProgressDialog dialog;

    FirebaseUser user;
    String userID;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Answers");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute_answer);
        initial();

        nextBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyAcount();
            }
        });
    }

    private void verifyAcount() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(emailET.getText().toString(),passET.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(contributeAnswerActivity.this, "Verification successful", Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder customDialogPDFupload = new AlertDialog.Builder(contributeAnswerActivity.this);
                        LayoutInflater inflater = LayoutInflater.from(contributeAnswerActivity.this);
                        final View pdfUploadView = inflater.inflate(R.layout.pdf_upload_design,null);
                        courseET = (EditText) pdfUploadView.findViewById(R.id.questionCourseNameID);
                        yearET = (EditText) pdfUploadView.findViewById(R.id.questionYearID);
                        pdfIV = (ImageView) pdfUploadView.findViewById(R.id.questionPdfIVid);
                        pdfIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(courseET.getText().toString().isEmpty()){
                                    courseET.setError("Course name is needed");
                                    courseET.requestFocus();
                                    return;
                                }
                                if(yearET.getText().toString().isEmpty()){
                                    yearET.setError("Year is needed");
                                    yearET.requestFocus();
                                    return;
                                }else {
                                    selectPDFfile();
                                }

                            }
                        });
                        customDialogPDFupload.setView(pdfUploadView);
                        customDialogPDFupload.create();
                        customDialogPDFupload.show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(contributeAnswerActivity.this, "Verification Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initial() {
        emailET=(EditText) findViewById(R.id.answerProviderEmailETID);
        passET=(EditText) findViewById(R.id.answerProviderPassETID);
        nextBTN=(Button) findViewById(R.id.answerNextBTN);

        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
    }
    private void selectPDFfile() {
        try{
            Intent objectIntent = new Intent();
            objectIntent.setType("application/pdf*");

            objectIntent.setAction(objectIntent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(objectIntent,"Select PDF File"),1);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK
                && data!=null && data.getData()!=null){
            uploadPDFfile(data.getData());

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPDFfile(Uri data) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("File is uploading...");
        dialog.show();
        StorageReference reference = storageReference.child("Answers"+System.currentTimeMillis()+".pdf");
        reference.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uri.isComplete());
                        Uri uri1 = uri.getResult();
                        Answer answer = new Answer(courseET.getText().toString(),uri1.toString(),userID,yearET.getText().toString());
                        databaseReference.child(databaseReference.push().getKey()).setValue(answer);
                        dialog.dismiss();
                        Toast.makeText(contributeAnswerActivity.this, "Uploaded done!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(contributeAnswerActivity.this,AnswerActivity.class));
                        finish();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        dialog.setMessage("uploaded: "+(int)percent+"%");
                    }
                });
    }
}
