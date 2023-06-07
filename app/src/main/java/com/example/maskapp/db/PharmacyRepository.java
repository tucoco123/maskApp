package com.example.maskapp.db;

import android.app.Application;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.example.maskapp.Data.Pharmacy;

import java.util.List;

public class PharmacyRepository {
    private final PharmacyDatabase db;

    public PharmacyRepository(Application application) {
        db = PharmacyDatabase.getInstance(application);
    }

    @Nullable
    public List<Pharmacy> searchName(String inputText, double latitude, double longitude) {
        return db.pharmacyDao().findByName(inputText, latitude, longitude);
    }

    public List<Pharmacy> findNearbyPharmacy(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        return db.pharmacyDao().findCameraRangePharmacy(startLatitude, startLongitude, endLatitude, endLongitude);
    }

    @WorkerThread
    public void insert(Pharmacy pharmacy){
        db.pharmacyDao().insertPharmacy(pharmacy);
    }

    public int getSumOfPharmacy(){
        return db.pharmacyDao().getCount();
    }



}
