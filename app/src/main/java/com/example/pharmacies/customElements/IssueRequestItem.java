package com.example.pharmacies.customElements;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pharmacies.Models.IssueRequest;
import com.example.pharmacies.R;

public class IssueRequestItem extends LinearLayout {
    TextView dateTv;
    TextView purposeTv;

    Context context;
    public IssueRequestItem(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.issue_request_item,this,true);
        dateTv = findViewById(R.id.dateTv);
        purposeTv = findViewById(R.id.purposeTv);
    }

    public void setDate(IssueRequest issueRequest){
        dateTv.setText(issueRequest.createdTime.toString());
        purposeTv.setText(issueRequest.purpose);
    }
}
