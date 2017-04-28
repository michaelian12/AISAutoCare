package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
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
    private ProgressDialog pd;

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

        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Menjangkau Server");

        /* Get Histories Data */
        RestClient.get("/history_bengkel?list_customer_id=" + idCustomer, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart()   {
                super.onStart();
                pd.show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + responseString);
                pd.hide();
                Toast.makeText(HistoryActivity.this, "Gagal mendapatkan riwayat pesanan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                try {
                    JSONArray arrayHistory = data.getJSONArray("data");

                    for (int i = 0; i < arrayHistory.length(); i++) {
                        JSONObject history = arrayHistory.getJSONObject(i);
                        histories.add(new History(history));
//                        System.out.println(histories.get(i).getVehicleName());
                    }

                    adapter = new HistoryRecyclerViewAdapter(HistoryActivity.this, histories);

                    recyclerView.setLayoutManager(new LinearLayoutManager(HistoryActivity.this));
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setHasFixedSize(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.hide();
            }
        });
    }
}
