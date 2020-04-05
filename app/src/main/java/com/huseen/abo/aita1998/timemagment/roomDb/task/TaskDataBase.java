package com.huseen.abo.aita1998.timemagment.roomDb.task;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = Task.class, version = 1)
public abstract class TaskDataBase extends RoomDatabase {
    public static TaskDataBase instance;

    public abstract TaskDao taskDao();

    public static synchronized TaskDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDataBase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomcallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute(0);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        private TaskDao taskDao;

        public PopulateDbAsyncTask(TaskDataBase taskDataBase) {
            taskDao = taskDataBase.taskDao();
        }

        @Override
        protected Integer doInBackground(Integer... voids) {
            return 0;
        }

    }
}
