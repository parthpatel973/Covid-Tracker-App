package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.covidtracker.api.ApiRequest;
import com.example.covidtracker.api.ModelClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Country extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<ModelClass> modelClassArrayList;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        modelClassArrayList=new ArrayList<>();
        CountryAdapter adapter=new CountryAdapter(modelClassArrayList,this);


        recyclerView=findViewById(R.id.Country_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
        dialog.show();

        ApiRequest.getApiInterface().getModelClass().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                modelClassArrayList.addAll(response.body());
                dialog.dismiss();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(Country.this, "Error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}