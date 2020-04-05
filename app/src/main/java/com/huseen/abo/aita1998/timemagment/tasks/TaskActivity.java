package com.huseen.abo.aita1998.timemagment.tasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.adtional.YourPreference;
import com.huseen.abo.aita1998.timemagment.roomDb.task.Task;
import com.huseen.abo.aita1998.timemagment.roomDb.task.TaskViewModel;
import com.huseen.abo.aita1998.timemagment.tasks.adapter.TaskAdapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity {
    TaskViewModel taskViewModel;
    int id;

    TaskAdapter taskAdapter;
    RecyclerView recyclerview;
    BottomSheetDialog bottomSheetDialog;
    FloatingActionButton floatingActionButton;
    LinearLayout timeLinear;
    LinearLayout dateLinear;

    TextView titleEt;
    TextView descriptionEt;
    TextView createTask;
    TextView timeTextView;
    TextView datetextView;
    CardView cardTime;

    Button setBtn;
    DatePicker datePicker;
    TimePicker timePicker;
    ViewPager viewPager;
    DatePickerDialog picker;

    List<Task> taskList;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task);
        relativeLayout = findViewById(R.id.relativeLayout);
        setBackground();
        id = getIntent().getIntExtra("id", 0);
        recyclerview = findViewById(R.id.recyclerview);

        taskViewModel = new TaskViewModel(getApplication());
        createBoteSheeteDialog();
        taskList = new ArrayList<>();

        taskAdapter = new TaskAdapter();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        floatingActionButton = findViewById(R.id.onClickAddProjecctBootomSheet);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddProjecctBootomSheet();
            }
        });

        taskViewModel.getAllTask().observe(this, tasks -> {
            for (int i = 0; i < tasks.size(); i++) {
                final Task task = tasks.get(i);

                if (task.getId() == id) {
                    taskList.add(task);
                }
            }
            recyclerview.setAdapter(taskAdapter);
            taskAdapter.setList(taskList);
        });


    }


    public void onClickAddProjecctBootomSheet() {
        bottomSheetDialog.show();
    }

    private void createBoteSheeteDialog() {

        if (bottomSheetDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.bottom_add_task, null);
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(view);
            titleEt = view.findViewById(R.id.titleEt);
            descriptionEt = view.findViewById(R.id.descriptionEt);
            createTask = view.findViewById(R.id.createTask);
            cardTime = view.findViewById(R.id.cardTime);
            timeLinear = view.findViewById(R.id.timeLinear);
            dateLinear = view.findViewById(R.id.dateLinear);
            timeTextView = view.findViewById(R.id.timeTextView);
            datetextView = view.findViewById(R.id.datetextView);

            final int[] date = new int[5];

            final Calendar cldr = Calendar.getInstance();

            Calendar cal = Calendar.getInstance(Locale.ENGLISH);

            //            String minuets = DateFormat.format("m", cal).toString();
//            String hoer = DateFormat.format("h", cal).toString();
//            String year = DateFormat.format("y", cal).toString();
//            String day = DateFormat.format("d", cal).toString();
//            String month = DateFormat.format("M", cal).toString();


            dateLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar cldr = Calendar.getInstance();
                    int day = cldr.get(Calendar.DAY_OF_MONTH);
                    int month = cldr.get(Calendar.MONTH);
                    int year = cldr.get(Calendar.YEAR);
                    // date picker dialog
                    picker = new DatePickerDialog(TaskActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                    datetextView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                    date[2] = year;
                                    date[3] = (monthOfYear + 1);
                                    date[4] = dayOfMonth;


                                }
                            }, year, month, day);
                    picker.setTitle("Select Date");
                    picker.show();
                }
            });

//            picker.dat

            timeLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            final String s = String.valueOf(selectedMinute);
                            if (s.length() == 1) {
                                timeTextView.setText(selectedHour + " : 0" + selectedMinute);
                                date[0] = selectedHour;
                                date[2] = selectedMinute;
                            } else {
                                timeTextView.setText(selectedHour + " : " + selectedMinute);
                            }
                        }
                    }, hour, minute, true);//Yes 24 hour time
                    date[0] = hour;
                    date[2] = minute;
                    mTimePicker.setTitle("Select Time");
                    mTimePicker.show();

                }
            });

            createTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String descriptionString = descriptionEt.getText().toString();
                    final String titleString = titleEt.getText().toString();
                    final String time = timeTextView.getText().toString();
                    final String date11 = datetextView.getText().toString();

                    if (TextUtils.isEmpty(titleString)) {
                        titleEt.setError("Description is Empty");
                        titleEt.setFocusable(true);
                    } else if (TextUtils.isEmpty(descriptionString)) {
                        descriptionEt.setError("Description is Empty");
                        descriptionEt.setFocusable(true);
                    } else if (TextUtils.isEmpty(time)) {

                        Toast.makeText(TaskActivity.this, "Enter The Time", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(date11)) {
                        Toast.makeText(TaskActivity.this, "Enter The Date", Toast.LENGTH_SHORT).show();

                    } else {
                        for (int i = 0; i < date.length; i++) {

                            Log.e("huseen", "onClick: " + date[i]);
                        }
                        cal.set(date[2], date[3], date[4], date[0], date[1]);

                        setProjectInRoom(titleString, descriptionString, String.valueOf(cal.getTimeInMillis()));
                        bottomSheetDialog.hide();
                    }

                }
            });
        }
    }

    public void setProjectInRoom(String titleString, String descriptionString, String time) {
        taskViewModel.insert(new Task(titleString, descriptionString, time, id, 0));
    }

    private void setBackground() {
        YourPreference yourPreference = YourPreference.getInstance(getApplicationContext());

        final String screen3 = yourPreference.getScreen3();

        Drawable yourDrawable = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(screen3));
            yourDrawable = Drawable.createFromStream(inputStream, screen3);
        } catch (FileNotFoundException e) {
            //     yourDrawable = getResources().getDrawable(R.drawable.default_image);
        }
        if (!screen3.equals("")) {
            relativeLayout.setBackground(yourDrawable);

        }

    }
}
