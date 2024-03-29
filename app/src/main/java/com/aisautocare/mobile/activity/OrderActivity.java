package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.fragment.RepairFragment;
import com.aisautocare.mobile.model.Order;
import com.aisautocare.mobile.model.POSTResponse;
import com.aisautocare.mobile.model.ServiceType;
import com.aisautocare.mobile.model.VehicleBrand;
import com.aisautocare.mobile.model.VehicleType;
import com.aisautocare.mobile.util.RestClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
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

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

public class OrderActivity extends AppCompatActivity {

    private Toolbar toolbar;

    // Layout
    private LinearLayout subServiceLayout, addressLayout, dateLayout, paymentMethodLayout;

    // Text View
    private TextView subLayanan, address, date, paymentMethod;

    // Button
    private Button orderButton;

    // konstanta untuk mendeteksi hasil balikan dari place picker
    private int PLACE_PICKER_REQUEST = 1;
    private Context mContext;
    private Place place;
    private LatLng selectedLocation;
    private FirebaseAuth auth;
    final String[] selectedIdService = {new String()};

    private ProgressDialog pd;

    /* Initial Value */
    private String[] subService = new String[0];
    private String[] idService = new String[0];

    ArrayList<ServiceType> serviceTypes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        auth = FirebaseAuth.getInstance();
        /* Get Intent */
        final String service = getIntent().getStringExtra("service");

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Layout */
        subServiceLayout = (LinearLayout) findViewById(R.id.order_sub_service_layout);
        addressLayout = (LinearLayout) findViewById(R.id.order_address_layout);
        dateLayout = (LinearLayout) findViewById(R.id.order_date_layout);
        paymentMethodLayout = (LinearLayout) findViewById(R.id.order_payment_method_layout);

        /* Text View */
        subLayanan = (TextView) findViewById(R.id.order_sub_service_text_view);
        address = (TextView) findViewById(R.id.order_address_text_view);
        date = (TextView) findViewById(R.id.order_date_text_view);
        paymentMethod = (TextView) findViewById(R.id.order_payment_method_text_view);

        /* Button */
        orderButton = (Button) findViewById(R.id.order_button);

//      progress dialog
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("Loading...");


        int idParent = 0;
        if (service.toLowerCase().contains("aki")) {

//            subService = new String[]{"Cek", "Ganti", "Stroom Aki"};
//            idService = new String[]{"10", "12", "13"};
            idParent = 1;
        } else if (service.toLowerCase().contains("cuci")) {
//            subService = new String[]{"Cuci Cepat", "Cuci Lengkap"};
//            idService = new String[]{"30", "31"};
            idParent = 2;
        } else if (service.toLowerCase().contains("ban")) {
//            subService = new String[]{"Tambal", "Ganti", "Kirim Bensin"};
//            idService = new String[]{"1", "18", "19"};
            idParent = 3;
        } else if (service.toLowerCase().contains("bengkel")) {
//            subService = new String[]{"Cek Mesin", "Ganto Oli", "Kirim Bensin/BBM"};
//            idService = new String[]{"23", "24", "26"};
            idParent = 4;
        } else if (service.toLowerCase().contains("cadangan")) {
//            subService = new String[]{"By Driver", "No Driver"};
//            idService = new String[]{"32", "33"};
            idParent = 5;
        }else if (service.toLowerCase().contains("derek")) {
//            subService = new String[]{"Derek Mobil", "Derek Motor"};
//            idService = new String[]{"35", "36"};
            idParent = 34;
        }

        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES, Context.MODE_PRIVATE);
        String wheel = sharedPreferences.getString("wheel", "");

        RequestParams params = new RequestParams();
        params.put("parent", idParent);
        params.put("wheel", wheel );
        String link = "/getservicetype";

        RestClient.get(link, params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                try {
                    pd.show();
                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + responseString);
                pd.hide();
                Toast.makeText(OrderActivity.this, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                // Pull out the first event on the public timeline
                pd.hide();
                try {
                    JSONArray arrayType = data.getJSONArray("data");


                    subService = new String[0];
                    idService = new String[0];

                    idService = new String[arrayType.length()];
                    subService = new String[arrayType.length()];
                    for (int i = 0; i < arrayType.length(); i++){
                        JSONObject type = arrayType.getJSONObject(i);
                        serviceTypes.add(new ServiceType(type));
                        idService[i] = type.getString("id");
                        subService[i] = type.getString("sub");
                        Log.i("Order Act", "subservice ketika perulangan dan id  " + subService[i] + idService[i] );
                    }
                    subServiceLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderActivity.this);
                            LayoutInflater inflater = getLayoutInflater();
                            View convertView = (View) inflater.inflate(R.layout.list_view_sub_service, null);
                            alertDialog.setView(convertView);
                            alertDialog.setTitle("Pilih Sub Pelayanan");
                            ListView lv = (ListView) convertView.findViewById(R.id.list_sub_service);

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(OrderActivity.this, android.R.layout.simple_list_item_1, subService);
                            lv.setAdapter(adapter);
                            final AlertDialog alert = alertDialog.create();
                            alert.show();
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    alert.dismiss();
                                    subLayanan.setText(subService[i]);
                                    selectedIdService[0] = idService[i];
                                    System.out.println("id yang dipilih " + selectedIdService[0]);
                                    System.out.println("nama layanan yang dipilih " + subService[i]);
                                }
                            });
                        }
                    });

                    Log.i("Order Act", "idservice setelah perulangan " + idService.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                pd.hide();
            }
        });



//        subLayanan.setText(subLayanan.getText() + " : " + subService[0]);
        final String[] finalSubService = subService;

        /* On Click Listener */
        final String[] finalIdService = idService;


        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intentLocPick = new Intent(OrderActivity.this, LocationActivity.class);
                intentLocPick.putExtra("lat", GlobalVar.selectedLat );
                intentLocPick.putExtra("lon", GlobalVar.selectedLon );
                startActivityForResult(intentLocPick,1);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//                try {
//                    //menjalankan place picker
//                    startActivityForResult(builder.build(OrderActivity.this), PLACE_PICKER_REQUEST);
//
//                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVar.selectedSubService = subLayanan.getText().toString();
                GlobalVar.selectedService = service.toLowerCase();
                if (address.getText().toString().contains("belum") | subLayanan.getText().toString().toLowerCase().contains("pilih")){

                    new AlertDialog.Builder(OrderActivity.this)
                            .setTitle("Alamat belum dipilih atau Sub Layanan")
                            .setMessage("Silahkan pilih alamat anda terlebih dahulu.")

                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    try {
                        pd.show();
                    }catch (Exception e){

                    }

                    new OrderActivity.POSTOrder().execute("");
                }

                //finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if(resultCode == 1 ){
            selectedLocation = new LatLng(Double.valueOf(data.getStringExtra("lat")), Double.valueOf(data.getStringExtra("lon")));
            String alamat = data.getStringExtra("address");
//            address.setText(selectedLocation.toString());
            address.setText(alamat);
            GlobalVar.selectedLat = Double.valueOf(data.getStringExtra("lat"));
            GlobalVar.selectedLon = Double.valueOf(data.getStringExtra("lon"));
        }
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                place = PlacePicker.getPlace(this, data);
//                GlobalVar.selectedLat = place.getLatLng().latitude;
//                GlobalVar.selectedLon = place.getLatLng().longitude;
//
//                String toastMsg = String.format(
//                        "Place: %s,%s \n" + "Alamat: %s \n", String.valueOf(place.getLatLng().latitude), String.valueOf(place.getLatLng().longitude), place.getAddress());
//                address.setText(toastMsg);
//            }
//        }
    }

    private String URLOrder = new GlobalVar().hostAPI + "/order";

    public class POSTOrder extends AsyncTask<String, Void, List<POSTResponse>> {

        private final String LOG_TAG = RepairFragment.GETRepair.class.getSimpleName();

        private List<POSTResponse> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject movieJson = new JSONObject(jsonStr);

            GlobalVar.bengkelID = Integer.valueOf(movieJson.getString("bengkel_id"));
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
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                order.setCustomer_id("1");
                order.setOrder_date(dateFormat.format(date));
                order.setOrder_time(timeFormat.format(date));
                order.setService_date(dateFormat.format(date));
                order.setService_time(timeFormat.format(date));
                order.setService_location(address.getText().toString());
                order.setLatitude(String.valueOf(selectedLocation.latitude));
                order.setLongitude(String.valueOf(selectedLocation.longitude));
//                order.setLatitude("6");
//                order.setLongitude("6");
                order.setArea_id("14");
                order.setIs_emergency("false");
                order.setLicense_plate("AB100CA");
                order.setRef_service_id(selectedIdService[0]);
                order.setStatus("1");
                order.setMethod("3");
                order.setPayment_status("1");
                order.setCar_manufacture(GlobalVar.selectedCar);
                order.setCar_manufacture_type(GlobalVar.selectedCarType);
                order.setCar_year(GlobalVar.selectedCarYear);
                order.setIdservice(selectedIdService[0]);

                GlobalVar.order = order;

                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
                String idCustomer = sharedPreferences.getString("idCustomer", "");
                System.out.println("SELECTED SERVIVCE " + selectedIdService[0]);
                Uri builtUri = Uri.parse(URLOrder).buildUpon()
                        .appendQueryParameter("customer_id", idCustomer)
                        .appendQueryParameter("order_date", order.getOrder_date())
                        .appendQueryParameter("order_time", order.getOrder_time())
                        .appendQueryParameter("service_date", order.getService_date())
                        .appendQueryParameter("service_time", order.getService_time())
                        .appendQueryParameter("service_location", order.getService_location())
                        .appendQueryParameter("latitude", order.getLatitude())
                        .appendQueryParameter("longitude", order.getLongitude())
                        .appendQueryParameter("area_id", order.getArea_id())
                        .appendQueryParameter("is_emergency", order.getIs_emergency())
                        .appendQueryParameter("license_plate", order.getLicense_plate())
                        .appendQueryParameter("ref_service_id", order.getRef_service_id())
                        .appendQueryParameter("status", order.getStatus())
                        .appendQueryParameter("method", order.getMethod())
                        .appendQueryParameter("payment_status", order.getPayment_status())
                        .appendQueryParameter("car_manufacture", order.getCar_manufacture())
                        .appendQueryParameter("car_manufacture_type", order.getCar_manufacture_type())
                        .appendQueryParameter("car_year", order.getCar_year())
                        .appendQueryParameter("idservice", order.getIdservice())
                        .build();

                URL url = new URL(builtUri.toString());


                //URL url = new URL(URLServiceRepair );
                Log.e("URl", url.toString());
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

            if (responses != null) {
                //repairs.clear();
                //repairs.addAll(services);
                pd.hide();
                System.out.println("responses ketika set adapter : " + responses.toString());
                GlobalVar.idOrder = responses.get(0).getId();
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
                Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }
}
