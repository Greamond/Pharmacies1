package com.example.pharmacies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.Warehouses;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnMedicines(View view){
        Intent intent = new Intent(this, MedicinesActivity.class);
        startActivity(intent);
    }

    public void OnRequest(View view){
        Intent intent = new Intent(this, RequestsActivity.class);
        startActivity(intent);
    }

    public void OnTransfer(View view){
        Intent intent = new Intent(this, TransfersActivity.class);
        startActivity(intent);
    }
}