package com.example.acer.excelexport;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.acer.excelexport.Dao.UserDao;
import com.example.acer.excelexport.entities.User;

@Database(entities = {User.class}, version = 2)
public abstract class AllRoomDatabase extends RoomDatabase {

    private static AllRoomDatabase INSTANCE;

    private static AllRoomDatabase.Callback sAllRoomDatabse =
            new AllRoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private UserDao mUserDao;

        public PopulateDbAsync(AllRoomDatabase instance) {
            mUserDao = instance.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            User user = new User();
            for(int i = 0; i < 10; i++){
                user.setName("respati");
                user.setAddress("narogong jaya"+ i +"A");
                user.setDeskripsi("ahahahahaha");
                mUserDao.insert(user);
            }
            return null;
        }
    }

    public static AllRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (AllRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        AllRoomDatabase.class, "coba")
                        .fallbackToDestructiveMigration()
                        .addCallback(sAllRoomDatabse)
                        .build();

            }
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
}
