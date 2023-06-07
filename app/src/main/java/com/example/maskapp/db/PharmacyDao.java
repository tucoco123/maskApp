package com.example.maskapp.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.maskapp.Data.Pharmacy;

import java.util.List;

@Dao
public interface PharmacyDao {
    @Query("SELECT * FROM Pharmacy WHERE Name LIKE '%' || :words || '%'  ORDER BY ABS(:lat-latitude)*ABS(:lat-latitude)+ABS(:lng-longitude)*ABS(:lng-longitude) ASC")
    List<Pharmacy> findByName(String words, double lat, double lng);


    @Query("SELECT * FROM Pharmacy WHERE latitude > :startLatitude AND latitude < :endLatitude AND longitude < :endLongitude AND longitude > :startLongitude")
    List<Pharmacy> findCameraRangePharmacy(double startLatitude,double startLongitude, double endLatitude, double endLongitude);

    @Query("SELECT COUNT(*) FROM Pharmacy")
    int getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPharmacy(Pharmacy pharmacy);
}
