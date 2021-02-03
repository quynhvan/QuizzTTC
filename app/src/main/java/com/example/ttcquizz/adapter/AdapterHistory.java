package com.example.ttcquizz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.R;
import com.example.ttcquizz.model.Quiz;

import java.util.List;

public class AdapterHistory extends RecyclerView.Adapter<AdapterHistory.HistoriesViewHoder> {
    private List<Quiz> histories;
    private Context context;

    public AdapterHistory(List<Quiz> histories, Context context) {
        this.histories = histories;
        this.context = context;
    }

    @Override
    public HistoriesViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.itemhistory, parent, false);
        HistoriesViewHoder viewHoder = new HistoriesViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriesViewHoder holder, int position) {
        final Quiz history = histories.get(position);
        holder.testName.setText(history.getTestName());
        if(history.getResult().getScoreEssay() == null) {
            holder.scoreTL.setText("Waiting");
        } else {
            holder.scoreTL.setText(history.getResult().getScoreEssay());
        }
        holder.scoreTN.setText(history.getResult().getScoreMultipleChoice());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }


    public class HistoriesViewHoder extends RecyclerView.ViewHolder {
        TextView testName;
        TextView scoreTL;
        TextView scoreTN;

        public HistoriesViewHoder(@NonNull View itemView) {
            super(itemView);
            testName = itemView.findViewById(R.id.tvTestName);
            scoreTL = itemView.findViewById(R.id.tv_score_TL);
            scoreTN = itemView.findViewById(R.id.tv_score_TN);
        }
    }
}