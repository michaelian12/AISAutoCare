package com.aisautocare.mobile.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aisautocare.mobile.GlobalVar;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.w3c.dom.Text;

import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/01/22.
 */

public class WaitOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button cancelButton;
    private TextView symbolFail;
    private TextView keterangan;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_order);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cancelButton = (Button) findViewById(R.id.wait_order_cancel_button);
        symbolFail = (TextView) findViewById(R.id.symbol_fail_wait);
        keterangan = (TextView) findViewById(R.id.keterangan_wait_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_wait_order);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GlobalVar.bengkelLat == 0){
                    symbolFail.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    keterangan.setText("Maaf Tidak ditemukan bengkel disekitar anda, tim kami akan menghubungi anda segera.");

                }
            }
        }, 60000); //semenit
        //new WaitOrderActivity.WaitSplash().execute();
    }

    class WaitSplash extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void... params) {
            try {
                Thread.currentThread();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Intent intent = new Intent(getApplicationContext(), ConfirmOrderActivity.class);
            startActivity(intent);
        }
    }
}
