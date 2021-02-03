package com.example.ttcquizz.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ttcquizz.LoginActivity;
import com.example.ttcquizz.R;
import com.example.ttcquizz.databinding.FragmentProfileBinding;
import com.example.ttcquizz.model.User;
import com.example.ttcquizz.remote.RetrofitClientInstance;
import com.example.ttcquizz.request.ChangePasswordRequest;
import com.example.ttcquizz.response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding binding;
    private boolean flagO = true, flagN = true, flagC = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        User user = sessionManager.getUser();
        binding.tvUsername.setText(user.getEmail());

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLogout("Are you sure you want to logout?");
            }
        });

        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePassword();
            }
        });

        return binding.getRoot();
    }

    private boolean logout() {
        sessionManager.getInstance(getActivity()).deleteToken();
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    }

    private void showDialogLogout(String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ConfirmDialog);
        View view = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        ((TextView) view.findViewById(R.id.txt_message)).setText(content);
        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(logout()) {
                    alertDialog.dismiss();
                }
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

    private void showChangePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.ConfirmDialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_pasword, getActivity().findViewById(R.id.dg_change_password));
        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        ImageView btnO = view.findViewById(R.id.btn_show_hide_old);
        ImageView btnN = view.findViewById(R.id.btn_show_hide_new);
        ImageView btnC = view.findViewById(R.id.btn_show_hide_confirm);

        btnO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShowHide(flagO, view.findViewById(R.id.btn_show_hide_old), view.findViewById(R.id.txt_old_password));
                flagO = !flagO;
            }
        });

        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShowHide(flagN, view.findViewById(R.id.btn_show_hide_new), view.findViewById(R.id.txt_new_password));
                flagN = !flagN;
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkShowHide(flagC, view.findViewById(R.id.btn_show_hide_confirm), view.findViewById(R.id.txt_confirm_password));
                flagC = !flagC;
            }
        });


        view.findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txtO = view.findViewById(R.id.txt_old_password);
                EditText txtN = view.findViewById(R.id.txt_new_password);
                EditText txtC = view.findViewById(R.id.txt_confirm_password);
                String oldP = txtO.getText().toString();
                String newP = txtN.getText().toString();
                String confirmP = txtC.getText().toString();

                if (isValidate(oldP, newP, confirmP, view) && isConfirm(newP, confirmP, view)) {
                    changePassword(oldP, newP, view);
                }

            }
        });
        view.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
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

    private void checkShowHide(boolean flag, ImageView img, EditText txt) {
        if (!flag) {
            txt.setTransformationMethod(PasswordTransformationMethod.getInstance());
            img.setImageResource(R.drawable.ic_hide);
        } else {
            txt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            img.setImageResource(R.drawable.ic_show);
        }
    }

    private boolean isValidate(String oldP, String newP, String confirmP, View view) {
        TextView o = view.findViewById(R.id.txtOldRequired);
        TextView n = view.findViewById(R.id.txtNewRequired);
        TextView c = view.findViewById(R.id.txtConfirmRequired);
        o.setText("Old Password is required");
        n.setText("New Password is required");
        c.setText("Confirm Password is required");

        if (TextUtils.isEmpty(oldP) && TextUtils.isEmpty(newP) && TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.VISIBLE);
            n.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(oldP) && TextUtils.isEmpty(newP) && !TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.VISIBLE);
            n.setVisibility(View.VISIBLE);
            c.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(oldP) && !TextUtils.isEmpty(newP) && TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.VISIBLE);
            n.setVisibility(View.INVISIBLE);
            c.setVisibility(View.VISIBLE);
            return false;
        }

        if (!TextUtils.isEmpty(oldP) && TextUtils.isEmpty(newP) && TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.INVISIBLE);
            n.setVisibility(View.VISIBLE);
            c.setVisibility(View.VISIBLE);
            return false;
        }

        if (!TextUtils.isEmpty(oldP) && !TextUtils.isEmpty(newP) && TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.INVISIBLE);
            n.setVisibility(View.INVISIBLE);
            c.setVisibility(View.VISIBLE);
            return false;
        }

        if (!TextUtils.isEmpty(oldP) && TextUtils.isEmpty(newP) && !TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.INVISIBLE);
            n.setVisibility(View.VISIBLE);
            c.setVisibility(View.INVISIBLE);
            return false;
        }

        if (TextUtils.isEmpty(oldP) && !TextUtils.isEmpty(newP) && !TextUtils.isEmpty(confirmP)) {
            o.setVisibility(View.VISIBLE);
            n.setVisibility(View.INVISIBLE);
            c.setVisibility(View.INVISIBLE);
            return false;
        }

        o.setVisibility(View.INVISIBLE);
        n.setVisibility(View.INVISIBLE);
        c.setVisibility(View.INVISIBLE);

        return true;
    }

    private boolean isConfirm(String newP, String confirmP, View view) {
        TextView txtC = view.findViewById(R.id.txtConfirmRequired);
        txtC.setText("Password and Confirm password must be match.");
        if (newP.equals(confirmP)) {
            txtC.setVisibility(View.INVISIBLE);
            return true;
        }
        txtC.setVisibility(View.VISIBLE);
        return false;
    }

    private void changePassword(String oldP, String newP, View view) {
        view.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        Call<BaseResponse> call = RetrofitClientInstance.getMyService(mContext).changePassword(new ChangePasswordRequest(oldP, newP));
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    BaseResponse res = response.body();
                    if (res.isStatus()) {
                        logout();
                    } else {
                        ((TextView) view.findViewById(R.id.txtFailChange)).setText(response.body().getMessage());
                        view.findViewById(R.id.txtFailChange).setVisibility(View.VISIBLE);
                    }

                } else {
                    ((TextView) view.findViewById(R.id.txtFailChange)).setText("Something wrong");
                    view.findViewById(R.id.txtFailChange).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                view.findViewById(R.id.txtFailChange).setVisibility(View.VISIBLE);
                view.findViewById(R.id.progress_bar).setVisibility(View.GONE);
            }
        });
    }

}