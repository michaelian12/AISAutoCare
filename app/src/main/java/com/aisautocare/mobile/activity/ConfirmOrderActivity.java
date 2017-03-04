package com.aisautocare.mobile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.app.Config;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/02/02.
 */

public class ConfirmOrderActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView mechanicImage;
    private TextView garageName, mechanicName, mechanicPhone, orderPrice, orderDistance, mechanicAddress;
    private Button confirmButton, cancelButton;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mechanicImage = (CircleImageView) findViewById(R.id.confrim_order_mechanic_image);
        garageName = (TextView) findViewById(R.id.confirm_order_garage_name);
        mechanicName = (TextView) findViewById(R.id.confirm_order_mechanic_name_text_view);
        mechanicPhone = (TextView) findViewById(R.id.confirm_order_mechanic_phone_text_view);
        orderPrice = (TextView) findViewById(R.id.confirm_order_price_text_view);
        orderDistance = (TextView) findViewById(R.id.confirm_order_distance_text_view);
        confirmButton = (Button) findViewById(R.id.confirm_order_confirm_button);
        cancelButton = (Button) findViewById(R.id.confirm_order_cancel_button);
        mechanicAddress = (TextView) findViewById(R.id.confirm_order_mechanic_address_text_view);


        garageName.setText(getIntent().getStringExtra("namaBengkel"));
        mechanicName.setText(getIntent().getStringExtra("namaBengkel"));
        mechanicPhone.setText(getIntent().getStringExtra("hpBengkel"));
        mechanicAddress.setText(getIntent().getStringExtra("alamatBengkel"));


//        mechanicName.setText(mechanicName.getText() + " " + name);
        orderDistance.setText("Menghitung jarak tempuh...");
        orderPrice.setText(getIntent().getStringExtra("hargaLayanan"));
        Log.i("Order", "lat lng " + getIntent().getStringExtra("latBengkel") + ","+ Double.valueOf(getIntent().getStringExtra("lonBengkel")));
        LatLng start = new LatLng(Double.valueOf(getIntent().getStringExtra("latBengkel")), Double.valueOf(getIntent().getStringExtra("lonBengkel")));
        LatLng end = new LatLng(GlobalVar.selectedLat,GlobalVar.selectedLon);
        GoogleDirection.withServerKey("AIzaSyBDv7B62-bLvjbdWZCXyIl4dxiLmSR4vB0")
                .from(start)
                .to(end)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction, String rawBody) {
                        if (direction.isOK()) {
                            // Do something
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);

                            List<Step> stepList = leg.getStepList();

                            Info distanceInfo = leg.getDistance();
                            final Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            final String duration = durationInfo.getValue();
                            GlobalVar.waktuTempuh = Integer.valueOf(durationInfo.getValue());
                            System.out.println("Jarak dan waktu " + distance + " " + duration);
                            orderDistance.setText((Double.valueOf(distanceInfo.getValue())/1000) + "Km");


//                            layoutButtons.setVisibility(View.VISIBLE);
                            //animateMarker(mMap, customerLoc, directionPositionList, false, Integer.valueOf(durationInfo.getValue()));

                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });


        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), TrackEmployeeActivity.class);
                intent.putExtra("namaBengkel", getIntent().getStringExtra("namaBengkel") );
                intent.putExtra("hpBengkel", getIntent().getStringExtra("hpBengkel") );
                intent.putExtra("alamatBengkel", getIntent().getStringExtra("alamatBengkel") );
                intent.putExtra("harga", getIntent().getStringExtra("hargaLayanan") );

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
