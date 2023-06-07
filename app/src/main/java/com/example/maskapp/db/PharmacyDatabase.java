package com.example.maskapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.maskapp.Data.Pharmacy;

@Database(entities = {Pharmacy.class},version = 1,exportSchema = false)
public abstract class PharmacyDatabase extends RoomDatabase {
    public abstract PharmacyDao pharmacyDao();
    private static volatile PharmacyDatabase INSTANCE;
    public static PharmacyDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (PharmacyDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PharmacyDatabase.class,"pharmacy.db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
