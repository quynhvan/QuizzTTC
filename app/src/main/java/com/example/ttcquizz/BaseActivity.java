package com.example.ttcquizz;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ttcquizz.remote.SessionManager;

public class BaseActivity  extends AppCompatActivity {
    protected SessionManager sessionManager;
    protected Runnable runnable;
    protected Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = SessionManager.getInstance(this);
    }

    protected void showConfirmDialog(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ConfirmDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, findViewById(R.id.dg_confirm));
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        ((TextView) view.findViewById(R.id.txt_message)).setText(content);
        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    protected void reCallApi() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(runnable, 4000);
            }
        };
        handler.postDelayed(runnable, 4000);
    }
}
