package com.example.pharmacies.ApiClasses;

import androidx.annotation.Nullable;

import com.example.pharmacies.Models.InvoiceDocument;
import com.example.pharmacies.Models.IssueRequest;
import com.example.pharmacies.Models.MedicineWriteOff;
import com.example.pharmacies.Models.Medicines;
import com.example.pharmacies.Models.Warehouses;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {

    @GET("Warehouses")
    Call<List<Warehouses>> getWarehouses();
    @GET("Medicines")
    Call<List<Medicines>> getMedicines(@Query("WarehouseId")int WarehouseId);
    @GET("Medicines")
    Call<List<Medicines>> getMedicines();
    @GET("IssueRequests/{Id}")
    Call<List<Medicines>> getIssueRequest(@Path("Id")int id);
    @GET("IssueRequests")
    Call<List<IssueRequest>> getIssueRequest();
    @POST("Medicines/Writeoff")
    Call<ResponseBody> writeOff(@Body MedicineWriteOff item);
    @POST("Medicines/Invoice")
    Call<ResponseBody> medInvoice(@Body InvoiceDocument doc);
    @POST("Medicines/{Id}/Transfer")
    Call<ResponseBody> medTransfer(@Path("Id")int MedicineId,@Query("destinationWarehouseId")int WarehouseId);
}
