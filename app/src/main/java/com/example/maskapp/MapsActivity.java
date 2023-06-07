package com.example.maskapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.maskapp.Data.Pharmacy;
import com.example.maskapp.databinding.ActivityMapsBinding;
import com.example.maskapp.viewmodel.PharmacyViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    public double myLatitude;
    public double myLongitude;
    public boolean fragmentIsDisplay = true;
    private boolean isListMode = false;
    private SearchFragment searchFragment;
    private FloatingActionButton floatingButton;
    public PharmacyViewModel pharmacyViewModel;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.maskapp.databinding.ActivityMapsBinding binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        pharmacyViewModel = new ViewModelProvider(this).get(PharmacyViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        floatingButton = findViewById(R.id.floating_button);
        fragmentIsDisplay = false;

        floatingButton.setOnClickListener(v -> {
            if (!isListMode) {
                floatingButton.setImageResource(R.mipmap.pin_location);
                openFragment();
            } else {
                floatingButton.setImageResource(R.drawable.ic_menu);
                hideFragment();
            }
        });
    }


    public void openFragment() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment(getActivity());
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container, searchFragment);
            transaction.commit();
        } else {
            Log.d(TAG, "open Fragment");
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.show(searchFragment);
            transaction.commit();
        }
        isListMode = true;
    }

    public void hideFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(searchFragment);
        transaction.commit();
        isListMode = false;
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("PotentialBehaviorOverride")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        createInfoFragment();

        CustomClusterManager mClusterManager = new CustomClusterManager(this, mMap);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            myLatitude = bundle.getDouble("latitude");
            myLongitude = bundle.getDouble("longitude");
            LatLng myLocate = new LatLng(myLatitude, myLongitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocate));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            if (bundle.getBoolean("enableLocate")) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            }
        }

        mMap.setOnMarkerClickListener(mClusterManager);
        mMap.setOnCameraIdleListener(mClusterManager);
        ClusterRender clusterRender = new ClusterRender(getActivity(), mMap, mClusterManager);
        mClusterManager.setRenderer(clusterRender);

        mClusterManager.setOnClusterClickListener((ClusterManager.OnClusterClickListener<Pharmacy>) cluster -> {
            List<Pharmacy> pharmacyDisplayList = new ArrayList<Pharmacy>(cluster.getItems());
            openClickFragment(pharmacyDisplayList);
            setIconDisplay(false);
            return true;
        });
        mClusterManager.setOnClusterItemClickListener((ClusterManager.OnClusterItemClickListener<Pharmacy>) pharmacy -> {
            List<Pharmacy> pharmacyDisplayList = new ArrayList<Pharmacy>();
            pharmacyDisplayList.add(pharmacy);
            openClickFragment(pharmacyDisplayList);
            setIconDisplay(false);
            return true;
        });
        mMap.setOnMapClickListener(latLng -> {
            if (fragmentIsDisplay) {
                closeClickFragment();
                setIconDisplay(true);
            }
        });

        Log.d(TAG, "onMapReady");
    }

    private void createInfoFragment() {
        ClickItemFragment clickFragment = new ClickItemFragment(getActivity(), true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, clickFragment);
        transaction.show(clickFragment);
        transaction.commit();
    }

    public void closeClickFragment(){
        List<Pharmacy> list = pharmacyViewModel.recycleViewPharmacyList.getValue();
        assert list != null;
        list.clear();
        pharmacyViewModel.recycleViewPharmacyList.setValue(list);
        fragmentIsDisplay = false;
    }

    public void openClickFragment(List<Pharmacy> pharmacyList){
        pharmacyViewModel.recycleViewPharmacyList.setValue(pharmacyList);
        fragmentIsDisplay = true;
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void setIconDisplay(boolean flag) {
        if (flag) {
            floatingButton.setVisibility(View.VISIBLE);
        } else {
            floatingButton.setVisibility(View.GONE);
        }
    }

    public MapsActivity getActivity() {
        return this;
    }

}