package Java_class;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diufinalproject.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

public class NoticeAdapter extends FirebaseRecyclerAdapter<Notice,NoticeAdapter.MyViewHolder> {
    Context context;

    public NoticeAdapter(@NonNull FirebaseRecyclerOptions<Notice> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Notice model) {

        holder.subject.setText(model.getNoticeSubject());
        holder.provider.setText(model.getNoticeProviderName());
        holder.date.setText(model.getNoticeDate());

        holder.subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(holder.subject.getContext())
                        .setGravity(Gravity.CENTER)
                        .setMargin(50,0,50,0)
                        .setContentHolder(new ViewHolder(R.layout.notice_display_design))
                        .setExpanded(false)
                        .create();
                View holderView = (LinearLayout) dialog.getHolderView();

                final TextView subject = holderView.findViewById(R.id.subjectNoticeID);
                final TextView description = holderView.findViewById(R.id.noticeDetailsTVid);
                final Button done = holderView.findViewById(R.id.doneBTNid);

                subject.setText(model.getNoticeSubject());
                description.setText("Details: \n"+ model.getNoticeBody());

                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }

    @NonNull
    @Override
    public NoticeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list_design,parent,false);
        return new NoticeAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView subject,provider,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subject=(TextView) itemView.findViewById(R.id.noticeSubjectTVid);
            provider=(TextView) itemView.findViewById(R.id.noticeProviderTVID);
            date=(TextView) itemView.findViewById(R.id.noticeDateTVID);
        }
    }
}
