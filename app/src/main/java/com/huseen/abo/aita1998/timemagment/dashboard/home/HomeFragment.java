package com.huseen.abo.aita1998.timemagment.dashboard.home;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.dashboard.home.adapter.HomeAdapter;
import com.huseen.abo.aita1998.timemagment.roomDb.project.Project;
import com.huseen.abo.aita1998.timemagment.roomDb.project.ProjectViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static String TAG = HomeFragment.class.getSimpleName();
    private BottomSheetDialog bottomSheetDialog;

    private HomeAdapter homeAdapter;
    private RecyclerView recyclerview;

    private TextView titleEt, descriptionEt;
    private Button createProject;
    private FloatingActionButton floatingActionButton;

    private ProjectViewModel projectViewModel;

    List<Project> projectList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        projectViewModel = new ProjectViewModel(getActivity().getApplication());
        recyclerview = view.findViewById(R.id.recyclerview);
        homeAdapter = new HomeAdapter();
        recyclerview.setAdapter(homeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(linearLayoutManager);

        projectList = new ArrayList<>();
        Log.e(TAG, "onCreateView: "+projectList.size() );
        homeAdapter.setList(projectList);

        projectViewModel.getAllProject().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                 if (projects.size() > 0) {
                    projectList = projects;
                    homeAdapter.setList(projectList);
                }
            }
        });

        createBoteSheeteDialog();


        floatingActionButton = view.findViewById(R.id.onClickAddProjecctBootomSheet);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddProjecctBootomSheet();
            }
        });

        return view;
    }

    public void onClickAddProjecctBootomSheet() {

        bottomSheetDialog.show();


    }

    private void createBoteSheeteDialog() {

        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_add_project, null);
            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);
            titleEt = view.findViewById(R.id.titleEt);
            descriptionEt = view.findViewById(R.id.descriptionEt);
            createProject = view.findViewById(R.id.createProject);
            createProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String descriptionString = descriptionEt.getText().toString();
                    final String titleString = titleEt.getText().toString();

                    if (TextUtils.isEmpty(titleString) && titleString.length() >= 3) {
                        titleEt.setError("Title least than 3 Character ");
                        titleEt.setFocusable(true);
//                        Toast.makeText(getContext(), "The Title Must by more 3 Character", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(descriptionString)) {
                        descriptionEt.setError("Description is Empty");
                        descriptionEt.setFocusable(true);
                    } else {
                        setProjectInRoom(titleString, descriptionString);
                    }

                }
            });
        }
    }

    private void setProjectInRoom(String titleString, String descriptionString) {

        projectViewModel.insert(new Project(titleString, descriptionString, System.currentTimeMillis() + "", 0));

        bottomSheetDialog.hide();

    }
}
