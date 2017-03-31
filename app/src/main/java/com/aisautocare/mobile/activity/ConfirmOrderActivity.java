package com.aisautocare.mobile.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.aisautocare.mobile.fragment.RepairFragment;
import com.aisautocare.mobile.model.Order;
import com.aisautocare.mobile.model.POSTResponse;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private TextView garageName, mechanicName, mechanicPhone, orderPrice, orderDistance, mechanicAddress, total, distancePrice, servicePrice, serviceName, appPrice;
    private Button confirmButton, cancelButton;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private TextView skip;

    private int totalPriceValue;
    private int appPriceValue = 10000;
    private int hargaJasaValue;
    private int distancePriceValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        total = (TextView) findViewById(R.id.text_total);
        mechanicImage = (CircleImageView) findViewById(R.id.confrim_order_mechanic_image);
        garageName = (TextView) findViewById(R.id.confirm_order_garage_name);
        mechanicName = (TextView) findViewById(R.id.confirm_order_mechanic_name_text_view);
        mechanicPhone = (TextView) findViewById(R.id.confirm_order_mechanic_phone_text_view);
        orderPrice = (TextView) findViewById(R.id.confirm_order_price_text_view);
        orderDistance = (TextView) findViewById(R.id.confirm_order_distance_text_view);
        confirmButton = (Button) findViewById(R.id.confirm_order_confirm_button);
        cancelButton = (Button) findViewById(R.id.confirm_order_cancel_button);
        mechanicAddress = (TextView) findViewById(R.id.confirm_order_mechanic_address_text_view);
        distancePrice = (TextView) findViewById(R.id.text_distance_price);
        servicePrice = (TextView) findViewById(R.id.text_service_price);
        serviceName = (TextView) findViewById(R.id.confirm_order_service_text_view);
        appPrice = (TextView) findViewById(R.id.text_app_price);
        appPrice.setText("Rp. "+ appPriceValue);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmOrderActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        if (getIntent().getStringExtra("namaLayanan").toLowerCase().contains("ganti")){
            total.setText("Subtotal (*belum termasuk harga barang. Harga barang akan dikonfirmasikan oleh Bengkel / operator akan dalam waktu 5 menit. Mohon nomor Anda dapat dihubungi)");


        }else{
            total.setText("Total");
        }
        try {
            hargaJasaValue = Integer.valueOf(getIntent().getStringExtra("hj"));
        }catch (Exception e){
            hargaJasaValue = 0;
        }
        serviceName.setText("Harga Jasa : " + getIntent().getStringExtra("namaLayanan") );
        servicePrice.setText("Rp. " + hargaJasaValue);
        garageName.setText(getIntent().getStringExtra("namaBengkel"));
        mechanicName.setText(getIntent().getStringExtra("namaBengkel"));
        mechanicPhone.setText(getIntent().getStringExtra("hpBengkel"));
        mechanicAddress.setText(getIntent().getStringExtra("alamatBengkel"));



//        mechanicName.setText(mechanicName.getText() + " " + name);
        orderDistance.setText("Menghitung jarak tempuh...");
        orderPrice.setText(getIntent().getStringExtra("hargaLayanan"));
        Log.i("Order", "lat lng " + getIntent().getStringExtra("latBengkel") + ","+ Double.valueOf(getIntent().getStringExtra("lonBengkel")));
//        LatLng start = new LatLng(-7.716980, 110.384301);
        LatLng start = new LatLng(Double.valueOf(getIntent().getStringExtra("latBengkel")), Double.valueOf(getIntent().getStringExtra("lonBengkel")));
  //      LatLng end = new LatLng(-7.750548, 110.385968);
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
                            orderDistance.setText("Jasa Dilivery : " + (Double.valueOf(distanceInfo.getValue())/1000) + "Km");
                            distancePriceValue = (((Integer.valueOf(distanceInfo.getValue())/1000)/5 * 10000 )+ 10000);
                            distancePrice.setText("Rp. " + distancePriceValue) ;
                            totalPriceValue = hargaJasaValue + appPriceValue+distancePriceValue;
                            orderPrice.setText("Rp. " + totalPriceValue);

                            //orderPrice.setText("RP." + (Double.valueOf(distancePrice.getText().toString().replace("Rp.", "").replace(".", "").replace("Rp", "").replace(" ", "") ).intValue() +  Double.valueOf(servicePrice.getText().toString().replace("Rp.", "").replace(".", "").replace("Rp", "").replace(" ", "")).intValue() + 10000));
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
                new ConfirmOrderActivity.SendOrderedSignal().execute("");

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
    private String URLOrder = new GlobalVar().hostAPI + "/sendconfirmordered";
    public class SendOrderedSignal extends AsyncTask<String, Void, List<POSTResponse>> {

        private final String LOG_TAG = RepairFragment.GETRepair.class.getSimpleName();

        private List<POSTResponse> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject movieJson = new JSONObject(jsonStr);
            //JSONArray movieArray = movieJson.getJSONArray();
//            System.out.println("movie json : " + movieJson  );
//            System.out.println("itemsarray : " + movieArray  );
            // System.out.println(" Data JSON Items" + jsonStr);
            List<POSTResponse> results = new ArrayList<>();
            JSONObject berita = movieJson;
            POSTResponse beritaModel = new POSTResponse(berita);
            results.add(beritaModel);
            return results;
        }

        @Override
        protected List<POSTResponse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {

                Order order = new Order();


                Uri builtUri = Uri.parse(URLOrder).buildUpon()
                        .appendQueryParameter("uidBengkel", getIntent().getStringExtra("uidBengkel"))
                        .appendQueryParameter("id", GlobalVar.idOrder)

                        .build();

                URL url = new URL(builtUri.toString());


                //URL url = new URL(URLServiceRepair );
                Log.e("URl", url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                System.out.println("DATA BALIKAN " + jsonStr);
                return getRepairDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<POSTResponse> responses) {

            if (responses != null) {
                //repairs.clear();
                //repairs.addAll(services);
                System.out.println("responses ketika set adapter : " + responses.toString());
                Intent intent = new Intent(getApplicationContext(), TrackEmployeeActivity.class);
                intent.putExtra("namaBengkel", getIntent().getStringExtra("namaBengkel") );
                intent.putExtra("hpBengkel", getIntent().getStringExtra("hpBengkel") );
                intent.putExtra("alamatBengkel", getIntent().getStringExtra("alamatBengkel") );
                intent.putExtra("harga", getIntent().getStringExtra("hargaLayanan") );
                intent.putExtra("uidBengkel", getIntent().getStringExtra("uidBengkel"));

                startActivity(intent);
                //GlobalVar.idOrder = responses.get(0).getId();
//                if (Integer.valueOf(responses.get(0).getApi_status()) == 1) {
//                    finish();
//                    Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getApplicationContext().startActivity(intent);
//                } else {
//                    Log.e("AIS", "Error POST Order");
//                    Log.e("AIS", "API Message : " + responses.get(0).getApi_message().toString());
//                }
                //rcAdapter = new RecyclerViewAdapterBerita(getActivity(), movies);
                //adapter = new ServiceRecyclerViewAdapter();

                //rcAdapter.notifyDataSetChanged();

                //recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //swipeContainer.setRefreshing(false);

                //adapter.setLoaded();

                //pageBerita++;
                //Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //getApplicationContext().startActivity(intent);
            }
        }
    }
}
