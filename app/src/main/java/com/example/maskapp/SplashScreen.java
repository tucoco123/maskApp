package com.example.maskapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.maskapp.API.APIClient;
import com.example.maskapp.API.ApiCallBack;
import com.example.maskapp.API.ErrorResponse;
import com.example.maskapp.Data.Data;
import com.example.maskapp.Data.DataProperties;
import com.example.maskapp.Data.Features;
import com.example.maskapp.Data.Geometry;
import com.example.maskapp.Data.Pharmacy;
import com.example.maskapp.db.AppExecutors;
import com.example.maskapp.viewmodel.PharmacyViewModel;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.geohash.GeoHash;
import retrofit2.Call;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private static final int numberOfCharacters = 12;
    private ArrayList<Features> arrFeatures;
    private AppExecutors executors;
    private int dataCount;
    private Intent intent;
    private PharmacyViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        viewModel = new PharmacyViewModel(getApplication());

        intent = new Intent(this,MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        readData();
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                                getPermission();
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                                getPermission();
                            } else {
                                // No location access granted.
                                notGetPermission();
                            }
                            startActivity(intent);
                        }
                );


        locationPermissionRequest.launch(new String[] {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
    }
    private void getPermission(){
        Bundle bundle = new Bundle();
        Location myLocation = getLastKnownLocation();
        assert myLocation != null;
        double myLatitude = myLocation.getLatitude();
        double myLongitude = myLocation.getLongitude();
        bundle.putDouble("latitude", myLatitude);
        bundle.putDouble("longitude", myLongitude);
        bundle.putBoolean("enableLocate",true);
        intent.putExtras(bundle);
    }

    private void notGetPermission(){
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude",25.04951);
        bundle.putDouble("longitude",121.51730);
        bundle.putBoolean("enableLocate",false);
        intent.putExtras(bundle);
    }

    private Location getLastKnownLocation() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "未開啟權限", Toast.LENGTH_LONG).show();
                return null;
            }
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void readData() {
        dataCount = viewModel.getCount();
        executors = new AppExecutors();
        if (dataCount == 0) {
            APIClient apiClient = new APIClient();
            Call<Data> getDataCall = apiClient.getData();
            getDataCall.enqueue(new ApiCallBack<Data>() {
                @Override
                public void onSuccess(Data response) {
                    arrFeatures = response.getFeatures();
                    executors.getDiskIO().execute(() -> addToDatabase(arrFeatures));
                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {

                }
            });
        }
    }


    private void addToDatabase(ArrayList<Features> arrFeatures) {
        Pharmacy pharmacy = new Pharmacy();
        DataProperties properties;
        Geometry geometry;
        for (int i = 0; i < arrFeatures.size(); i++) {
            Features features = arrFeatures.get(i);
            properties = features.getProperties();
            geometry = features.getGeometry();
            pharmacy.setId(i);
            pharmacy.setName(properties.getName());
            pharmacy.setNumOfAdult(properties.getNumOfAdult());
            pharmacy.setNumOfChild(properties.getNumOfChild());
            pharmacy.setAddress(properties.getAddress());
            pharmacy.setTel(properties.getPhone());
            pharmacy.setNote(properties.getNote());
            pharmacy.setUpdateTime(properties.getUpdated());
            double longitude = Double.parseDouble(geometry.getCoordinates().get(0));
            double latitude = Double.parseDouble(geometry.getCoordinates().get(1));
            pharmacy.setLatitude(latitude);
            pharmacy.setLongitude(longitude);
            GeoHash geoHash = GeoHash.withCharacterPrecision(latitude, longitude, numberOfCharacters);
            pharmacy.setGeoHash(geoHash.toBase32());
            viewModel.insert(pharmacy);
        }
        dataCount = arrFeatures.size();
    }


}
