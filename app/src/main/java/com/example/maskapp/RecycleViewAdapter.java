package com.example.maskapp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maskapp.Data.Pharmacy;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    private final List<Pharmacy> pharmacyList;
    private final MapsActivity activity;
    private boolean isSearchFragment = false;

    public RecycleViewAdapter(MapsActivity activity, List<Pharmacy> pharmacyList) {
        this.activity = activity;
        this.pharmacyList = pharmacyList;
    }

    public RecycleViewAdapter(MapsActivity activity, List<Pharmacy> pharmacyList, boolean isSearchFragment) {
        this.activity = activity;
        this.pharmacyList = pharmacyList;
        this.isSearchFragment = isSearchFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pharmacy pharmacy = pharmacyList.get(position);
        holder.textName.setText(pharmacy.getName());
        holder.textAdultNum.setText(String.valueOf(pharmacy.getNumOfAdult()));
        holder.textChildNum.setText(String.valueOf(pharmacy.getNumOfChild()));
        holder.textAddress.setText(pharmacy.getAddress());
        holder.textTel.setText(pharmacy.getTel());
        holder.textNote.setText(pharmacy.getNote());
        holder.textUpdate.setText(pharmacy.getUpdateTime());

        if(position%2 == 0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#24cc76"));
        }
        else{
            holder.linearLayout.setBackgroundColor(Color.parseColor("#698af2"));
        }

        holder.linearLayout.setOnClickListener(v -> {
            LatLng latLng = new LatLng(pharmacy.getLatitude(),pharmacy.getLongitude());
            int zoom = 16;
            activity.getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
            if(isSearchFragment){
                activity.hideFragment();
            }
        });
    }


    @Override
    public int getItemCount() {
        return pharmacyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout linearLayout;
        public TextView textName;
        public TextView textAdultNum;
        public TextView textChildNum;
        public TextView textAddress;
        public TextView textTel;
        public TextView textNote;
        public TextView textUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.item_background);
            textName = itemView.findViewById(R.id.txt_name);
            textAdultNum = itemView.findViewById(R.id.txt_adult_mask_number);
            textChildNum = itemView.findViewById(R.id.txt_child_mask_number);
            textAddress = itemView.findViewById(R.id.txt_address);
            textTel = itemView.findViewById(R.id.txt_telephone);
            textNote = itemView.findViewById(R.id.txt_note);
            textUpdate = itemView.findViewById(R.id.txt_update);
        }
    }
}
