package com.example.ttcquizz;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.example.ttcquizz.databinding.ActivityLoginBinding;
import com.example.ttcquizz.model.User;
import com.example.ttcquizz.remote.RetrofitClientInstance;
import com.example.ttcquizz.request.LoginRequest;
import com.example.ttcquizz.response.BaseResponse;
import com.example.ttcquizz.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding binding;
    private boolean flag = true;
    private String email, password;
    private Dialog dialog;

    @Override
    protected void onStart() {
        super.onStart();
        if (sessionManager.getDeviceToken() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = binding.txtEmail.getText().toString();
                password = binding.txtPass.getText().toString();

                if (isValidate(email.toLowerCase(), password)) {
                    login();
                }
            }
        });

        binding.btnShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShowHide(flag);
                flag = !flag;
            }

        });
    }

    private void checkShowHide(boolean flag) {
        if (!flag) {
            binding.txtPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.btnShowHide.setImageResource(R.drawable.ic_hide);
        } else {
            binding.txtPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            binding.btnShowHide.setImageResource(R.drawable.ic_show);
        }
    }

    private boolean isValidate(String email, String password) {
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            binding.txtMailRequired.setVisibility(View.VISIBLE);
            binding.txtPassRequired.setVisibility(View.VISIBLE);
            return false;
        }

        if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
            binding.txtMailRequired.setVisibility(View.INVISIBLE);
            binding.txtPassRequired.setVisibility(View.VISIBLE);
            return false;
        }

        if (!TextUtils.isEmpty(password) && TextUtils.isEmpty(email)) {
            binding.txtPassRequired.setVisibility(View.INVISIBLE);
            binding.txtMailRequired.setVisibility(View.VISIBLE);
            return false;
        }

        binding.txtMailRequired.setVisibility(View.INVISIBLE);
        binding.txtPassRequired.setVisibility(View.INVISIBLE);
        return true;
    }

    public void login() {
        binding.progressBar.setVisibility(View.VISIBLE);
        LoginRequest request = new LoginRequest();
        request.setEmail(email);
        request.setPassword(password);

        Call<LoginResponse> call = RetrofitClientInstance.getMyService(this).checkLogin(request);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    BaseResponse res = response.body();
                    if (res.isStatus()) {
                        LoginResponse user = response.body();
                        User mUser = new User();
                        mUser.setId(user.getData().getId());
                        mUser.setEmail(user.getData().getEmail());
                        mUser.setAccessToken(user.getData().getAccessToken());
                        mUser.setPassword(password);
                        String token = response.body().getData().getAccessToken();
                        sessionManager.saveDevLiceToken(token);
                        sessionManager.saveUser(mUser);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        String message = response.body().getMessage();
                        binding.txtLoginFail.setText(message);
                        binding.txtLoginFail.setVisibility(View.VISIBLE);
                    }
                } else {
                    String message = "Login invalid";
                    binding.txtLoginFail.setText(message);
                    binding.txtLoginFail.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                binding.txtLoginFail.setText(t.getMessage());
                binding.txtLoginFail.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}