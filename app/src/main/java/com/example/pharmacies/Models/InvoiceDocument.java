package com.example.pharmacies.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoiceDocument {
    private String documentDate;
    public String provider;
    public List<Medicines> Medicines;

    public void setDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        documentDate = format.format(date);
    }
    public Date getDate(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return format.parse(documentDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
