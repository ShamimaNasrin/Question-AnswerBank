package TeacherAllActivity;

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
import com.example.diufinalproject.StudentListActivity;
import com.example.diufinalproject.TeacherListActivity;
import com.example.diufinalproject.TeacherProfileActivity;
import com.example.diufinalproject.ThanksActivity;
import com.example.diufinalproject.contributeActivity;
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

import Java_class.Notice;
import Java_class.Report;
import Java_class.Student;
import Java_class.Teacher;
import StudentAllActivity.StudentDashboardActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherDashboardActivity extends AppCompatActivity {

    CircleImageView pro_picIV;
    TextView pro_status_name;

    Button noticeBTN,contactBTN,questionBTN,profileBTN,teacherBTN,studentBTN;
    EditText noticeSubjectET,noticeBodyET,reportBodyET;
    String noticeProviderName;
    String currentTime;
    Button noticeDone,reportDone;
    TextView NoticeproviderTV,noticeDateTV,reportDate;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav;
    androidx.appcompat.widget.Toolbar toolbar;

    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference userRef;
    String userID;
    private static final String Users = "teacher";

    AlertDialog.Builder customDialogPDFupload;
    AlertDialog.Builder customDialogReportupload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);
        initial();

        setSupportActionBar(toolbar);
        nav.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.primaryTextColor));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        fetchProfile();

        noticeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, noticeAllActivity.class));
            }
        });
        contactBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, EmergencyContactActivity.class));
            }
        });
        questionBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, questionListActivity.class));
            }
        });
        profileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, TeacherProfileActivity.class));
            }
        });
        teacherBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, TeacherListActivity.class));
            }
        });
        studentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, StudentListActivity.class));
            }
        });
        pro_picIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeacherDashboardActivity.this, TeacherProfileActivity.class));
            }
        });

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.post_noticeID){
                    customDialogPDFupload = new AlertDialog.Builder(TeacherDashboardActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(TeacherDashboardActivity.this);
                    final View postNotice = inflater.inflate(R.layout.post_notice_design,null);
                    noticeSubjectET = (EditText) postNotice.findViewById(R.id.notice_subject_etID);
                    noticeBodyET = (EditText) postNotice.findViewById(R.id.noticeDetailsETid);
                    noticeDone = (Button) postNotice.findViewById(R.id.noticeSubmitBTNid);
                    NoticeproviderTV = (TextView) postNotice.findViewById(R.id.noticeProvideNameID);
                    noticeDateTV = (TextView) postNotice.findViewById(R.id.noticeDateID);

                    userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            if(teacher!=null){
                                noticeProviderName = teacher.getTeacherName();
                                NoticeproviderTV.setText("Posted by: "+noticeProviderName);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Calendar calendar= Calendar.getInstance();
                    currentTime = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    noticeDateTV.setText("Posted On: "+currentTime);

                    noticeDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            postNoticeMethod();
                        }
                    });

                    customDialogPDFupload.setView(postNotice);
                    customDialogPDFupload.create();
                    customDialogPDFupload.show();

                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.teacher_helpID){
                    startActivity(new Intent(TeacherDashboardActivity.this, helpActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.teacher_answerID){
                    startActivity(new Intent(TeacherDashboardActivity.this, AnswerActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                if(menuItem.getItemId()==R.id.teacher_contributeID){
                    startActivity(new Intent(TeacherDashboardActivity.this, contributeActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.teacher_thanksID){
                    startActivity(new Intent(TeacherDashboardActivity.this, ThanksActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if(menuItem.getItemId()==R.id.teacher_AboutID){
                    startActivity(new Intent(TeacherDashboardActivity.this, AboutActivity.class));
                    drawerLayout.closeDrawer(GravityCompat.START);
                }

                if(menuItem.getItemId()==R.id.teacher_logoutID){
                    AlertDialog.Builder alertDGBL = new AlertDialog.Builder(TeacherDashboardActivity.this);
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
                            startActivity(new Intent(TeacherDashboardActivity.this, LoginActivity.class));
                            finish();
                            drawerLayout.closeDrawer(GravityCompat.START);
                        }
                    });

                    AlertDialog alertDialog = alertDGBL.create();
                    alertDialog.show();
                }

                if(menuItem.getItemId()==R.id.teacher_reportID){
                    customDialogReportupload = new AlertDialog.Builder(TeacherDashboardActivity.this);
                    LayoutInflater inflater = LayoutInflater.from(TeacherDashboardActivity.this);
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
                            }else {
                                Report report = new Report(reportBodyET.getText().toString(),userID,reportDate.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("Reports")
                                        .push().setValue(report)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                reportBodyET.setText("");
                                                Toast.makeText(TeacherDashboardActivity.this, "Report send successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(TeacherDashboardActivity.this, "Report send  Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });
    }

    private void postNoticeMethod() {
        if(noticeSubjectET.getText().toString().isEmpty()){
            noticeSubjectET.setError("Please enter the subject first");
            noticeSubjectET.requestFocus();
            return;
        }
        if(noticeBodyET.getText().toString().isEmpty()){
            noticeBodyET.setError("Plese write here in details");
            noticeBodyET.requestFocus();
            return;
        }
        else {

            Notice notice = new Notice(noticeBodyET.getText().toString(),noticeSubjectET.getText().toString(),NoticeproviderTV.getText().toString(),
                    userID,noticeDateTV.getText().toString());

            FirebaseDatabase.getInstance().getReference().child("notices")
                    .push().setValue(notice)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            noticeBodyET.setText("");
                            noticeSubjectET.setText("");
                            noticeDateTV.setText("Posted On:");
                            NoticeproviderTV.setText("Posted By:");
                            Toast.makeText(TeacherDashboardActivity.this, "Notice posted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(TeacherDashboardActivity.this, "Notice posted Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void fetchProfile() {
        userRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher = snapshot.getValue(Teacher.class);
                if(teacher!=null){
                    String profileName = teacher.getTeacherName();
                    String photoLink = snapshot.child("teacherImage").getValue().toString();
                    Picasso.get().load(photoLink).into(pro_picIV);
                    pro_status_name.setText(profileName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initial() {
        noticeBTN = (Button) findViewById(R.id.teacherNoticeBTNid);
        contactBTN = (Button) findViewById(R.id.teacherContactBTNid);
        questionBTN = (Button) findViewById(R.id.teacherQuestionBTNid);
        profileBTN = (Button) findViewById(R.id.teacherProfileBTNid);
        teacherBTN = (Button) findViewById(R.id.teacherMembersBTNid);
        studentBTN = (Button) findViewById(R.id.teacherStudentListid);
        pro_picIV = (CircleImageView) findViewById(R.id.pro_picCIVid);
        pro_status_name = (TextView) findViewById(R.id.pro_statusTVid);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerID);
        nav = (NavigationView) findViewById(R.id.nav_manuID);
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.teacher_toolbarID);

        database = FirebaseDatabase.getInstance();
        userRef = database.getReference(Users);
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();

    }
}
