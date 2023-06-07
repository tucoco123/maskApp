package com.example.maskapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.maskapp.Data.Pharmacy;
import com.example.maskapp.db.PharmacyRepository;

import java.util.List;

public class PharmacyViewModel extends AndroidViewModel {
    private final PharmacyRepository mPharmacyRepository;
    public MutableLiveData<List<Pharmacy>> searchResultList = new MutableLiveData<>();
    public MutableLiveData<List<Pharmacy>> nearbyPharmacyList = new MutableLiveData<>();
    public MutableLiveData<List<Pharmacy>> recycleViewPharmacyList = new MutableLiveData<>();

    public PharmacyViewModel(@NonNull Application application) {
        super(application);
        mPharmacyRepository = new PharmacyRepository(application);

    }

    public void searchPharmacy(String inputText, double latitude, double longitude) {
        List<Pharmacy> resultList = mPharmacyRepository.searchName(inputText, latitude, longitude);
        searchResultList.setValue(resultList);
    }

    public void nearbyPharmacy(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        List<Pharmacy> resultList = mPharmacyRepository.findNearbyPharmacy(startLatitude, startLongitude, endLatitude, endLongitude);
        nearbyPharmacyList.setValue(resultList);
    }

    public void insert(Pharmacy pharmacy){
        mPharmacyRepository.insert(pharmacy);
    }

    public int getCount(){
        return mPharmacyRepository.getSumOfPharmacy();
    }


}
