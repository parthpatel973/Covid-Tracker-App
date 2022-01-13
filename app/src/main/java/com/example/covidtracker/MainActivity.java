package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.covidtracker.api.ApiRequest;
import com.example.covidtracker.api.ModelClass;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView Total_Active, Total_cases, Total_Recover,Total_Deaths;
    TextView today_active, today_recover, today_deaths;
    TextView date;
    TextView country_list;
    private List<ModelClass> list;
    PieChart pie_Chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();


        Total_Active = findViewById(R.id.ActiveCases);
        Total_cases = findViewById(R.id.TotalCases);
        Total_Recover = findViewById(R.id.RecoverCases);
        Total_Deaths = findViewById(R.id.DeathsCases);

        today_deaths=findViewById(R.id.TodayDeaths);
        today_active=findViewById(R.id.TodayActive);
        today_recover=findViewById(R.id.TodayRecover);
        date = findViewById(R.id.Date);
        pie_Chart = findViewById(R.id.pieChart);
        country_list=findViewById(R.id.CountryList);

        ShowCountry();

        ApiRequest.getApiInterface().getModelClass().enqueue(new Callback<List<ModelClass>>() {
            @Override
            public void onResponse(Call<List<ModelClass>> call, Response<List<ModelClass>> response) {
                list.addAll(response.body());
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCountry().equals("India")) {

                        int active = Integer.parseInt(list.get(i).getActive());
                        int total = Integer.parseInt(list.get(i).getCases());
                        int recovered = Integer.parseInt(list.get(i).getRecovered());
                        int deaths = Integer.parseInt(list.get(i).getDeaths());

                        Total_Active.setText(NumberFormat.getInstance().format(active));
                        Total_cases.setText(NumberFormat.getInstance().format(total));
                        Total_Recover.setText(NumberFormat.getInstance().format(recovered));
                        Total_Deaths.setText(NumberFormat.getInstance().format(deaths));

                        today_deaths.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                        today_active.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                        today_recover.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));

                        pie_Chart.addPieSlice(new PieModel(deaths, getResources().getColor(R.color.brown)));
                        pie_Chart.addPieSlice(new PieModel(total, getResources().getColor(R.color.blue)));
                        pie_Chart.addPieSlice(new PieModel(active, getResources().getColor(R.color.yellow)));
                        pie_Chart.addPieSlice(new PieModel(recovered, getResources().getColor(R.color.pink_100)));

                        setDate(list.get(i).getUpdated());
                    }
                }

            }

            @Override
            public void onFailure(Call<List<ModelClass>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ShowCountry() {
        country_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Country.class);
                startActivity(intent);
            }
        });
    }

    private void setDate(String updated) {
        DateFormat dateFormat = new SimpleDateFormat("dd,MM,yyyy");

        long milliSeconds = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        date.setText("Updated at " + dateFormat.format(calendar.getTime()));

    }


}