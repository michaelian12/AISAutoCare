package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.adapter.HistoryRecyclerViewAdapter;
import com.aisautocare.mobile.model.History;
import com.aisautocare.mobile.util.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

public class HistoryActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private HistoryRecyclerViewAdapter adapter;

    private ArrayList<History> histories = new ArrayList<History>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        /* Get user id */
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
        String idCustomer = sharedPreferences.getString("idCustomer", "");
//        System.out.println("id customer di history tuh " + idCustomer);

        /* Get Histories Data */
        RestClient.get("/history_bengkel?customer_id=" + idCustomer, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart()   {
                super.onStart();
//                pd.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + responseString);
//                pd.hide();
                Toast.makeText(HistoryActivity.this, "Gagal mendapatkan riwayat pesanan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                try {
                    JSONArray arrayHistory = data.getJSONArray("data");

                    for (int i = 0; i < arrayHistory.length(); i++) {
                        JSONObject history = arrayHistory.getJSONObject(i);
                        histories.add(new History(history));
                        System.out.println(histories.get(i).getVehicleName());
//                        brandNames.add(vehicleBrands.get(i).getName());
//                        if (vehicleBrands.get(i).getId().equals(idBrand)) {
//                            indexSelectedBrand = i;
//                        }
                    }

                    adapter = new HistoryRecyclerViewAdapter(HistoryActivity.this, histories);

                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);

//                    arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
//                    vehicleBrandSpinner.setAdapter(arrayBrandAdapter);
//                    System.out.println(brandNames);
//                    System.out.println(arrayBrandAdapter);
//                    System.out.println("panjang array = " + vehicleBrandSpinner.length());
//                    System.out.println("index selected brand = " + indexSelectedBrand);
////                            vehicleBrandSpinner.setSelection(indexSelectedBrand);
//                    vehicleBrandSpinner.setText(brandNames.get(indexSelectedBrand));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                pd.hide();
            }
        });

//        ArrayList<History> histories = new ArrayList<History>();
//        histories.add(new History(R.drawable.ic_car, "2 Februari 2017", "Rp 100.000", "Ford Fiesta", "Derek"));
//        histories.add(new History(R.drawable.ic_car, "26 Januari 2017", "Rp 80.000", "Ford Fiesta", "Ganti Aki"));
//        histories.add(new History(R.drawable.ic_bike, "14 Januari 2017", "Rp 35.000", "Kawasaki Ninja", "Cuci / Salon"));

//        adapter = new HistoryRecyclerViewAdapter(this, histories);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setHasFixedSize(true);
    }
}
