package com.example.pharmacies;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.MedicineWriteOff;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteOffActivity extends AppCompatActivity {

    EditText countEt;
    EditText reasonEt;
    int medId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_off);
        medId = getIntent().getIntExtra("id",0);
        countEt = findViewById(R.id.countEt);
        reasonEt = findViewById(R.id.reasonEt);
    }

    public void OnSave(View view){
        MedicineWriteOff item = new MedicineWriteOff();
        item.medicineId = medId;
        item.quantity = Integer.valueOf(countEt.getText().toString());
        item.reason = reasonEt.getText().toString();
        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.writeOff(item).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast toast = new Toast(WriteOffActivity.this);
                    toast.setText("Успешно");
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast toast = new Toast(WriteOffActivity.this);
                toast.setText("Всё плохо");
                toast.show();
            }
        });
    }
}