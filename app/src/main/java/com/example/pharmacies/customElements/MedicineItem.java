package com.example.pharmacies.customElements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.R;
import com.example.pharmacies.WriteOffActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class MedicineItem extends LinearLayout {
    TextView nameTv ;
    TextView quantityTv;
    Medicines medicines;
    ImageView imageView;
    TextView priceTv;
    TextView manufacturerTv;
    TextView nameTradeTv;
    Context context;
    public MedicineItem(Context context) {
        super(context);
        this.context = context;
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.medicine_item, this, true);
        quantityTv = findViewById(R.id.countTv);
        nameTv = findViewById(R.id.nameTv);
        imageView = findViewById(R.id.imageV);
        priceTv = findViewById(R.id.priceTv);
        manufacturerTv = findViewById(R.id.manufacturerTv);
        nameTradeTv = findViewById(R.id.nameTradeTv);
    }
    public void setDate(Medicines medicines){
        quantityTv.setText(String.valueOf(medicines.quantity));
        nameTv.setText(medicines.name);
        priceTv.setText(priceTv.getText()+ String.valueOf(medicines.price));
        manufacturerTv.setText(manufacturerTv.getText()+medicines.manufacturer);
        nameTradeTv.setText(nameTradeTv.getText()+medicines.tradeName);
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream in = null;
                try {
                    in = new URL(medicines.image).openStream();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                setImage(bitmap);
            }
        }).start();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WriteOffActivity.class);
                intent.putExtra("id",medicines.id);
                context.startActivity(intent);
            }
        });
    }

    private void setImage(Bitmap bitmap){
        ( (Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }
}
