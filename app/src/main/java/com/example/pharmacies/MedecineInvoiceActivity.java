package com.example.pharmacies;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.pharmacies.Models.InvoiceDocument;
import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.Models.Warehouses;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MedecineInvoiceActivity extends AppCompatActivity {

    EditText dateDocumentEt;
    EditText quantityEt;
    EditText providerEt;
    EditText dateEt;
    EditText priceEt;
    List<Medicines> medicinesList;
    Spinner medicinesSpinner;
    List<Medicines> selectedMed = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medecine_invoice);

        dateDocumentEt = findViewById(R.id.dateDocumentEt);
        providerEt = findViewById(R.id.providerEt);
        priceEt = findViewById(R.id.priceEt);
        dateEt = findViewById(R.id.dateEt);
        quantityEt = findViewById(R.id.quantityEt);
        medicinesSpinner = findViewById(R.id.medicinesSpinner);

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.getMedicines().enqueue(new Callback<List<Medicines>>() {
            @Override
            public void onResponse(Call<List<Medicines>> call, Response<List<Medicines>> response) {
                if (response.isSuccessful()){
                    medicinesList = response.body();
                    List<String> names = new ArrayList<>();
                    for (Medicines item:medicinesList) {
                        names.add(item.name);
                    }
                    ArrayAdapter adapter = new ArrayAdapter(MedecineInvoiceActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,names);
                    medicinesSpinner.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Medicines>> call, Throwable t) {

            }
        });

        medicinesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Medicines med = medicinesList.get(position);

                if (selectedMed.contains(med)){
                    dateEt.setText(med.expirationDate.toString());
                    priceEt.setText(String.valueOf(med.price));
                    quantityEt.setText(String.valueOf(med.quantity));
                }else{
                    dateEt.setText("");
                    priceEt.setText("");
                    quantityEt.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void  OnSaveList(View view){
        Medicines med = medicinesList.get(medicinesSpinner.getSelectedItemPosition());
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date pickedDate = null;
        try {
            pickedDate = df.parse(dateEt.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Date endDate = Date.from(Instant.now());
        if (pickedDate.after(endDate)){
            med.quantity = Integer.valueOf(quantityEt.getText().toString());
            med.expirationDate = (pickedDate);
            med.price = Integer.valueOf(priceEt.getText().toString());

            selectedMed.add(med);
        }
        else{
            Toast toast = new Toast(MedecineInvoiceActivity.this);
            toast.setText("Проверь дату, дура");
            toast.show();
        }


    }

    public void  OnInvoice(View view){
        InvoiceDocument doc = new InvoiceDocument();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        try {
            doc.setDate(df.parse(dateDocumentEt.getText().toString()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doc.provider = providerEt.getText().toString();
        doc.Medicines = selectedMed;

        IApi retrofit = RetrofitFactory.getRetrofit();
        retrofit.medInvoice(doc).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast toast = new Toast(MedecineInvoiceActivity.this);
                    toast.setText("Успешно");
                    toast.show();
                }
                ResponseBody body = response.errorBody();
                try {
                    String str = body.string();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ResponseBody body2 = response.body();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast toast = new Toast(MedecineInvoiceActivity.this);
                toast.setText("Провал");
                toast.show();
            }
        });
    }
}