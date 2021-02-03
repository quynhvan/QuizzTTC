package com.example.ttcquizz.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.ttcquizz.R;
import com.example.ttcquizz.databinding.FragmentBeginBinding;


public class BeginFragment extends Fragment {
    FragmentBeginBinding binding;
    private int totaltime;
    private int runtime;
    private Animation animSlideup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_begin,
                container, false);
        animSlideup = AnimationUtils.loadAnimation(getContext(), R.anim.slideup);
        // set filtAnimation listener
        binding.tVSlogan.startAnimation(animSlideup);
        return binding.getRoot();
    }
    public static BeginFragment newInstance() {
        return new BeginFragment();
    }
}