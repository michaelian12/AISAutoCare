package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.Button;

import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import android.widget.LinearLayout;

import com.aisautocare.mobile.GlobalVar;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.androidhive.firebasenotifications.R;

public class TrackEmployeeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private GoogleMap mMap;
    private LinearLayout timer;
    private Button arriveButton, finishButton;

    private LinearLayout btnFinished;
//    private LinearLayout layoutButtons;
    private Firebase trackFirebase;
    private Marker customerLoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_employee);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        arriveButton = (Button) findViewById(R.id.track_arrive_button);
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
        trackFirebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String lat = map.get("lat").toString();
                String lon = map.get("lon").toString();
                LatLng locCurrent = new LatLng(Double.valueOf(lat), Double.valueOf(lon));
                customerLoc.setPosition(locCurrent);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
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
        LatLng start = new LatLng(Double.valueOf(getIntent().getStringExtra("latBengkel")), Double.valueOf(getIntent().getStringExtra("lonBengkel")));
        LatLng end = new LatLng(GlobalVar.selectedLat,GlobalVar.selectedLon);

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


                            final ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
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
                            timer.addView(new CircularCountdown(TrackEmployeeActivity.this));
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

    private static class CircularCountdown extends View {

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
}
