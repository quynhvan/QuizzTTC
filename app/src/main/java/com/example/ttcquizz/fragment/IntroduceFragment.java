package com.example.ttcquizz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.ttcquizz.LoginActivity;
import com.example.ttcquizz.R;
//import com.example.ttcquizz.utilities.PreferencesMaganer;
import com.example.ttcquizz.adapter.ViewAdapter;
import com.example.ttcquizz.databinding.FragmentIntroduceBinding;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

public class IntroduceFragment extends BaseFragment {
    private FragmentIntroduceBinding binding;
    private ViewPager viewPager;
    private WormDotsIndicator wormDotsIndicator;
    private ViewAdapter viewAdapter;
    private Animation slideup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduce, container, false);
        //final PreferencesMaganer preferencesMaganer = new PreferencesMaganer(getContext());
        wormDotsIndicator = (WormDotsIndicator) binding.wormDot;
        viewPager = (ViewPager) binding.viewPager;
        viewAdapter = new ViewAdapter(getContext());
        viewPager.setAdapter(viewAdapter);
        wormDotsIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.btnLogin.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        binding.btnLogin.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        binding.btnLogin.setVisibility(View.VISIBLE);
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // btn to login
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.saveStatus(true);
                startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finish();

            }
        });
        return binding.getRoot();

    }


}