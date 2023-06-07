package com.example.maskapp;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class ClickItemFragment extends Fragment {
    private final MapsActivity activity;
    private final float device_height;
    private final boolean isHaveMaxHeight;

    public ClickItemFragment(MapsActivity activity,boolean isHaveMaxHeight){
        this.activity = activity;
        this.isHaveMaxHeight = isHaveMaxHeight;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float density = metrics.density;
        device_height = metrics.heightPixels / density;


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recycelview_layout, container, false);
        RecyclerView recyclerView = root.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        if(isHaveMaxHeight){
            ConstraintLayout constraintLayout = (ConstraintLayout) root.findViewById(R.id.constraint);
            ConstraintSet set = new ConstraintSet();
            set.clone(constraintLayout);
            set.constrainMaxHeight(R.id.recycleView, (int) (device_height));
            set.applyTo(constraintLayout);
            activity.pharmacyViewModel.recycleViewPharmacyList.observe(activity, pharmacyList -> {
                RecycleViewAdapter adapter = new RecycleViewAdapter(activity, pharmacyList);
                recyclerView.setAdapter(adapter);
            });
        }
        else{
            activity.pharmacyViewModel.searchResultList.observe(activity, pharmacyList -> {
                RecycleViewAdapter adapter = new RecycleViewAdapter(activity, pharmacyList, true);
                recyclerView.setAdapter(adapter);
            });
        }

        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
