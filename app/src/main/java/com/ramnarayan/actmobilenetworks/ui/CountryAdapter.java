package com.ramnarayan.actmobilenetworks.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ramnarayan.actmobilenetworks.R;
import com.ramnarayan.actmobilenetworks.data.CountryPojo;
import com.ramnarayan.actmobilenetworks.preference.SharedPreference;
import com.squareup.picasso.Picasso;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {
    private Context context;
    private final CountryPojo countryPojo;
    private final BottomSheetDialog bottomSheetDialog;
    private final CountryInfoListener countryInfoListener;

    public CountryAdapter(CountryPojo countryPojo, BottomSheetDialog bottomSheetDialog, CountryInfoListener countryInfoListener) {
        this.countryPojo = countryPojo;
        this.bottomSheetDialog = bottomSheetDialog;
        this.countryInfoListener = countryInfoListener;
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new CountryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        CountryPojo.Result result = countryPojo.getResult()[position];
        holder.textCountryName.setText(result.getName());

        String flagUrl = "https://www.countryflags.io/" + result.getCode() + "/shiny/64.png";
        Picasso.get()
                .load(flagUrl)
                .into(holder.imageCountryFlag);

        holder.rlCountry.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            countryInfoListener.countryInfo(result.getName(), result.getCode(), flagUrl);
        });

        holder.rbCountryFlag.setOnClickListener(view -> {
            bottomSheetDialog.dismiss();
            countryInfoListener.countryInfo(result.getName(), result.getCode(), flagUrl);
        });

        holder.rbCountryFlag.setChecked(SharedPreference.getCountryCode(context).equalsIgnoreCase(result.getCode()));
    }

    @Override
    public int getItemCount() {
        return countryPojo != null ? countryPojo.getResult().length : 0;
    }

    static class CountryHolder extends RecyclerView.ViewHolder {
        private final TextView textCountryName;
        private final ImageView imageCountryFlag;
        private final RelativeLayout rlCountry;
        private final RadioButton rbCountryFlag;

        public CountryHolder(@NonNull View itemView) {
            super(itemView);

            textCountryName = itemView.findViewById(R.id.textCountryName);
            imageCountryFlag = itemView.findViewById(R.id.imageCountryFlag);
            rbCountryFlag = itemView.findViewById(R.id.rbCountryFlag);
            rlCountry = itemView.findViewById(R.id.rlCountry);

        }
    }

    public interface CountryInfoListener {
        void countryInfo(String countryName, String countryCode, String countryFlagUrl);
    }
}
