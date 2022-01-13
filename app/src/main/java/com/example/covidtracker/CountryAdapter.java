package com.example.covidtracker;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covidtracker.api.ModelClass;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {


    private final ArrayList<ModelClass> arrayList;
    private final Context context;

    public CountryAdapter(ArrayList<ModelClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.country_items,parent,false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        ModelClass data=arrayList.get(position);
        holder.snum.setText(String.valueOf(position+1));
        holder.country_Name.setText(data.getCountry());
        holder.Total_cases.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));

        Map<String,String> img_flag=data.getCountryInfo();
        Glide.with(context).load(img_flag.get("flag")).into(holder.country_flag);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        private final TextView snum;
        private final TextView country_Name;
        private final TextView Total_cases;
        private final ImageView country_flag;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            snum = itemView.findViewById(R.id.s_num);
            country_Name = itemView.findViewById(R.id.CountryName);
            country_flag = itemView.findViewById(R.id.flags);
            Total_cases = itemView.findViewById(R.id.Cases);
        }
    }
}
