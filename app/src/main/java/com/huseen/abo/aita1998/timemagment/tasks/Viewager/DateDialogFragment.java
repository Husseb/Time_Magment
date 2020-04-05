package com.huseen.abo.aita1998.timemagment.tasks.Viewager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.huseen.abo.aita1998.timemagment.R;

public class DateDialogFragment extends Fragment {

   private DatePicker datePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_date_dialog, container, false);

        datePicker = view.findViewById(R.id.date_picker);

        return view;
    }

}
