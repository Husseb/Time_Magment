package com.huseen.abo.aita1998.timemagment.roomDb.task;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository productRepository;
    private LiveData<List<Task>> allProduct;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        productRepository = new TaskRepository(application);
    }

    public String insert(Task task) {
        return productRepository.insert(task);
    }

    public void update(Task task) {
        productRepository.updat(task);
    }

    public void delete(Task task) {
        productRepository.delete(task);
    }

    public void removeAllTask() {
        productRepository.deleteAllProduct();
    }

    public List<Task> getAll() throws ExecutionException, InterruptedException {
        return productRepository.getAll();
    }
    
    public LiveData<List<Task>> getAllTask() {
        return productRepository.getAllProducts();
    }
}