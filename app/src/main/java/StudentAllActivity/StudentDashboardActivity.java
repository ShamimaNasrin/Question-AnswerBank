package StudentAllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diufinalproject.AboutActivity;
import com.example.diufinalproject.AnswerActivity;
import com.example.diufinalproject.EmergencyContactActivity;
import com.example.diufinalproject.LoginActivity;
import com.example.diufinalproject.R;
import com.example.diufinalproject.StudentProfileActivity;
import com.example.diufinalproject.TeacherListActivity;
import com.example.diufinalproject.ThanksActivity;
import com.example.diufinalproject.contributeActivity;
import com.example.diufinalproject.contributeAnswerActivity;
import com.example.diufinalproject.helpActivity;
import com.example.diufinalproject.noticeAllActivity;
import com.example.diufinalproject.questionListActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Calendar;

import Java_class.Report;
import Java_class.Student;
import Java_class.Teacher;
import TeacherAllActivity.TeacherDashboardActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDashboardActivity extends AppCompatActivity {

    CircleImageView pro_picIV;
    TextView pro_status_name;
    Button profileBTN,questionBTN,answerBTN,teacherBTN,noticeBTN,contactBTN,reportDone;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    androidx.appcompat.widget.Toolbar toolbar;

    EditText reportBodyET;
    TextView reportDate;
    String currentTime;

    AlertDialog.Builder customDialogReportupload;

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String userID;
    private static final String Users = "student";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        initialization();

        setSupportActionBar(toolbar);
        nav.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryTextColor));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fetchProfile();

        profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, StudentProfileActivity.class));
            }
        });
        questionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, questionListActivity.class));
            }
        });
        teacherBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, TeacherListActivity.class));
            }
        });
        contactBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, EmergencyContactActivity.class));
            }
        });
        answerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, AnswerActivity.class));
            }
        });
        noticeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, noticeAllActivity.class));
            }
        });
        pro_picIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StudentDashboardActivity.this, StudentProfileActivity.class));
            }
        });


        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.thanksID){
                    startActivity(new Intent(StudentDashboardActivity.this,ThanksActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.AboutID){
                    startActivity(new Intent(StudentDashboardActivity.this, AboutActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.answerID){
                    startActivity(new Intent(StudentDashboardActivity.this, contributeAnswerActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.reportID){
                    customDialogReportupload = new AlertDialog.Builder(StudentDashboardActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(StudentDashboardActivity.this);
                    final View postReport = inflater.inflate(R.layout.post_report_design,null);

                    reportBodyET = (EditText) postReport.findViewById(R.id.reportDetailsETid);
                    reportDate = (TextView) postReport.findViewById(R.id.reportDateID);
                    reportDone = (Button) postReport.findViewById(R.id.reportSubmitBTNid);
                    Calendar calendar= Calendar.getInstance();
                    currentTime = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    reportDate.setText("Report On: "+currentTime);

                    reportDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(reportBodyET.getText().toString().isEmpty()){
                                reportBodyET.setError("Please type here in details");
                                reportBodyET.requestFocus();
                                return;
                            }
                            else {
                                Report report = new Report(reportBodyET.getText().toString(),userID,reportDate.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("Reports")
                                        .push().setValue(report)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                reportBodyET.setText("");
                                                Toast.makeText(StudentDashboardActivity.this, "Report send successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(StudentDashboardActivity.this, "Report send  Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });

                    customDialogReportupload.setView(postReport);
                    customDialogReportupload.create();
                    customDialogReportupload.show();

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.helpID){
                    startActivity(new Intent(StudentDashboardActivity.this, helpActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.logoutID){

                    AlertDialog.Builder alertDGBL = new AlertDialog.Builder(StudentDashboardActivity.this);
                    alertDGBL.setTitle("LogOut");
                    alertDGBL.setMessage("Do you want to logout?");
                    alertDGBL.setIcon(R.drawable.logout);
                    alertDGBL.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.commit();

                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(StudentDashboardActivity.this, LoginActivity.class));
                            finish();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    });

                    AlertDialog alertDialog = alertDGBL.create();
                    alertDialog.show();

                }
                return true;
            }
        });
    }
    private void fetchProfile() {
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Student student = snapshot.getValue(Student.class);
                if(student!=null){
                    String profileName = student.getStudentName();
                    String photoLink = snapshot.child("profileImage").getValue().toString();
                    Picasso.get().load(photoLink).into(pro_picIV);
                    pro_status_name.setText(profileName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialization() {
        profileBTN = (Button) findViewById(R.id.studentProfileBTNid);
        questionBTN = (Button) findViewById(R.id.studentQuestionBTNid);
        answerBTN = (Button) findViewById(R.id.studentAnswerBTNid);
        teacherBTN = (Button) findViewById(R.id.studentTeacherBTNid);
        noticeBTN = (Button) findViewById(R.id.studentNoticeBTNid);
        contactBTN = (Button) findViewById(R.id.studentContactBTNid);
        pro_picIV = (CircleImageView) findViewById(R.id.pro_picCIVid);
        pro_status_name = (TextView) findViewById(R.id.pro_statusTVid);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerID);
        nav = (NavigationView) findViewById(R.id.nav_manuID);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarID);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Users);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
    }
}
