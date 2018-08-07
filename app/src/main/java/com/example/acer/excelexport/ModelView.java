package com.example.acer.excelexport;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.acer.excelexport.entities.User;
import com.example.acer.excelexport.repository.RepoUser;

import java.util.List;

public class ModelView extends AndroidViewModel{

    private RepoUser repoUser;

    private LiveData<List<User>> getAllData;


    public ModelView(Application app){
        super(app);

        repoUser = new RepoUser(app);
        getAllData = repoUser.getrGetAllUser();

    }


    //user


    public LiveData<List<User>> getGetAllData() {
        return getAllData;
    }

    public void insert(User user){
        repoUser.insert(user);
    }
}
