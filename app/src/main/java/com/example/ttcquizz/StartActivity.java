package com.example.ttcquizz;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.databinding.DataBindingUtil;

import com.example.ttcquizz.databinding.ActivityStartBinding;
import com.example.ttcquizz.fragment.BeginFragment;
import com.example.ttcquizz.fragment.IntroduceFragment;

public class StartActivity extends BaseActivity {
    ActivityStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        getFragment();
        countdown(3000);
    }

    private void getFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new BeginFragment(), null).commit();
    }

    private void countdown(int total) {
        new CountDownTimer(total, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                if (sessionManager.getStatus() == true) {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                } else {
                    getSupportFragmentManager()
                            .beginTransaction().replace(R.id.container, new IntroduceFragment(), null).commit();
                }
            }
        }.start();
    }
}