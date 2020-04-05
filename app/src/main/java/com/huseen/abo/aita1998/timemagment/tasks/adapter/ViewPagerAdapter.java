package com.huseen.abo.aita1998.timemagment.tasks.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.huseen.abo.aita1998.timemagment.tasks.Viewager.DateDialogFragment;
import com.huseen.abo.aita1998.timemagment.tasks.Viewager.TimeDialogFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context _context;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        _context = context;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = new DateDialogFragment();
                break;
            case 1:
                f = new TimeDialogFragment();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }

}