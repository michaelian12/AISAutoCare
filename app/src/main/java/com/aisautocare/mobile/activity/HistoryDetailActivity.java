package com.aisautocare.mobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.aisautocare.mobile.util.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.firebasenotifications.R;

public class HistoryDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView workshopImage; // blm kepake
    private TextView dateTextView, serviceNameTextView, vehicleNameTextView, addressTextView, workshopNameTextView, workshopAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateTextView = (TextView) findViewById(R.id.history_detail_date_text_view);
        serviceNameTextView = (TextView) findViewById(R.id.history_detail_service_name_text_view);
        vehicleNameTextView = (TextView) findViewById(R.id.history_detail_vehicle_name_text_view);
        addressTextView = (TextView) findViewById(R.id.history_detail_address_text_view);
        workshopNameTextView = (TextView) findViewById(R.id.history_detail_workshop_name_text_view);
        workshopAddressTextView = (TextView) findViewById(R.id.history_detail_workshop_address_text_view);

        /* Get order id */
        String orderId = getIntent().getStringExtra("idOrder");
        System.out.println("id order = " +orderId);

        /* Get Histories Data */
        RestClient.get("/history_bengkel?detail_customer_order_id=" + orderId, null, new JsonHttpResponseHandler() {
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
                Toast.makeText(HistoryDetailActivity.this, "Gagal mendapatkan detail riwayat pesanan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                try {
                    System.out.println("masuk sukses");
                    System.out.println(data);
                    System.out.println("muncul data");

                    String date = data.getString("order_date");
                    System.out.println("tanggal order = " +date);

                    dateTextView.setText(date);
                    serviceNameTextView.setText(data.getString("service_sub").trim() + " " + data.getString("service_name").trim());
                    vehicleNameTextView.setText(data.getString("brand_name") + " " + data.getString("vehicle_type") + " " + data.getString("vehicle_year"));
                    addressTextView.setText(data.getString("order_service_location"));
                    workshopNameTextView.setText(data.getString("bengkel_name"));
                    workshopAddressTextView.setText(data.getString("bengkel_address"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                pd.hide();
            }
        });


    }
}
