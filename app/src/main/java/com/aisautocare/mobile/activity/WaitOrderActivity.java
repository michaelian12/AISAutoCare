package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.fragment.RepairFragment;
import com.aisautocare.mobile.model.Order;
import com.aisautocare.mobile.model.POSTResponse;
import com.akexorcist.googledirection.model.Line;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Button yesButton;
    private Button noButton;
    private LinearLayout layoutExtendButton;
    private TextView skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_order);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noButton = (Button) findViewById(R.id.button_no_extend_range);
        yesButton = (Button) findViewById(R.id.button_yes_extend_range);
        layoutExtendButton = (LinearLayout) findViewById(R.id.layout_confirmation_extend);
        cancelButton = (Button) findViewById(R.id.wait_order_cancel_button);
        symbolFail = (TextView) findViewById(R.id.symbol_fail_wait);
        keterangan = (TextView) findViewById(R.id.keterangan_wait_text_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_wait_order);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (GlobalVar.bengkelLat == 0){
                    symbolFail.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    keterangan.setText("Maaf tidak ditemukan bengkel yang tersedia di sekitar anda. Apakah anda ingin memperluas wilayah pencarian ?");
                    layoutExtendButton.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.GONE);
                    new WaitOrderActivity.POSTNotGetMechanic().execute("");
                    handler.removeCallbacksAndMessages(null);

                }
            }
        }, 60000);//60000); //semenit
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(WaitOrderActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(WaitOrderActivity.this, MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
                finish();
            }
        });
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutExtendButton.setVisibility(View.GONE);
                symbolFail.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                keterangan.setText("Mencari montir yang tersedia");
                cancelButton.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (GlobalVar.bengkelLat == 0){

                            symbolFail.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            keterangan.setText("Maaf tidak ditemukan bengkel yang tersedia di sekitar anda. Operator akan menghubungin Anda dalam waktu maksimal 5 menit");
                            cancelButton.setText("Kembali");
                            handler.removeCallbacksAndMessages(null);
                        }
                    }
                }, 60000);
            }
        });

        //new WaitOrderActivity.WaitSplash().execute();
    }
    private String URLOrder = new GlobalVar().hostAPI + "/sendnotifnotgetmechanic";
    public class POSTNotGetMechanic extends AsyncTask<String, Void, List<POSTResponse>> {

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
                        .appendQueryParameter("id", GlobalVar.idOrder)
                        .appendQueryParameter("id_customer", GlobalVar.idCustomerLogged)
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
            }
        }
    }

}
