package com.aisautocare.mobile.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.adapter.FragmentAdapter;
import com.aisautocare.mobile.fragment.RepairFragment;
import com.aisautocare.mobile.model.Order;
import com.aisautocare.mobile.model.POSTResponse;
import com.aisautocare.mobile.model.User;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import info.androidhive.firebasenotifications.R;
import com.aisautocare.mobile.app.Config;
import com.aisautocare.mobile.util.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;

import static android.R.attr.order;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private LinearLayout btnAddVehicle;
    private LinearLayout selectedVehicle;
    private TextView pilihKendaraan;
    private LinearLayout tambahKendaraan;

    private static int RESULT_ADD_VEHICLE=1;
    private static int RESULT_REGISTER=2;

    private FloatingActionButton fab;

    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    private Firebase postUser;
    public String regId;

    User user = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        //txtMessage = (TextView) findViewById(R.id.txt_push_message);
        selectedVehicle = (LinearLayout) findViewById(R.id.selected_vehicle);
        //selectedVehicle.removeViewInLayout();
        pilihKendaraan = (TextView) findViewById(R.id.tvChooseVehicle);
        pilihKendaraan.setVisibility(View.INVISIBLE);
        btnAddVehicle = (LinearLayout) findViewById(R.id.btnAddVehicle);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);
        if (getIntent().getStringExtra("finish") != null){
            Log.i(TAG, "SAKITTTT JIWAAAA");
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.dialog_rating);
            dialog.setTitle("Penilaian");
            dialog.show();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TrackEmployeeActivity.class);

                startActivityForResult(intent, 1);
            }
        });

        btnAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);

                startActivityForResult(intent, 1);
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

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();


        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // remove title
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        /* Set Navigation Drawer */
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navItemSelect);

        /* Set View Pager */
        viewPager = (ViewPager) findViewById(R.id.category_viewpager);
        FragmentAdapter adapter = new FragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Find the tab layout that shows the tabs
        tabLayout = (TabLayout) findViewById(R.id.category_tabs);
        tabLayout.setupWithViewPager(viewPager);

        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    Log.i(TAG, "kedeetek blm login");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
        Firebase.setAndroidContext(this);
        String result = "";
        result = getIntent().getStringExtra("register");
        if ( result != null){
            if(result.equalsIgnoreCase("1")){
                new MainActivity.POSTDeviceid().execute("");
                postUser = new Firebase("https://devais-b06d4.firebaseio.com/users/" );
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("type", "1");
                map.put("regId", regId);
                postUser.child(auth.getCurrentUser().getUid()).updateChildren(map);
                Log.i(TAG, "masuk ke hasil Register");
            }
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "On result code " + resultCode);
        if (resultCode == RESULT_ADD_VEHICLE ) {//
            selectedVehicle = (LinearLayout) findViewById(R.id.selected_vehicle);

            LayoutInflater inflater;
            inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.view_selected_vehicle, null);
            TextView vehicleManufacture = (TextView) layout.findViewById(R.id.selected_vehicle_manufacture);
            TextView vehicleManufactureType = (TextView) layout.findViewById(R.id.selected_vehicle_manufacture_type);
            System.out.println("MERK "  + data.getStringExtra("Merk"));
            vehicleManufacture.setText(data.getStringExtra("Merk"));
            vehicleManufactureType.setText(data.getStringExtra("MerkType"));
            selectedVehicle.removeAllViews();
            selectedVehicle.addView(layout);
            pilihKendaraan.setVisibility(View.VISIBLE);
            btnAddVehicle.removeAllViews();

            GlobalVar.isVehicleSelected = true;
        } else if (resultCode == RESULT_REGISTER){


//            final Dialog dialog = new Dialog(this);
//            dialog.setContentView(R.layout.dialog_rating);
//            dialog.setTitle("Penilaian");
//            dialog.show();
        }
    }

    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        //if (!TextUtils.isEmpty(regId));
            //txtRegId.setText("Firebase Reg Id: " + regId);
        //else
            //txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    NavigationView.OnNavigationItemSelectedListener navItemSelect = new NavigationView.OnNavigationItemSelectedListener() {

        Intent intent;

        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {

            menuItem.setCheckable(true);
            drawerLayout.closeDrawer(GravityCompat.START);

            switch (menuItem.getItemId()){
                case R.id.nav_profile:
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_history:
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_garage:
                    intent = new Intent(MainActivity.this, GarageActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_login:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_register:
                    intent = new Intent(MainActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.nav_logout:
                    Toast.makeText(MainActivity.this, getString(R.string.auth_logout), Toast.LENGTH_LONG).show();
                    auth.signOut();

                    return true;

                default:
                    return true;
            }
        }
    };

    private String URLRegister = new GlobalVar().hostAPI + "/register";
    public class POSTDeviceid extends AsyncTask<String, Void, List<POSTResponse>> {

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
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id", "");
                Log.i(TAG, "id setelah register"+ id);
                Log.i(TAG, "regid yang akan dikirim "+ regId);

//                Log.i(TAG, sharedPreferences.getAll().get("name").toString());

                user.setName(sharedPreferences.getString("name",""));
                user.setCellphone(sharedPreferences.getString("phone",""));
                user.setUid(auth.getCurrentUser().getUid());
                user.setEmail(sharedPreferences.getString("email",""));
                user.setType("1");

                Uri builtUri = Uri.parse(URLRegister).buildUpon()
//                        .appendQueryParameter("id", id)
                        .appendQueryParameter("deviceid", regId)
                        .appendQueryParameter("name", user.getName())
                        .appendQueryParameter("cellphone", user.getCellphone())
                        .appendQueryParameter("email", user.getEmail())
                        .appendQueryParameter("type", user.getType())
                        .appendQueryParameter("email", user.getEmail())
                        .appendQueryParameter("address","")
                        .appendQueryParameter("latitude", "")
                        .appendQueryParameter("longitude", "")
                        .appendQueryParameter("ref_area_id", "14")
                        .appendQueryParameter("ref_occupation_id", "1")
                        .appendQueryParameter("uid", user.getUid())

                        .build();

                URL url = new URL(builtUri.toString());

                Log.i(TAG, "URL register " + url);
                //URL url = new URL(URLServiceRepair );

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
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
            //Log.i(TAG, responses.get(0).toString());
            if (responses != null) {
                //repairs.clear();
                //repairs.addAll(services);
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    String id = responses.get(0).getId();
//                                    editor.putString("id", id);
                editor.putString("idCustomer", responses.get(0).getId());
                editor.commit();
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
//                Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
            }
        }
    }

}
