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
import com.example.diufinalproject.viewQuestionPdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends FirebaseRecyclerAdapter<Teacher,TeacherAdapter.MyViewHolder> {
    Context context;

    public TeacherAdapter(@NonNull FirebaseRecyclerOptions<Teacher> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Teacher model) {
        holder.name.setText(model.getTeacherName());
        holder.number.setText(model.getTeacherPhone());
        holder.email.setText(model.getTeacherEmail());
        Picasso.get().load(model.getTeacherImage()).into(holder.teacherIV);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(holder.name.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.teacher_display_design))
                        .setExpanded(false)
                        .create();
                View holderView = (LinearLayout) dialog.getHolderView();

                final TextView nameTV = holderView.findViewById(R.id.teacherNameDisplayID);
                final TextView emailTV = holderView.findViewById(R.id.teacherEmailDisplayID);
                final TextView phoneTV = holderView.findViewById(R.id.teacherPhoneDisplayID);
                final ImageView pro_picIV = holderView.findViewById(R.id.teacherPicDisplayID);
                final Button callBTN = holderView.findViewById(R.id.callTeacherBTNid);

                nameTV.setText(model.getTeacherName());
                emailTV.setText("Email: "+model.getTeacherEmail());
                phoneTV.setText("Phone: "+model.getTeacherPhone());
                Picasso.get().load(model.getTeacherImage()).into(pro_picIV);

                callBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+model.getTeacherPhone()));

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
    public TeacherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_list_design,parent,false);
        return new TeacherAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,number,email;
        ImageView teacherIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.teacherNameTVID);
            number=(TextView) itemView.findViewById(R.id.teacherNumberTVID);
            email=(TextView) itemView.findViewById(R.id.teacherEmailTVID);
            teacherIV=(CircleImageView) itemView.findViewById(R.id.teacherIVid);
        }
    }
}
