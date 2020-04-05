package com.huseen.abo.aita1998.timemagment.roomDb.project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Project.class, version = 1)
public abstract class ProjectDataBase extends RoomDatabase {
    public static ProjectDataBase instance;

    public abstract ProjectDao projectDao();

    public static synchronized ProjectDataBase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDataBase.class, "project_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomcallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomcallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute(0);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        private ProjectDao projectDao;

        public PopulateDbAsyncTask(ProjectDataBase projectDataBase) {
            projectDao = projectDataBase.projectDao();
        }

        @Override
        protected Integer doInBackground(Integer... voids) {
            return 0;
        }

    }
}
