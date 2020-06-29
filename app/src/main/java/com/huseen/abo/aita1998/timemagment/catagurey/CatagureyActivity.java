package com.huseen.abo.aita1998.timemagment.catagurey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.adtional.YourPreference;
import com.huseen.abo.aita1998.timemagment.catagurey.adapter.CatagureyAdapter;
import com.huseen.abo.aita1998.timemagment.roomDb.project.Project;
import com.huseen.abo.aita1998.timemagment.roomDb.project.ProjectViewModel;
import com.huseen.abo.aita1998.timemagment.roomDb.task.TaskViewModel;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CatagureyActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;

    private CatagureyAdapter catagureyAdapter;
    private RecyclerView recyclerview;

    private TextView titleEt, descriptionEt;
    private Button createProject;
    private FloatingActionButton floatingActionButton;

    private ProjectViewModel projectViewModel;

    List<Project> projectList;
    int screenType;
    Uri imageUri;
    public static final int STORAGE_REQUEST_CODE = 200;
    public static final int IMAGE_PICK_GALLARY_REQUEST_CODE = 300;
    String stoaregpermition[];
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_catagurey);

        relativeLayout = findViewById(R.id.relativeLayout);

        setBackground();
        projectViewModel = new ProjectViewModel(getApplication());
        recyclerview = findViewById(R.id.recyclerview);
        catagureyAdapter = new CatagureyAdapter();
        recyclerview.setAdapter(catagureyAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);

        projectList = new ArrayList<>();
        Log.e("TAG", "onCreateView: " + projectList.size());
        catagureyAdapter.setList(projectList, new TaskViewModel(getApplication()), CatagureyActivity.this);

        projectViewModel.getAllProject().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(List<Project> projects) {
                if (projects.size() > 0) {
                    projectList = projects;
                    catagureyAdapter.setList(projectList, new TaskViewModel(getApplication()), CatagureyActivity.this);
                }
            }
        });
        stoaregpermition = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        createBoteSheeteDialog();


        floatingActionButton = findViewById(R.id.onClickAddProjecctBootomSheet);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddProjecctBootomSheet();
            }
        });

    }

    public void onClickAddProjecctBootomSheet() {

        bottomSheetDialog.show();


    }

    private void createBoteSheeteDialog() {
        ImageView addScreen;
        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_add_project, null);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
            titleEt = view.findViewById(R.id.titleEt);
            descriptionEt = view.findViewById(R.id.descriptionEt);
            createProject = view.findViewById(R.id.createProject);
            addScreen = view.findViewById(R.id.addScreen);
            addScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showImagePicasoDialog();
                }
            });
            createProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String descriptionString = descriptionEt.getText().toString();
                    final String titleString = titleEt.getText().toString();

                    if (TextUtils.isEmpty(titleString) && titleString.length() >= 3) {
                        titleEt.setError("Title least than 3 Character ");
                        titleEt.setFocusable(true);
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


    private void showImagePicasoDialog() {
        String[] options = {"Screen1", "Screen2", "Screen3"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Screen");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                screenType = which;
                if (!cheackStoaregPermition()) {
                    requestedtoaregPermition();
                } else {
                    pickFromGallary();
                }
            }
        });
        builder.create().show();
    }

    private boolean cheackStoaregPermition() {
        boolean result = ContextCompat.checkSelfPermission(CatagureyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestedtoaregPermition() {
        ActivityCompat.requestPermissions(this, stoaregpermition, STORAGE_REQUEST_CODE);
    }


    private void pickFromGallary() {


        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLARY_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean stoaregAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (stoaregAccepted) {
                    pickFromGallary();
                } else {
                    Toast.makeText(CatagureyActivity.this, "please enable Stoareg permition", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLARY_REQUEST_CODE) {
                YourPreference yourPreference = YourPreference.getInstance(getApplicationContext());
                yourPreference.addScreen(data.getData().toString(), screenType);
            }
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    private void setBackground() {
        YourPreference yourPreference = YourPreference.getInstance(getApplicationContext());

        final String screen2 = yourPreference.getScreen2();

        Drawable yourDrawable = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(screen2));
            yourDrawable = Drawable.createFromStream(inputStream, screen2);
        } catch (FileNotFoundException e) {
            //     yourDrawable = getResources().getDrawable(R.drawable.default_image);
        }
        if (!screen2.equals("")) {
            relativeLayout.setBackground(yourDrawable);

        }

    }

}
