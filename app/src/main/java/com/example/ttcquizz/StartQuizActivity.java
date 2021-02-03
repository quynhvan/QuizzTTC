package com.example.ttcquizz;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.example.ttcquizz.databinding.ActivityStartQuizBinding;
import com.example.ttcquizz.model.User;

public class StartQuizActivity extends BaseActivity {

    private ActivityStartQuizBinding binding;
    private Animation fromBottom;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_quiz);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int testTime = bundle.getInt(Constants.QUIZ_TIME);
        String testName = bundle.getString(Constants.QUIZ_NAME, null);
        id = bundle.getInt(Constants.QUIZ_ID);
        binding.txtSplash.setText("Welcome to " + testName);

        User user = sessionManager.getUser();
        binding.txtHi.setText("Hi " + user.getEmail());

        binding.txtName.setText("Test Name: " + testName);
        binding.txtTime.setText("Time: " + testTime + "p");
        doAnimation();
    }

    private void doAnimation() {
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);

        int height = dimension.heightPixels;

        if (height > 2550) {
            binding.bgapp.animate().translationY(-2000).setDuration(800).setStartDelay(1000);
        }
        if (height > 2500 && height <= 2550) {
            binding.bgapp.animate().translationY(-1950).setDuration(800).setStartDelay(1000);
        }
        if (height > 2450 && height <= 2500) {
            binding.bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(1000);
        }
        if (height > 2400 && height <= 2450) {
            binding.bgapp.animate().translationY(-1850).setDuration(800).setStartDelay(1000);
        }
        if (height > 2350 && height <= 2400) {
            binding.bgapp.animate().translationY(-1800).setDuration(800).setStartDelay(1000);
        }
        if (height > 2300 && height <= 2350) {
            binding.bgapp.animate().translationY(-1750).setDuration(800).setStartDelay(1000);
        }
        if (height > 2250 && height <= 2300) {
            binding.bgapp.animate().translationY(-1700).setDuration(800).setStartDelay(1000);
        }
        if (height > 2200 && height <= 2250) {
            binding.bgapp.animate().translationY(-1650).setDuration(800).setStartDelay(1000);
        }
        if (height > 2150 && height <= 2200) {
            binding.bgapp.animate().translationY(-1600).setDuration(800).setStartDelay(1000);
        }
        if (height > 2100 && height <= 2150) {
            binding.bgapp.animate().translationY(-1550).setDuration(800).setStartDelay(1000);
        }
        if (height > 2050 && height <= 2100) {
            binding.bgapp.animate().translationY(-1500).setDuration(800).setStartDelay(1000);
        }
        if (height > 2000 && height <= 2050) {
            binding.bgapp.animate().translationY(-1450).setDuration(800).setStartDelay(1000);
        }
        if (height > 1950 && height <= 2000) {
            binding.bgapp.animate().translationY(-1400).setDuration(800).setStartDelay(1000);
        }
        if (height > 1900 && height <= 1950) {
            binding.bgapp.animate().translationY(-1350).setDuration(800).setStartDelay(1000);
        }
        if (height > 1850 && height <= 1900) {
            binding.bgapp.animate().translationY(-1300).setDuration(800).setStartDelay(1000);
        }
        if (height > 1800 && height <= 1850) {
            binding.bgapp.animate().translationY(-1250).setDuration(800).setStartDelay(1000);
        }
        if (height > 1750 && height <= 1800) {
            binding.bgapp.animate().translationY(-1200).setDuration(800).setStartDelay(1000);
        }
        if (height > 1700 && height <= 1750) {
            binding.bgapp.animate().translationY(-1150).setDuration(800).setStartDelay(1000);
        }
        if (height > 1650 && height <= 1700) {
            binding.bgapp.animate().translationY(-1100).setDuration(800).setStartDelay(1000);
        }
        if (height <= 1650) {
            binding.bgapp.animate().translationY(-1050).setDuration(800).setStartDelay(1000);
        }

        binding.clover.animate().alpha(0).setDuration(800).setStartDelay(1300);
        binding.txtSplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(1000);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        binding.txtMain.startAnimation(fromBottom);
        binding.txtInfo.startAnimation(fromBottom);
        binding.btnStart.startAnimation(fromBottom);
        binding.btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmStartDialog("Are you ready to start quiz?");
            }
        });
    }

    private void showConfirmStartDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ConfirmDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, findViewById(R.id.dg_confirm));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        ((TextView) view.findViewById(R.id.txt_message)).setText(content);
        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartQuizActivity.this, ActivityTestQuestion.class);
                intent.putExtra(Constants.QUIZ_ID, id);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        if (alertDialog.getWindow() != null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }
        alertDialog.show();
    }

}