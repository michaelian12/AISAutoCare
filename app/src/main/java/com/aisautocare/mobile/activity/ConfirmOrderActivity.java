package com.aisautocare.mobile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aisautocare.mobile.app.Config;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/02/02.
 */

public class ConfirmOrderActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvDistance;
    private TextView tvPrice;
    private LinearLayout btnConfirmOrder;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        tvName = (TextView) findViewById(R.id.nameEmployee);
        tvDistance = (TextView) findViewById(R.id.distanceOrder);
        tvPrice = (TextView) findViewById(R.id.priceOrder);
        btnConfirmOrder = (LinearLayout) findViewById(R.id.btnConfirmOrder);

        String name = getIntent().getStringExtra("nama");
        String distance = getIntent().getStringExtra("lama_perjalanan");
        String price = getIntent().getStringExtra("total");
        //dummy
        name = "John Smith";
        distance = "4 Km";
        price = "Rp 50.000";

        tvName.setText(tvName.getText() + " " + name);
        tvDistance.setText(tvDistance.getText() + " " + distance);
        tvPrice.setText(tvPrice.getText() + " " + price);
        btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackEmployeeActivity.class);
                startActivity(intent);
            }
        });
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    //displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received



                    //Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                }
            }
        };
    }
}
