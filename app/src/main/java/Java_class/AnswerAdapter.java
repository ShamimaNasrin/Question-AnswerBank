package Java_class;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diufinalproject.R;
import com.example.diufinalproject.viewQuestionPdf;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AnswerAdapter extends FirebaseRecyclerAdapter<Answer,AnswerAdapter.MyViewHolder> {
    Context context;

    public AnswerAdapter(@NonNull FirebaseRecyclerOptions<Answer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Answer model) {
        holder.answerCourseTV.setText(model.getAnswerCourseName());
        holder.answerYearTV.setText("Year: "+model.getAnswerYear());

        holder.answerCourseTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.answerCourseTV.getContext(), viewQuestionPdf.class);
                intent.putExtra("filename",model.getAnswerCourseName());
                intent.putExtra("fileUrl",model.getAnswerFile());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.answerCourseTV.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public AnswerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_list_design,parent,false);
        return new AnswerAdapter.MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView answerCourseTV,answerYearTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            answerCourseTV=(TextView) itemView.findViewById(R.id.answerCourseTVID);
            answerYearTV=(TextView) itemView.findViewById(R.id.answerYearTVID);
        }
    }
}
