package com.todotask.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.todotask.R;

public abstract class BaseActivity extends AppCompatActivity {
    protected void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, tag).commit();
    }
}
