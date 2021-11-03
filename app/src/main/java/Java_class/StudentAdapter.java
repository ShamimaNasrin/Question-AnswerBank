package Java_class;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diufinalproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends FirebaseRecyclerAdapter<Student,StudentAdapter.MyViewHolder> {
    Context context;

    public StudentAdapter(@NonNull FirebaseRecyclerOptions<Student> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Student model) {
        holder.name.setText(model.getStudentName());
        holder.s_class.setText("Class: "+model.getStudentClass());
        holder.section.setText("Section: "+model.getStudentSection());
        holder.roll.setText("Roll: "+model.getStudentRoll());
        Picasso.get().load(model.getProfileImage()).into(holder.studentIV);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(holder.name.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.student_display_design))
                        .setExpanded(false)
                        .create();
                View holderView = (LinearLayout) dialog.getHolderView();

                final TextView nameTV = holderView.findViewById(R.id.studentNameDisplayID);
                final TextView s_class_display = holderView.findViewById(R.id.studentClassDisplayID);
                final TextView sectionTV = holderView.findViewById(R.id.studentSectionDisplayID);
                final TextView rollTV = holderView.findViewById(R.id.studentRollDisplayID);
                final TextView emailTV = holderView.findViewById(R.id.studentEmailDisplayID);
                final TextView phoneTV = holderView.findViewById(R.id.studentPhoneDisplayID);
                final ImageView pro_picIV = holderView.findViewById(R.id.studentPicDisplayID);
                final Button callBTN = holderView.findViewById(R.id.callStudentBTNid);

                nameTV.setText(model.getStudentName());
                s_class_display.setText("Class: "+model.getStudentClass());
                sectionTV.setText("Section: "+model.getStudentSection());
                rollTV.setText("Roll: "+model.getStudentRoll());
                emailTV.setText("Email: "+model.getStudentEmail());
                phoneTV.setText("Phone: "+model.getStudentPhone());
                Picasso.get().load(model.getProfileImage()).into(pro_picIV);

                callBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+model.getStudentPhone()));

                        if(ActivityCompat.checkSelfPermission(holder.name.getContext(), Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(holder.name.getContext(), "Please allow permission to call!", Toast.LENGTH_SHORT).show();
                            ActivityCompat.requestPermissions((Activity) holder.name.getContext(),new String[]{Manifest.permission.CALL_PHONE},1);
                        }else {
                            holder.name.getContext().startActivity(intent);
                        }
                    }
                });

                dialog.show();
            }
        });

    }

    @NonNull
    @Override
    public StudentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_design,parent,false);
        return new StudentAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,s_class,section,roll;
        ImageView studentIV;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name =(TextView) itemView.findViewById(R.id.studentNameTVID);
            s_class =(TextView) itemView.findViewById(R.id.studentClassTVID);
            section =(TextView) itemView.findViewById(R.id.studentSectionTVID);
            roll =(TextView) itemView.findViewById(R.id.studentRollTVID);
            studentIV =(CircleImageView) itemView.findViewById(R.id.studentIVid);

        }
    }
}
