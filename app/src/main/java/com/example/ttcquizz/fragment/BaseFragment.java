package com.example.ttcquizz.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ttcquizz.remote.SessionManager;

public class BaseFragment extends Fragment {
    protected Activity mContext;
    protected SessionManager sessionManager;
    protected Runnable runnable;
    protected Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = SessionManager.getInstance(getContext());
        mContext = getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sessionManager = SessionManager.getInstance(getContext());
    }

    protected void showToast(String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    protected void reCallApi() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                mContext.runOnUiThread(()->{
                    showToast("sdkdsg");
                });
                handler.postDelayed(runnable, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
    }
}
