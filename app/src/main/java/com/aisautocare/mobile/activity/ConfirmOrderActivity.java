package com.aisautocare.mobile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aisautocare.mobile.app.Config;
import com.google.firebase.messaging.FirebaseMessaging;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/02/02.
 */

public class ConfirmOrderActivity extends AppCompatActivity {

    private CircleImageView mechanicImage;
    private TextView garageName, mechanicName, mechanicPhone, orderPrice, orderDistance;
    private LinearLayout confirmButton, cancelButton;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        mechanicImage = (CircleImageView) findViewById(R.id.confrim_order_mechanic_image);
        garageName = (TextView) findViewById(R.id.confirm_order_garage_name);
//        mechanicName = (TextView) findViewById(R.id.confirm_order_mechanic_name_text_view);
        mechanicPhone = (TextView) findViewById(R.id.confirm_order_mechanic_phone_text_view);
        orderPrice = (TextView) findViewById(R.id.confirm_order_price_text_view);
        orderDistance = (TextView) findViewById(R.id.confirm_order_distance_text_view);
        confirmButton = (LinearLayout) findViewById(R.id.confirm_order_confirm_button);
        cancelButton = (LinearLayout) findViewById(R.id.confirm_order_cancel_button);

        String name = getIntent().getStringExtra("nama");
        String distance = getIntent().getStringExtra("lama_perjalanan");
        String price = getIntent().getStringExtra("total");
        //dummy
//        name = "John Smith";
        distance = "4 Km";
        price = "Rp 50.000";

//        mechanicName.setText(mechanicName.getText() + " " + name);
        orderDistance.setText(orderDistance.getText() + " " + distance);
        orderPrice.setText(orderPrice.getText() + " " + price);

        confirmButton.setOnClickListener(new View.OnClickListener() {
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
