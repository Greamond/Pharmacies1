package com.example.pharmacies;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.customElements.MedicineItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueRequestDetailsActivity extends AppCompatActivity {

    public List<Medicines> medicinesList;
    LinearLayout itemsHolder;
    public int idRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_request_details);

        itemsHolder = findViewById(R.id.itemsHolder);
        idRequest = getIntent().getIntExtra("id",0);

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.getIssueRequest(idRequest).enqueue(new Callback<List<Medicines>>() {
            @Override
            public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                if (response.isSuccessful()){
                    medicinesList = response.body();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {reshowItems(medicinesList);}
                    });

                }
            }

            @Override
            public void onFailure(Call<List<Medicines>> call, Throwable t) {

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
}