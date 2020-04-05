package com.huseen.abo.aita1998.timemagment.roomDb.project;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository productRepository;
    private LiveData<List<Project>> allProduct;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        productRepository = new ProjectRepository(application);
    }

    public String insert(Project project) {
        return productRepository.insert(project);
    }

    public void update(Project project) {
        productRepository.updat(project);
    }

    public void delete(Project project) {
        productRepository.delete(project);
    }

    public void removeAllProject() {
        productRepository.deleteAllProduct();
    }

    public List<Project> getAll() throws ExecutionException, InterruptedException {
        return productRepository.getAll();
    }

    public LiveData<List<Project>> getAllProject() {
        return productRepository.getAllProducts();
    }
}
