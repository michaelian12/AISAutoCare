package com.aisautocare.mobile.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aisautocare.mobile.GlobalVar;
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
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.snapshot.DoubleNode;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

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
import java.util.Map;

import info.androidhive.firebasenotifications.R;

public class TrackEmployeeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LinearLayout timer;
    private Button arriveButton, finishButton, cancelButton;

    private LinearLayout btnFinished;
//    private LinearLayout layoutButtons;
    private Firebase trackFirebase;
    private Marker customerLoc;
    private ImageView callPhone;
    private TextView namaMechanic, namaMechanicEmployee, hpMechanic;
    private ArrayList<LatLng> directionPositionList;
    private CountDownTimer timerGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_employee);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cancelButton = (Button) findViewById(R.id.track_cancel_button);
        namaMechanic = (TextView) findViewById(R.id.track_mechanic_name);
        namaMechanicEmployee = (TextView) findViewById(R.id.nama_montir_track);
        hpMechanic = (TextView) findViewById(R.id.track_employee_mechanic_phone_text_view);
        callPhone = (ImageView) findViewById(R.id.call_mechanic);
//        namaMechanic.setText(getIntent().getStringExtra("namaBengkel"));
//        namaMechanicEmployee.setText(getIntent().getStringExtra("namaBengkel"));
//        hpMechanic.setText(getIntent().getStringExtra("hpBengkel"));
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(TrackEmployeeActivity.this)
                        .setTitle("PERHATIAN!")
                        .setMessage("Apakah anda yakin akan membatalkan Pesanan? Anda akan dikenai biaya pembatalan sebesar Rp 20.000")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(TrackEmployeeActivity.this)
                                        .setTitle("PERHATIAN!")
                                        .setMessage("Bengkel akan menuju lokasi untuk mengambil biaya pembatalan")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(TrackEmployeeActivity.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        callPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("cell phone clicked");
                if (ContextCompat.checkSelfPermission(TrackEmployeeActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(TrackEmployeeActivity.this, new String[]{Manifest.permission.CALL_PHONE},
                            1);
                }else{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    String hp = getIntent().getStringExtra("hpBengkel");
//                    if (hp.substring(0,1) != "0"){
//                        hp = "0" + hp;
//                    }

                    callIntent.setData(Uri.parse("tel:"+hpMechanic.getText()));
//                    callIntent.setData(Uri.parse("tel:"+getIntent().getStringExtra("hpBengkel")));

                    if (ActivityCompat.checkSelfPermission(TrackEmployeeActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }

            }
        });



        arriveButton = (Button) findViewById(R.id.track_arrive_button);
        arriveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrackEmployeeActivity.this, RatingActivity.class);
                startActivity(intent);
            }
        });
//        finishButton = (Button) findViewById(R.id.track_finish_button);
        timer = (LinearLayout) findViewById(R.id.timer);
//        layoutButtons = (LinearLayout) findViewById(R.id.layout_button_track);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        timer.addView(new CircularCountdown(this));
//        LayoutInflater inflater;
//        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.view_track_employee, null);

        //timer.addView(layout);

//        setContentView();

//        finishButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(TrackEmployeeActivity.this, RatingActivity.class);
//                startActivity(intent);
//            }
//        });

        String name = "andoyo";
        Firebase.setAndroidContext(this);
        trackFirebase = new Firebase("https://devais-b06d4.firebaseio.com/messages/" + name);
//        trackFirebase.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Map map = dataSnapshot.getValue(Map.class);
//                String lat = map.get("lat").toString();
//                String lon = map.get("lon").toString();
//                LatLng locCurrent = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
//                customerLoc.setPosition(locCurrent);
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//
//            }
//        });



        timerGo = new CountDownTimer(1000, 20) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                try{
                    if (GlobalVar.statusBerangkat){
                        timer.addView(new CircularCountdown(TrackEmployeeActivity.this));
//                            layoutButtons.setVisibility(View.VISIBLE);
                        animateMarker(mMap, customerLoc, directionPositionList, false, GlobalVar.waktuTempuh);
                    }else{
                        timerGo.start();
                    }

                }catch(Exception e){
                    Log.e("Error", "Error: " + e.toString());
                }
            }
        }.start();
        
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng start = new LatLng(GlobalVar.bengkelLat, GlobalVar.bengkelLon);
        LatLng end = new LatLng(GlobalVar.selectedLat,GlobalVar.selectedLon);
//        LatLng start = new LatLng(-7.716980, 110.384301);

//        LatLng end = new LatLng(-7.750548, 110.385968);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(start, 15.0f));
        mMap.getUiSettings().setMapToolbarEnabled(false);

         mMap.addMarker(new MarkerOptions().position(end).title("Lokasi Kendaraan anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car)));

        customerLoc = mMap.addMarker(new MarkerOptions().position(start).title("Lokasi Keberangkatan Montir"));
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


                            directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(TrackEmployeeActivity.this, directionPositionList, 5, Color.RED);
                            mMap.addPolyline(polylineOptions);
                            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(directionPositionList.get(0), 10));
                            Handler handler = new Handler();
                            Info distanceInfo = leg.getDistance();
                            final Info durationInfo = leg.getDuration();
                            String distance = distanceInfo.getText();
                            final String duration = durationInfo.getValue();
                            GlobalVar.waktuTempuh = Integer.valueOf(durationInfo.getValue());
                            System.out.println("Jarak dan waktu " + distance + " " + duration);
//                            timer.addView(new CircularCountdown(TrackEmployeeActivity.this));
//                            layoutButtons.setVisibility(View.VISIBLE);
//                            animateMarker(mMap, customerLoc, directionPositionList, false, Integer.valueOf(durationInfo.getValue()));

                        } else {
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        // Do something
                    }
                });


    }
    private static void animateMarker(GoogleMap myMap, final Marker marker, final List<LatLng> directionPoint,
                                      final boolean hideMarker, int duration ) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = myMap.getProjection();
        //final long duration = 720000;
        duration = (duration) *1000;

        final Interpolator interpolator = new LinearInterpolator();

        final int finalDuration = duration;
        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / finalDuration);
                if (i < directionPoint.size())
                    marker.setPosition(directionPoint.get(i));
                i++;

                handler.postDelayed(this, finalDuration /directionPoint.size());
//                if (t < 1.0) {
//                    // Post again 16ms later.
//
//                } else {
//                    handler.postDelayed(this,  (finalDuration *60)*1000/directionPoint.size());
//                    if (hideMarker) {
//                        marker.setVisible(false);
//                    } else {
//                        marker.setVisible(true);
//                    }
//                }
            }
        });
    }

    private class CircularCountdown extends View {

        private final Paint backgroundPaint;
        private final Paint progressPaint;
        private final Paint textPaint;

        private long startTime;
        private long currentTime;
        private long maxTime;

        private long progressMillisecond;
        private double progress;

        private RectF circleBounds;
        private float radius;
        private float handleRadius;
        private float textHeight;
        private float textOffset;

        private final Handler viewHandler;
        private final Runnable updateView;

        public CircularCountdown(Context context) {
            super(context);

            // used to fit the circle into
            circleBounds = new RectF();

            // size of circle and handle
            radius = 150;
            handleRadius = 10;

            // limit the counter to go up to maxTime ms
            maxTime = GlobalVar.waktuTempuh * 1000;

            // start and current time
            startTime = System.currentTimeMillis();
            currentTime = startTime;


            // the style of the background
            backgroundPaint = new Paint();
            backgroundPaint.setStyle(Paint.Style.STROKE);
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setStrokeWidth(10);
            backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
            backgroundPaint.setColor(Color.parseColor("#4D4D4D"));  // dark gray

            // the style of the 'progress'
            progressPaint = new Paint();
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setAntiAlias(true);
            progressPaint.setStrokeWidth(10);
            progressPaint.setStrokeCap(Paint.Cap.SQUARE);
            progressPaint.setColor(Color.parseColor("#00A9FF"));    // light blue

            // the style for the text in the middle
            textPaint = new TextPaint();
            textPaint.setTextSize(radius / 2);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextAlign(Paint.Align.CENTER);

            // text attributes
            textHeight = textPaint.descent() - textPaint.ascent();
            textOffset = (textHeight / 2) - textPaint.descent();




            // This will ensure the animation will run periodically
            viewHandler = new Handler();
            updateView = new Runnable() {
                @Override
                public void run() {
                    // update current time
                    currentTime = System.currentTimeMillis();

                    // get elapsed time in milliseconds and clamp between <0, maxTime>
                    progressMillisecond = (currentTime - startTime) % maxTime;

                    // get current progress on a range <0, 1>
                    progress = (double) progressMillisecond / maxTime;


                    CircularCountdown.this.invalidate();
                    viewHandler.postDelayed(updateView, 1000);
                }
            };
            viewHandler.post(updateView);
            if (currentTime >= maxTime){
                new TrackEmployeeActivity.NotifCountDown().execute("");
                viewHandler.removeCallbacksAndMessages(null);
            }
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // get the center of the view
            float centerWidth = canvas.getWidth() / 2;
            float centerHeight = canvas.getHeight() / 2;


            // set bound of our circle in the middle of the view
            circleBounds.set(centerWidth - radius,
                    centerHeight - radius,
                    centerWidth + radius,
                    centerHeight + radius);


            // draw background circle
            canvas.drawCircle(centerWidth, centerHeight, radius, backgroundPaint);

            // we want to start at -90°, 0° is pointing to the right
            canvas.drawArc(circleBounds, -90, (float) (progress * 360), false, progressPaint);

//            int menit = (((int) maxTime - (int) progressMillisecond / 100) / 1000) / 60;
//            int detik = (((int) maxTime - (int) progressMillisecond / 100) % 1000) / 60;
//
//            int menit = (((int)maxTime - (int)progressMillisecond)/1000)/60;
//            int detik = (((int)maxTime - (int)progressMillisecond)/1000)%60;

            int menit = (((int)maxTime - (int)progressMillisecond)/1000)/60;
            int detik = (((int)maxTime - (int)progressMillisecond)/1000)%60;


            // display text inside the circle
//            canvas.drawText((double)(maxTime - progressMillisecond/100)/10 + "S",
//                    centerWidth,
//                    centerHeight + textOffset,
//                    textPaint);
            canvas.drawText(menit + ":" + detik,
                    centerWidth,
                    centerHeight + textOffset,
                    textPaint);

            // draw handle or the circle
            canvas.drawCircle((float) (centerWidth + (Math.sin(progress * 2 * Math.PI) * radius)),
                    (float) (centerHeight - (Math.cos(progress * 2 * Math.PI) * radius)),
                    handleRadius,
                    progressPaint);
        }

    }
    public class NotifCountDown extends AsyncTask<String, Void, List<POSTResponse>> {
        private String URLRegister = new GlobalVar().hostAPI + "/sendhasarrive";
        private final String LOG_TAG = MainActivity.class.getSimpleName();

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
            System.out.println("nama by get id " +berita.getString("name"));

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

                Uri builtUri = Uri.parse(URLRegister).buildUpon()
                        .appendQueryParameter("id", GlobalVar.idOrder)
                        .build();


                URL url = new URL(builtUri.toString());

                Log.i(TrackEmployeeActivity.class.getSimpleName(), "url uid " + url);
                //URL url = new URL(URLServiceRepair );

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
//                Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
            }
        }
    }
}
