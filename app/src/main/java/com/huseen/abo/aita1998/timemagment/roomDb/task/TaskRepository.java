package com.huseen.abo.aita1998.timemagment.roomDb.task;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskRepository {
    TaskDao taskDao;
    LiveData<List<Task>> allTask;

    public TaskRepository(Application application) {
        TaskDataBase noteDataBase = TaskDataBase.getInstance(application);
        taskDao = noteDataBase.taskDao();
        allTask = taskDao.getAllTask();
    }

    public String insert(Task task) {
        final AsyncTask<Task, Integer, Integer> execute = new InsertTaskAsyncTask(taskDao).execute(task);
        final AsyncTask.Status status = execute.getStatus();
        return status.name();
    }

    public void updat(Task task) {
        new UpdateTaskAsyncTask(taskDao).execute(task);
    }


    public void delete(Task task) {
        new DeleteTaskAsyncTask(taskDao).execute(task);
    }

    public void deleteAllProduct() {
        new DeleteAllProductsAsyncTask(taskDao).execute();
    }

    public List<Task> getAll() throws ExecutionException, InterruptedException {
        final AsyncTask<Task, Integer, List<Task>> execute = new GetAllAsyncTask(taskDao).execute();

        return execute.get();
    }


    public LiveData<List<Task>> getAllProducts() {
        return allTask;
    }

    private static class InsertTaskAsyncTask extends AsyncTask<Task, Integer, Integer> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return 0;
        }
    }

    private static class UpdateTaskAsyncTask extends AsyncTask<Task, Integer, Integer> {
        private TaskDao taskDao;

        private UpdateTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            taskDao.update(tasks[0]);
            return 0;
        }
    }

    private static class GetAllAsyncTask extends AsyncTask<Task, Integer, List<Task>> {

        private TaskDao taskDao;

        private GetAllAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected List<Task> doInBackground(Task... tasks) {

            return taskDao.getAll();
        }
    }

    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Integer, Integer> {
        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            taskDao.delete(tasks[0]);
            return 0;
        }
    }

    private static class DeleteAllProductsAsyncTask extends AsyncTask<Task, Integer, Integer> {
        private TaskDao taskDao;

        private DeleteAllProductsAsyncTask(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Integer doInBackground(Task... tasks) {
            taskDao.deleteAllProduct();
            return 0;
        }
    }
}
