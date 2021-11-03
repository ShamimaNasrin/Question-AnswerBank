package Java_class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class QuestionAdapter extends FirebaseRecyclerAdapter<Questions,QuestionAdapter.MyViewHolder> {
    Context context;

    public QuestionAdapter(@NonNull FirebaseRecyclerOptions<Questions> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Questions model) {

        holder.course.setText(model.getQuestionCourse());
        holder.year.setText(model.getQuestionYear());

        holder.course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.course.getContext(), viewQuestionPdf.class);
                intent.putExtra("filename",model.getQuestionCourse());
                intent.putExtra("fileUrl",model.getQuestionFile());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.course.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_list_design,parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView course,year;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            course=(TextView) itemView.findViewById(R.id.questionCourseTVID);
            year=(TextView) itemView.findViewById(R.id.questionYearTVID);
        }
    }

}
