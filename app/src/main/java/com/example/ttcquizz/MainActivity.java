package com.example.ttcquizz;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ttcquizz.fragment.HistoryFragment;
import com.example.ttcquizz.fragment.HomeFragment;
import com.example.ttcquizz.fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.ttcquizz.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        showFragment(new HomeFragment(), true);
    }

    public void showFragment(Fragment fragment, boolean isAddToBackStack) {
        if (isAddToBackStack) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    switch (item.getItemId()) {
                        case R.id.nav_exam:
                            if(currentFragment instanceof  HomeFragment){
                                break;
                            }
                            showFragment(new HomeFragment(), true);
                            break;
                        case R.id.nav_his:
                            if(currentFragment instanceof HistoryFragment){
                                break;
                            }
                            showFragment(new HistoryFragment(), true);
                            break;
                        case R.id.nav_profile:
                            if(currentFragment instanceof  ProfileFragment){
                                break;
                            }
                            showFragment(new ProfileFragment(), true);
                            break;
                    }
                    return true;
                }
            };
}