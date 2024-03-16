package com.example.pharmacies;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.Models.Warehouses;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransfersActivity extends AppCompatActivity {

    List<Warehouses> warehousesList;
    List<Medicines> medicinesList;
    Spinner toSpinner;
    Spinner medicineSpinner;
    EditText quatityEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfers);

        toSpinner = findViewById(R.id.toSpinner);
        medicineSpinner = findViewById(R.id.medecineSpinner);
        quatityEt = findViewById(R.id.quantityEt);

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.getWarehouses().enqueue(new Callback<List<Warehouses>>() {
            @Override
            public void onResponse(Call<List<Warehouses>> call, Response<List<Warehouses>> response) {
                if (response.isSuccessful()){
                    warehousesList = response.body();
                    List<String> names = new ArrayList<>();
                    for (Warehouses item : warehousesList) {
                        names.add(item.name);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(TransfersActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,names);
                    toSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Warehouses>> call, Throwable t) {

            }
        });

        retrofit.getMedicines().enqueue(new Callback<List<Medicines>>() {
            @Override
            public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                if (response.isSuccessful()){
                    medicinesList = response.body();
                    List<String> names = new ArrayList<>();
                    for (Medicines med : medicinesList) {
                        names.add(med.name);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(TransfersActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,names);
                    medicineSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Medicines>> call, Throwable t) {

            }
        });
    }

    public void OnTransfer(View view){
        Warehouses warehouses = warehousesList.get(toSpinner.getSelectedItemPosition());
        Medicines med = medicinesList.get(medicineSpinner.getSelectedItemPosition());

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.medTransfer(med.id,warehouses.id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast toast = new Toast(TransfersActivity.this);
                    toast.setText("Успешно");
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast toast = new Toast(TransfersActivity.this);
                toast.setText("Ахахах, чё? Не Получилось?");
                toast.show();
            }
        });
    }
}