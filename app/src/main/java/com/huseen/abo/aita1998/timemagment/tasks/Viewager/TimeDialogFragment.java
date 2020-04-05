package com.huseen.abo.aita1998.timemagment.tasks.Viewager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.huseen.abo.aita1998.timemagment.R;

public class TimeDialogFragment extends Fragment {

   private TimePicker timePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_time_dialog, container, false);
        timePicker = view.findViewById(R.id.time_picker);
        return view;
    }


}
