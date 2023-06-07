package com.example.maskapp;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.maskapp.viewmodel.PharmacyViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

public class CustomClusterManager extends ClusterManager{
    private static final String TAG = "CustomClusterManager";
    private final PharmacyViewModel pharmacyViewModel;
    private final GoogleMap mMap;


    public CustomClusterManager(MapsActivity activity, GoogleMap map) {
        super(activity, map);
        this.mMap = map;
        pharmacyViewModel = new ViewModelProvider(activity).get(PharmacyViewModel.class);
        pharmacyViewModel.nearbyPharmacyList.observe(activity, pharmacyList -> {
            clearItems();
            addItems(pharmacyList);
            cluster();
        });
    }

    @Override
    public void onCameraIdle() {
        super.onCameraIdle();
        Log.d(TAG,"onCameraIdle");

        LatLng nearLeft = mMap.getProjection().getVisibleRegion().nearLeft;
        LatLng farRight = mMap.getProjection().getVisibleRegion().farRight;
        pharmacyViewModel.nearbyPharmacy(nearLeft.latitude, nearLeft.longitude, farRight.latitude, farRight.longitude);
    }

}
