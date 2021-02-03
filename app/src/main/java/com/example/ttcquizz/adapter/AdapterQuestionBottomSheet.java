package com.example.ttcquizz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.R;
import com.example.ttcquizz.model.ItemBottomSheet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdapterQuestionBottomSheet extends RecyclerView.Adapter<AdapterQuestionBottomSheet.MyViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    private Integer numberQuestion;
    private onClickItemBottomSheet onClickItemBottomSheet;
    private Set<Integer> set = new HashSet<>();
    private static final String TAG = "TAG";
    private ArrayList<Integer> lists = new ArrayList<>();
    private List<ItemBottomSheet> listItemBottomsheet = new ArrayList<>();

//    public AdapterQuestionBottomSheet(Context context, Integer numberQuestion, onClickItemBottomSheet onClickItemBottomSheet) {
//        this.context = context;
//        this.numberQuestion = numberQuestion;
//        this.onClickItemBottomSheet = onClickItemBottomSheet;
//        inflater = LayoutInflater.from(context);
//        notifyDataSetChanged();
//    }

    public AdapterQuestionBottomSheet(Context context, List<ItemBottomSheet> listItem, onClickItemBottomSheet onClickItemBottomSheet) {
        this.context = context;
        this.listItemBottomsheet = listItem;
        this.onClickItemBottomSheet = onClickItemBottomSheet;
        inflater = LayoutInflater.from(context);
    }

    public void setPositionAnswer(int position) {
        listItemBottomsheet.get(position).setAnswer(true);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_bottom_sheet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvSoThuTu.setText("" + (position + 1));
        ItemBottomSheet itemBottomSheet = listItemBottomsheet.get(position);
            if (!itemBottomSheet.isAnswer()) {
                holder.Itembottomsheet.setBackgroundResource(R.color.white);
            }else{
                holder.Itembottomsheet.setBackgroundResource(R.color.orange);
            }
    }

    @Override
    public int getItemCount() {
        return listItemBottomsheet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSoThuTu;
        private ConstraintLayout Itembottomsheet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSoThuTu = itemView.findViewById(R.id.tv_so_thu_tu);
            Itembottomsheet = itemView.findViewById(R.id.Itembottomsheet);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickItemBottomSheet.onClickItemBottomSheet(getAdapterPosition());
                }
            });
        }
    }

    public interface onClickItemBottomSheet {
        void onClickItemBottomSheet(int position);
    }
}
