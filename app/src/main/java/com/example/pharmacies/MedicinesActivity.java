package com.example.pharmacies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.Models.Warehouses;
import com.example.pharmacies.customElements.MedicineItem;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MedicinesActivity extends AppCompatActivity {

    List<Warehouses> warehouses;
    LinearLayout itemsHolder;
    Spinner warehousesSpinner;
    boolean itemSelected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicines);
        itemsHolder = findViewById(R.id.itemsHolder);
        warehousesSpinner = findViewById(R.id.warehousesSpinner);

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.getWarehouses().enqueue(new Callback<List<Warehouses>>() {
            @Override
            public void onResponse(Call<List<Warehouses>> call, Response<List<Warehouses>> response) {
                if (response.isSuccessful()){
                    warehouses = response.body();
                    List<String> names = new ArrayList<>();
                    names.add("Все склады");
                    for (Warehouses item:warehouses) {
                        names.add(item.name);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(MedicinesActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,names);
                    warehousesSpinner.setAdapter(adapter);
                    itemSelected = true;
                }
            }

            @Override
            public void onFailure(Call<List<Warehouses>> call, Throwable t) {

            }
        });

        warehousesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!itemSelected)
                    return;
                IApi retrofit2 = RetrofitFactory.getRetrofit();
                if (position==0){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            List<Medicines> medicines = new ArrayList<>();
                            for (Warehouses warehouses:warehouses) {
                                Response<List<Medicines>> currMedicines = null;
                                try {
                                    currMedicines = retrofit2.getMedicines(warehouses.id).execute();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                if (currMedicines.isSuccessful()){
                                    boolean contains = false;
                                    for (Medicines medItem: currMedicines.body()) {
                                        for (Medicines containedMedicines: medicines) {
                                            if (Objects.equals(medItem.name, containedMedicines.name)){
                                                containedMedicines.quantity+=medItem.quantity;
                                                contains = true;
                                            }
                                        }
                                        if (!contains)
                                            medicines.add(medItem);
                                    }
                                }
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    reshowItems(medicines);
                                }
                            });

                        }
                    }).start();

                }else{
                    retrofit2.getMedicines(warehouses.get(position-1).id).enqueue(new Callback<List<Medicines>>() {
                        @Override
                        public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                            if (response.isSuccessful()){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        reshowItems(response.body());
                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Medicines>> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void reshowItems(List<Medicines> medicines){
        itemsHolder.removeAllViews();

        for (Medicines item: medicines) {
            MedicineItem medicineItem = new MedicineItem(this);
            medicineItem.setDate(item);
            itemsHolder.addView(medicineItem);
        }
    }

    public void OnInvoice(View view){
        Intent intent = new Intent(this,MedecineInvoiceActivity.class);
        startActivity(intent);
    }
}