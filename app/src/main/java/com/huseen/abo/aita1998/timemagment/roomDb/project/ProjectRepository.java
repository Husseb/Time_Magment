package com.huseen.abo.aita1998.timemagment.roomDb.project;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectRepository {
    ProjectDao projectDao;
    LiveData<List<Project>> allProject;

    public ProjectRepository(Application application) {
        ProjectDataBase noteDataBase = ProjectDataBase.getInstance(application);
        projectDao = noteDataBase.projectDao();
        allProject = projectDao.getAllProject();
    }

    public String insert(Project Project) {
        final AsyncTask<Project, Integer, Integer> execute = new InsertProjectAsyncProject(projectDao).execute(Project);
        final AsyncTask.Status status = execute.getStatus();
        return status.name();
    }

    public void updat(Project Project) {
        new UpdateProjectAsyncProject(projectDao).execute(Project);
    }


    public void delete(Project Project) {
        new DeleteProjectAsyncProject(projectDao).execute(Project);
    }

    public void deleteAllProduct() {
        new DeleteAllProductsAsyncProject(projectDao).execute();
    }

    public List<Project> getAll() throws ExecutionException, InterruptedException {

        final AsyncTask<Project, Integer, List<Project>> execute = new GetAllAsyncProject(projectDao).execute();

        return execute.get();
    }


    public LiveData<List<Project>> getAllProducts() {
        return allProject;
    }

    private static class InsertProjectAsyncProject extends AsyncTask<Project, Integer, Integer> {
        private ProjectDao projectDao;

        private InsertProjectAsyncProject(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Integer doInBackground(Project... projects) {
            projectDao.insert(projects[0]);
            return 0;
        }
    }

    private static class UpdateProjectAsyncProject extends AsyncTask<Project, Integer, Integer> {
        private ProjectDao projectDao;

        private UpdateProjectAsyncProject(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Integer doInBackground(Project... projects) {
            projectDao.update(projects[0]);
            return 0;
        }
    }

    private static class GetAllAsyncProject extends AsyncTask<Project, Integer, List<Project>> {

        private ProjectDao projectDao;

        private GetAllAsyncProject(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected List<Project> doInBackground(Project... projects) {
            return projectDao.getAll();
        }
    }

    private static class DeleteProjectAsyncProject extends AsyncTask<Project, Integer, Integer> {
        private ProjectDao projectDao;

        private DeleteProjectAsyncProject(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Integer doInBackground(Project... Projects) {
            projectDao.delete(Projects[0]);
            return 0;
        }
    }

    private static class DeleteAllProductsAsyncProject extends AsyncTask<Project, Integer, Integer> {
        private ProjectDao projectDao;

        private DeleteAllProductsAsyncProject(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Integer doInBackground(Project... projects) {
            projectDao.deleteAllProduct();
            return 0;
        }
    }
}
