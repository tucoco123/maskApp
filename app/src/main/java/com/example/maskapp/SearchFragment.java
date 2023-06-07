package com.example.maskapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.textfield.TextInputEditText;

@SuppressLint("ValidFragment")
public class SearchFragment extends Fragment {
    private static final String TAG = "Search";
    private final MapsActivity activity;
    private TextInputEditText textInputEditText;
    private ProgressBar progressBar;
    private TextView txtNoResult;
    public ClickItemFragment searchResultFragment;


    public SearchFragment(MapsActivity activity) {
        this.activity = activity;
        activity.pharmacyViewModel.searchResultList.observe(activity, pharmacyList -> {
            if (pharmacyList.size() == 0) {
                Log.d(TAG,"pharmacyList is 0");
                txtNoResult.setVisibility(View.VISIBLE);
            } else {
                searchResultFragment = new ClickItemFragment(activity,false);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.result_frameLayout, searchResultFragment);
                transaction.addToBackStack("FRAGMENT");
                transaction.commit();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.search_layout, container, false);

        View content = root.findViewById(R.id.no_item_layout);
        ViewGroup.LayoutParams param = content.getLayoutParams();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        param.height = displayMetrics.heightPixels - 100;


        progressBar = (ProgressBar) root.findViewById(R.id.spin_kit);
        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);

        txtNoResult = root.findViewById(R.id.txt_noResult);
        textInputEditText = root.findViewById(R.id.textInput);

        textInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            textInputEditText.clearFocus();
            hideKeyboardFrom(activity, v);
            return false;
        });

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                progressBar.setVisibility(View.VISIBLE);
                txtNoResult.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String inputText = s.toString();
                activity.pharmacyViewModel.searchPharmacy(inputText, activity.myLatitude, activity.myLongitude);
            }

            @Override
            public void afterTextChanged(Editable s) {
                progressBar.setVisibility(View.GONE);
            }
        });


        textInputEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                hideKeyboardFrom(activity, v);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
