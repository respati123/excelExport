package com.example.acer.excelexport.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.acer.excelexport.AllRoomDatabase;
import com.example.acer.excelexport.Dao.UserDao;
import com.example.acer.excelexport.entities.User;

import java.math.RoundingMode;
import java.util.List;

public class RepoUser {

    private UserDao rUserDao;

    private LiveData<List<User>> rGetAllUser;

    public  RepoUser(Application application){
        AllRoomDatabase roomDatabase = AllRoomDatabase.getDatabase(application);
        rUserDao = roomDatabase.userDao();
        rGetAllUser = rUserDao.getAllUser();
    }


    public void insert(User user){
        new insertUserAsyncTask(rUserDao).execute(user);
    }

    public LiveData<List<User>> getrGetAllUser() {
        return rGetAllUser;
    }


    private class insertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao userDao;

        public insertUserAsyncTask(UserDao rUserDao) {
            userDao = rUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }
}
