package com.ramnarayan.actmobilenetworks.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.ramnarayan.actmobilenetworks.R;
import com.ramnarayan.actmobilenetworks.data.CountryPojo;
import com.ramnarayan.actmobilenetworks.preference.SharedPreference;
import com.ramnarayan.actmobilenetworks.util.Constants;
import com.squareup.picasso.Picasso;

/*
@author Ram Narayan
@created at 10th Oct 2021
 */
public class CountryActivity extends AppCompatActivity implements CountryAdapter.CountryInfoListener {
    private Context context;

    protected CountryViewModel countryViewModel;
    private boolean isBottomSheetOpen;
    private CountryPojo countryPojo;

    private LinearLayout llChooseRegion;
    private ImageView imageCountryFlag;
    private TextView textCountryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        initObserver();
        intiView();
        setViewValue();

        countryViewModel.getCountryNameList(context);
    }

    private void intiView() {
        llChooseRegion = findViewById(R.id.llChooseRegion);
        imageCountryFlag = findViewById(R.id.imageCountryFlag);
        textCountryName = findViewById(R.id.textCountryName);
    }

    private void setViewValue() {
        llChooseRegion.setOnClickListener(view -> {
            if (countryPojo == null) {
                isBottomSheetOpen = true;
                countryViewModel.getCountryNameList(context);
            } else {
                loadBottomSheet();
            }
        });

        if (!SharedPreference.getCountryCode(context).equalsIgnoreCase("")) {
            textCountryName.setText(SharedPreference.getCountryName(context));
            Picasso.get()
                    .load(SharedPreference.getCountryFlagUrl(context))
                    .into(imageCountryFlag);
        }
    }

    private void initObserver() {
        context = this;
        countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);
        countryViewModel.getCountryNameList().observe(this, strings -> {
            if (strings != null) {
                if (strings[0].equalsIgnoreCase(Constants.OK)) {
                    countryPojo = new Gson().fromJson(strings[1], CountryPojo.class);
                    if (isBottomSheetOpen) {
                        isBottomSheetOpen = false;
                        loadBottomSheet();
                    }
                } else {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void loadBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(R.layout.item_bottom_sheet_country);

        RecyclerView rvCountry = bottomSheetDialog.findViewById(R.id.rvCountry);

        loadRecyclerView(rvCountry, bottomSheetDialog);
        bottomSheetDialog.show();

    }

    private void loadRecyclerView(RecyclerView rvCountry, BottomSheetDialog bottomSheetDialog) {
        if (rvCountry != null) {
            LinearLayoutManager manager = new LinearLayoutManager(context);
            rvCountry.setLayoutManager(manager);
            rvCountry.setAdapter(new CountryAdapter(countryPojo, bottomSheetDialog, this));
        }
    }

    @Override
    public void countryInfo(String countryName, String countryCode, String countryFlagUrl) {
        SharedPreference.setCountryName(context, countryName);
        SharedPreference.setCountryCode(context, countryCode);
        SharedPreference.setCountryFlagUrl(context, countryFlagUrl);

        textCountryName.setText(countryName);
        Picasso.get()
                .load(countryFlagUrl)
                .into(imageCountryFlag);
    }
}

