package com.huseen.abo.aita1998.timemagment.dashboard.calender;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huseen.abo.aita1998.timemagment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class calenderFragment extends Fragment {


    public calenderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calender, container, false);

        return view;
    }

}
