package com.example.ttcquizz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ttcquizz.R;
import com.example.ttcquizz.adapter.AdapterNotify;
import com.example.ttcquizz.databinding.FragmentHomeBinding;
import com.example.ttcquizz.model.DataQuiz;
import com.example.ttcquizz.model.Quiz;
import com.example.ttcquizz.remote.RetrofitClientInstance;
import com.example.ttcquizz.response.QuizResponse;
import com.example.ttcquizz.response.ResultResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment {
    private AdapterNotify adapterNotify;
    private List<Quiz> notify;
    private FragmentHomeBinding binding;
    private List<Integer> listId;
    private List<String> listName;
    private List<String> listDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        notify  = new ArrayList<>();
        callApiExam();
        return binding.getRoot();
    }


    private void initData(int id, String name, String date) {
        notify.add(new Quiz(id, name, date));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        adapterNotify = new AdapterNotify(notify, getContext());
        binding.recyclerviewHome.setAdapter(adapterNotify);
        binding.recyclerviewHome.setLayoutManager(layoutManager);
        adapterNotify.notifyDataSetChanged();
    }

    private void callApiExam(){
        listId = new ArrayList<>();
        listName = new ArrayList<>();
        listDate = new ArrayList<>();
        Call<QuizResponse> call = RetrofitClientInstance.getMyService(getActivity()).getQuiz();
        call.enqueue(new Callback<QuizResponse>() {
            @Override
            public void onResponse(Call<QuizResponse> call, Response<QuizResponse> response) {
                if(response.isSuccessful()){
                    List<DataQuiz> result = (List<DataQuiz>) response.body().getData();
                    for(int i = 0; i < result.size(); i++) {
                        listId.add(result.get(i).getId());
                        listName.add(result.get(i).getTestName());
                        listDate.add(result.get(i).getTestDateFinish());
                    }
                    getDataNotification(listId, listName, listDate);
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

    private void getDataNotification(List<Integer> listId, List<String> listName, List<String> listDate) {
        if(listId.size() != 0) {
            for(int i = 0; i < listId.size(); i++) {
                int id = (int) listId.get(i);
                String name = listName.get(i);
                String date = listDate.get(i);
                Call<ResultResponse> call = RetrofitClientInstance.getMyService(getActivity()).getResult(id);
                call.enqueue(new Callback<ResultResponse>() {
                    @Override
                    public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                        if(response.isSuccessful()) {
                            ResultResponse res = response.body();
                            if(!res.isStatus()) {
                                initData(id, name, date);
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
}