package com.huseen.abo.aita1998.timemagment.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.dashboard.calender.calenderFragment;
import com.huseen.abo.aita1998.timemagment.dashboard.home.HomeFragment;
import com.huseen.abo.aita1998.timemagment.dashboard.task.TaskFragment;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView bottomnavigation;
    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "home").commit();
        bottomnavigation = findViewById(R.id.bottomnavigation);
        bottomnavigation.setSelectedItemId(R.id.home);
        bottomnavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment(), "home").commit();
                        return true;
                    case R.id.calender:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new calenderFragment(), "profile").commit();
                        return true;
                    case R.id.task:
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new TaskFragment(), "friend").commit();
                        return true;
                }
                return false;
            }
        });


    }

}