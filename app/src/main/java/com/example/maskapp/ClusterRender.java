package com.example.maskapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.maskapp.Data.Pharmacy;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class ClusterRender extends DefaultClusterRenderer<Pharmacy> {

    private final Context context;

    public ClusterRender(Context context, GoogleMap map, ClusterManager<Pharmacy> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onBeforeClusterItemRendered(@NonNull Pharmacy item, @NonNull MarkerOptions markerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions);

        TextView text = new TextView(context);
        String name = item.getName();
        text.setText(name);
        text.setTypeface(Typeface.DEFAULT_BOLD);
        text.setPadding(8, 5, 8, 5);
        text.setTextColor(Color.parseColor("#FFFFFF"));
        IconGenerator generator = new IconGenerator(context);
        generator.setBackground(context.getDrawable(R.color.light_green));
        generator.setContentView(text);
        Bitmap icon = generator.makeIcon();
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

    @Override
    protected void onClusterItemUpdated(@NonNull Pharmacy item, @NonNull Marker marker) {
        super.onClusterItemUpdated(item, marker);
    }

    @Override
    protected void onClusterRendered(@NonNull Cluster<Pharmacy> cluster, @NonNull Marker marker) {
        super.onClusterRendered(cluster, marker);

    }

    @Override
    protected int getColor(int clusterSize) {
        return super.getColor(clusterSize);
    }


}
