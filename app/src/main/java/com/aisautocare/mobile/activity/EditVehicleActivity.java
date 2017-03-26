package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.model.VehicleBrand;
import com.aisautocare.mobile.model.VehicleType;
import com.aisautocare.mobile.util.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

public class EditVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private MaterialBetterSpinner vehicleTypeSpinner, vehicleBrandSpinner, vehicleBrandTypeSpinner;
    private EditText vehicleYearEditText;
    private Button vehicleSubmitButton;

    private static String[] VEHICLE_TYPE = {"Mobil", "Motor"};
    private ArrayList<VehicleBrand> vehicleBrands = new ArrayList<VehicleBrand>();
    private ArrayList<String> brandNames = new ArrayList<String>();
    private ArrayList<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
    private ArrayList<String> typeNames = new ArrayList<String>();

    /* vehicle data variables */
    String wheel;
    String idBrand;
    String idType;
    String year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vehicleTypeSpinner = (MaterialBetterSpinner) findViewById(R.id.edit_vehicle_type_spinner);
        vehicleBrandSpinner = (MaterialBetterSpinner) findViewById(R.id.edit_vehicle_manufacture_spinner);
        vehicleBrandTypeSpinner = (MaterialBetterSpinner) findViewById(R.id.edit_vehicle_manufacture_type_spinner);
        vehicleYearEditText = (EditText) findViewById(R.id.edit_vehicle_year_edit_text);
        vehicleSubmitButton = (Button) findViewById(R.id.edit_vehicle_submit_button);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, VEHICLE_TYPE);
        vehicleTypeSpinner.setAdapter(arrayAdapter);

        /* Get vehicle id */
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES, Context.MODE_PRIVATE);
        final String value = sharedPreferences.getString("idVehicleSelected", "");
        System.out.println("ada mobel dengan id " + value);

        /* Vehicle id validation not empty */
        if (!value.equals("")) {
            System.out.println("ada mobil broo");
            String link = "/getvehiclebyid?id=" + value;

            /* Get vehicle data */
            RestClient.get(link, null, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    System.out.println("error" + responseString);
                    Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data tipe kendaraan", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject data) {

                    try {
                        wheel = data.getString("wheel");
                        idBrand = data.getString("brand_id");
                        idType = data.getString("ref_vehicle_type_id");
                        year = data.getString("year");

                        /* Set vehicle type spinner */
                        if (wheel == "4") {
                            vehicleTypeSpinner.setSelection(0);
                        } else if (data.getString("wheel") == "2") {
                            vehicleTypeSpinner.setSelection(1);
                        }

//                        vehicleBrandSpinner.setSelection();
//                        vehicleBrandTypeSpinner.setSelection();
//                        vehicleYearEditText.setText();

                        System.out.println("bisa sampe sini");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } // end if

        /* Initialize brands data */
        RestClient.get("/vehiclebrand?wheel=" + wheel, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + responseString);
                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                /* Set vehicle brand spinner */
                try {
                    JSONArray arrayBrands = data.getJSONArray("data");

                    int selectedBrand = 0;

                    for (int i = 0; i < arrayBrands.length(); i++) {
                        JSONObject brand = arrayBrands.getJSONObject(i);
                        vehicleBrands.add(new VehicleBrand(brand));
                        if (idBrand == vehicleBrands.get(i).getId()) {
                            selectedBrand = i;
                        }
                        brandNames.add(vehicleBrands.get(i).getName());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
                    vehicleBrandSpinner.setAdapter(arrayAdapter);
                    vehicleBrandSpinner.setSelection(selectedBrand);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        /* Initialize brand types data */
        RestClient.get("vehicletype?ref_brand_id=" + idBrand, null, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                System.out.println("error" + responseString);
                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data model kendaraan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                /* Set vehicle brand type spinner */
                try {
                    JSONArray arrayType = data.getJSONArray("data");

                    int selectedType = 0;

                    for (int i = 0; i < arrayType.length(); i++) {
                        JSONObject brand = arrayType.getJSONObject(i);
                        vehicleTypes.add(new VehicleType(brand));
                        if (idBrand == vehicleTypes.get(i).getId()) {
                            selectedType = i;
                        }
                        typeNames.add(vehicleTypes.get(i).getType());
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
                    vehicleBrandTypeSpinner.setAdapter(arrayAdapter);
                    vehicleBrandTypeSpinner.setSelection(selectedType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        /* Set vehicle year edit text */
        vehicleYearEditText.setText(year);

//        vehicleSubmitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String link = "/getvehiclebyid?id=" + value;
//                RestClient.get(link, null, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                        System.out.println("error" + responseString);
//                        Toast.makeText(EditVehicleActivity.this, "Gagal merubah data", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                        // Update vehicle data
//                        RequestParams params = new RequestParams();
//                        params.put("user_id", GlobalVar.idCustomerLogged);
//                        params.put("ref_vehicle_type_id", refVehicleTypeId);
//                        params.put("note", "");
//                        params.put("year", vehicleYearEditText.getText());
//                        System.out.println("sukses ngirim broo");
//
//                        try {
////                        JSONObject object = data.getJSONObject("data");
//
//
//                            data.getString("brand");
//                            data.getString("name");
//                            data.getString("year");
//
//                            vehicleTypeSpinner.setSelection();
//                            vehicleBrandSpinner.setSelection();
//                            vehicleBrandTypeSpinner.setSelection();
//                            vehicleYearEditText.setText();
//
//                            System.out.println("bisa sampe sini");
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                });
//            }
//        });
    }
}
