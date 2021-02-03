package com.example.ttcquizz.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.R;
import com.example.ttcquizz.adapter.AdapterHistory;
import com.example.ttcquizz.databinding.FragmenthistoryBinding;
import com.example.ttcquizz.model.DataQuiz;
import com.example.ttcquizz.model.DataResult;
import com.example.ttcquizz.model.Quiz;
import com.example.ttcquizz.remote.RetrofitClientInstance;
import com.example.ttcquizz.response.QuizResponse;
import com.example.ttcquizz.response.ResultResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends BaseFragment {
    private FragmenthistoryBinding binding;
    private AdapterHistory adapterHistory;
    private List<Quiz> histories;
    private List<Integer> listId;
    private List<String> listName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmenthistory, container, false);
        histories = new ArrayList<>();
        callApiExam();
        reCallApi();
        return binding.getRoot();
    }

    private void initData(int id, String name, DataResult data) {
        histories.add(new Quiz(id, name, data));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        adapterHistory = new AdapterHistory(histories, getContext());
        binding.recyclerviewHistory.setAdapter(adapterHistory);
        binding.recyclerviewHistory.setLayoutManager(layoutManager);
        adapterHistory.notifyDataSetChanged();
    }

    private void getDataNotification(List<Integer> listId, List<String> listName) {
        if(listId.size() != 0) {
            histories.clear();
            for(int i = 0; i < listId.size(); i++) {
                int id = (int) listId.get(i);
                String name = listName.get(i);
                Call<ResultResponse> call = RetrofitClientInstance.getMyService(getActivity()).getResult(id);
                call.enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if(response.isSuccessful()) {
                            ResultResponse res = response.body();
                            if(res.isStatus()) {
                                initData(id, name, res.getData());
                            }
                        } else {
                            showToast(response.message());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResultResponse> call, Throwable t) {
                        showToast("Something wrong!");
                    }
                });
            }
        }
    }

    private void callApiExam(){
        mContext.runOnUiThread(()->{
            showToast("sdkdsg");
        });
        listId = new ArrayList<>();
        listName = new ArrayList<>();
        Call<QuizResponse> call = RetrofitClientInstance.getMyService(getActivity()).getQuiz();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if(response.isSuccessful()){
                    List<DataQuiz> result = (List<DataQuiz>) response.body().getData();
                    for(int i = 0; i < result.size(); i++) {
                        listId.add(result.get(i).getId());
                        listName.add(result.get(i).getTestName());
                    }
                    getDataNotification(listId, listName);
                } else {
                    showToast(response.message());
                }
            }
            @Override
            public void onFailure(Call<QuizResponse> call, Throwable t) {
                showToast("Something wrong!");
            }
        });
    }

    protected void reCallApi() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                callApiExam();
                handler.postDelayed(runnable, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
    }
}