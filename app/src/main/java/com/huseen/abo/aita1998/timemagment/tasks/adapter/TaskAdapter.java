package com.huseen.abo.aita1998.timemagment.tasks.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.roomDb.task.Task;
import com.huseen.abo.aita1998.timemagment.roomDb.task.TaskViewModel;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyHolder> {
    Context context;
    private List<Task> taskList;
    private TaskViewModel taskViewModel;

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        final Task task = taskList.get(position);

        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.parseLong(task.getTime()));
        String minuets = DateFormat.format("m", cal).toString();
        String hoer = DateFormat.format("h", cal).toString();
        String year = DateFormat.format("y", cal).toString();
        String day = DateFormat.format("d", cal).toString();
        String month = DateFormat.format("M", cal).toString();

        holder.textView.setText(day + "/" + month + "/" + year);

        holder.describtionProjectTv.setText(task.getDetails());

        holder.nameProjectTv.setText(task.getName());
        if (task.getSelect() == 1) {
            holder.selectGray.setVisibility(View.GONE);
            holder.selectBlueIv.setVisibility(View.VISIBLE);
        } else {
            holder.selectGray.setVisibility(View.VISIBLE);
            holder.selectBlueIv.setVisibility(View.GONE);
        }

        holder.selectGray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setSelect(1);
                taskViewModel.update(task);
                holder.selectGray.setVisibility(View.GONE);
                holder.selectBlueIv.setVisibility(View.VISIBLE);

            }
        });
        holder.selectBlueIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setSelect(0);
                taskViewModel.update(task);
                 holder.selectBlueIv.setVisibility(View.GONE);
                holder.selectGray.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public void setTaskList(TaskViewModel taskViewModel) {
        this.taskViewModel = taskViewModel;
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView selectGray;
        ImageView selectBlueIv;
        TextView nameProjectTv;
        TextView describtionProjectTv;
        TextView textView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            selectGray = itemView.findViewById(R.id.selectGrayIv);
            selectBlueIv = itemView.findViewById(R.id.selectBlueIv);
            describtionProjectTv = itemView.findViewById(R.id.describtionProjectTv);
            nameProjectTv = itemView.findViewById(R.id.nameProjectTv);
            textView = itemView.findViewById(R.id.textView);
        }
    }

    public void setList(List<Task> taskList) {
        this.taskList = taskList;
    }
}
