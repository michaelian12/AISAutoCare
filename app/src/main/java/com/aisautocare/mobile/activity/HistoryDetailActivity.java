package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aisautocare.mobile.GlobalVar;

import info.androidhive.firebasenotifications.R;

public class HistoryDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Get user id */
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
        String idCustomer = sharedPreferences.getString("idCustomer", "");
        System.out.println("id customer di detil history tuh " + idCustomer);


    }
}
