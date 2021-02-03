package com.example.ttcquizz.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.Constants;
import com.example.ttcquizz.R;
import com.example.ttcquizz.StartQuizActivity;
import com.example.ttcquizz.model.Quiz;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AdapterNotify extends RecyclerView.Adapter<AdapterNotify.NotifyViewHoder> {
    List<Quiz> quizzes;
    Context context;

    public AdapterNotify(List<Quiz> quizzes, Context context) {
        this.quizzes = quizzes;
        this.context = context;
    }


    @Override
    public NotifyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).
                inflate(R.layout.itemhome, parent, false);
        NotifyViewHoder viewHoder = new NotifyViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHoder holder, int position) {
        final Quiz quiz = quizzes.get(position);
        holder.tv_Shownotify.setText("You have a new test "+ quiz.getTestName());
        String date = quiz.getTestDateFinish().substring(29);
        holder.tv_Date.setText(date);
        holder.lnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StartQuizActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.QUIZ_ID, quiz.getId());
                bundle.putInt(Constants.QUIZ_TIME, quiz.getTestTime());
                bundle.putString(Constants.QUIZ_NAME, quiz.getTestName());
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quizzes.size();
    }

    public class NotifyViewHoder extends RecyclerView.ViewHolder {
        TextView tv_Shownotify;
        ImageView img_email;
        TextView tv_Date;
        LinearLayout lnNotify;

        public NotifyViewHoder(@NonNull View itemView) {
            super(itemView);
            tv_Shownotify = itemView.findViewById(R.id.tv_show_notify);
            img_email = itemView.findViewById(R.id.img_email);
            tv_Date = itemView.findViewById(R.id.tv_deadline);
            lnNotify = itemView.findViewById(R.id.ln_notify);
        }
    }
}
