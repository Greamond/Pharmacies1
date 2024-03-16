package com.example.pharmacies;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pharmacies.ApiClasses.IApi;
import com.example.pharmacies.ApiClasses.RetrofitFactory;
import com.example.pharmacies.Models.IssueRequest;
import com.example.pharmacies.customElements.IssueRequestItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestsActivity extends AppCompatActivity {

    List<IssueRequest> issueRequests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.getIssueRequest().enqueue(new Callback<List<IssueRequest>>() {
            @Override
            public void onResponse(Call<List<IssueRequest>> call, Response<List<IssueRequest>> response) {
                if (response.isSuccessful()){
                    issueRequests = response.body();
                    LinearLayout main = findViewById(R.id.main);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (IssueRequest item:issueRequests) {
                                IssueRequestItem uiItem = new IssueRequestItem(RequestsActivity.this);
                                uiItem.setDate(item);
                                main.addView(uiItem);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<IssueRequest>> call, Throwable t) {

            }
        });

    }
}