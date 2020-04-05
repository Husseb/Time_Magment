package com.huseen.abo.aita1998.timemagment.catagurey.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huseen.abo.aita1998.timemagment.R;
import com.huseen.abo.aita1998.timemagment.catagurey.CatagureyActivity;
import com.huseen.abo.aita1998.timemagment.roomDb.project.Project;
import com.huseen.abo.aita1998.timemagment.roomDb.task.Task;
import com.huseen.abo.aita1998.timemagment.roomDb.task.TaskViewModel;
import com.huseen.abo.aita1998.timemagment.tasks.TaskActivity;

import java.util.ArrayList;
import java.util.List;

public class CatagureyAdapter extends RecyclerView.Adapter<CatagureyAdapter.MyHolder> {
    Context context;
    private List<Project> projectList;
    private TaskViewModel taskViewModel;
    List<Task> taskList = new ArrayList<>();
    private CatagureyActivity catagureyActivity;


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        if (position >= 0) {
            final Project project = projectList.get(position);


            int r = project.getProgress();
            holder.progressBar.setProgress(r);
            holder.textView.setText(project.getProgress() + " %");
            holder.nameProjectTv.setText(project.getName());
            holder.describtionProjectTv.setText(project.getDetails());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskActivity.class);
                    intent.putExtra("id", project.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void getPrgressBarValue(Project project) {
        taskViewModel.getAllTask().observe(catagureyActivity, tasks -> {
            for (int i = 0; i < tasks.size(); i++) {
                final Task task = tasks.get(i);

                if (task.getId() == project.getId()) {
                    taskList.add(task);
                }

            }
            for (int i = 0; i < taskList.size(); i++) {


            }
        });
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }

    public void setList(List<Project> projectList, TaskViewModel taskViewModel, CatagureyActivity catagureyActivity) {
        this.projectList = projectList;
        this.taskViewModel = taskViewModel;
        this.catagureyActivity = catagureyActivity;
        notifyDataSetChanged();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView nameProjectTv, describtionProjectTv, textView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            nameProjectTv = itemView.findViewById(R.id.nameProjectTv);
            describtionProjectTv = itemView.findViewById(R.id.describtionProjectTv);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
