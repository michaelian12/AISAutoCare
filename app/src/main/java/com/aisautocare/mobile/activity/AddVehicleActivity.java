package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

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
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

public class AddVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static String[] VEHICLE_TYPE= {"Mobil","Motor"};

    private ArrayList<VehicleBrand> vehicleBrands = new ArrayList<VehicleBrand>();
    private ArrayList<String> brandNames = new ArrayList<String>();

    private ArrayList<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
    private ArrayList<String> typeNames = new ArrayList<String>();

    private static String[] CAR_MANUFACTURER = {"TOYOTA","Honda","Daihatsu","Suzuki","Mitsubishi","Nissan"};
    String [] CAR_MANUFACTURER_TYPE;
    private static String[] BIKE_MANUFACTURER = {"Bajaj", "Ducati", "Harley Davidson", "Honda", "Kawasaki", "KTM", "Piaggio", "Suzuki", "TVS",
            "Yamaha", "Others"};
    private static String[] TRANSMISSION = {"Auto", "Manual"};
    private static String[] YEAR = {"2001", "2010"};
    private ArrayList<String> years = new ArrayList<String>();
    private int thisYear = Calendar.getInstance().get(Calendar.YEAR);

    private MaterialBetterSpinner vehicleTypeSpinner;
    private MaterialBetterSpinner vehicleManufactureSpinner;
    private MaterialBetterSpinner vehicleManufactureTypeSpinner;
    private MaterialBetterSpinner vehicleYearSpinner;

    private Button vehicleSubmitButton;

    private int chooseBefore = -1;
    private int selectedManufactureType;
    private int selectedManufacture;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleTypeSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_type);
        vehicleManufactureSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_manufacture_spinner);
        vehicleManufactureTypeSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_manufacture_type_spinner);
        //vehicleYearSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_year_spinner);
        vehicleSubmitButton = (Button) findViewById(R.id.vehicle_submit_button);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Set Vehicle Type Spinner */
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, VEHICLE_TYPE);
        vehicleTypeSpinner.setAdapter(arrayAdapter);
        pd = new ProgressDialog(AddVehicleActivity.this);

        pd.setMessage("loading");
        vehicleTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String link = GlobalVar.hostAPI;

                if (position == 0) {
                    link = "/vehiclebrand?wheel=4";
                } else if (position == 1) {
                    link = "/vehiclebrand?wheel=2";
                }

                RestClient.get(link, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        pd.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray data) {
                        // If the response is JSONObject instead of expected JSONArray
                        pd.hide();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        System.out.println("error" + responseString);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                        // Pull out the first event on the public timeline
                        JSONObject firstEvent = null;
                        try {
                            JSONArray arrayBrands = data.getJSONArray("data");
                            brandNames.clear();
                            for (int i = 0; i < arrayBrands.length(); i++){
                                JSONObject brand = arrayBrands.getJSONObject(i);
                                vehicleBrands.add(new VehicleBrand(brand));
                                brandNames.add(vehicleBrands.get(i).getName());

                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
                            vehicleManufactureSpinner.clearListSelection();

                            vehicleManufactureSpinner.setAdapter(arrayAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.hide();
                    }
                });
            }
        });

        /* Set Vehicle Manufacture Spinner */
//        ArrayAdapter<String> brandSpinnerAdapter = new ArrayAdapter<VehicleBrand>(this, android.R.layout.simple_dropdown_item_1line, brandNames);
//        brandSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        vehicleManufactureSpinner.setAdapter(brandSpinnerAdapter);

        vehicleManufactureSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String link = "/vehicletype?ref_brand_id=" + vehicleBrands.get(i).getId();

                Log.i("addvehicle", "alamat tipe brand " + link);
                RestClient.get(link, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();


                        pd.show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray data) {
                        // If the response is JSONObject instead of expected JSONArray

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        System.out.println("error" + responseString);
                        pd.hide();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                        // Pull out the first event on the public timeline
                        JSONObject firstEvent = null;
                        try {
                            JSONArray arrayType = data.getJSONArray("data");
                            typeNames.clear();
                            vehicleTypes.clear();
                            for (int i = 0; i < arrayType.length(); i++){
                                JSONObject type = arrayType.getJSONObject(i);
                                vehicleTypes.add(new VehicleType(type));
                                typeNames.add(vehicleTypes.get(i).getType());



                            }
                            System.out.println(typeNames);
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, typeNames);
                            vehicleManufactureTypeSpinner.clearListSelection();
                            vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pd.hide();
                    }
                });
            }
        });


        /* Set Vehicle Manufacturer Model Spinner */

        vehicleManufactureTypeSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (chooseBefore == -1){
//                    new AlertDialog.Builder(AddVehicleActivity.this)
//                            .setTitle("Merk Belum Dipilih")
//                            .setMessage("Silahkan merk terlebih dahulu.")
//
//                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int which) {
//                                    // do nothing
//                                }
//                            })
//                            .setIcon(android.R.drawable.ic_dialog_alert)
//                            .show();
//                }

            }
        });

        vehicleManufactureTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedManufactureType = i;
            }
        });

        /* Set Vehicle Year Spinner */
        for (int i = 2000; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, years);
        //vehicleYearSpinner.setAdapter(arrayAdapter);

        /* Set Submit Button */
        vehicleSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String[] result = new String[0];
//                result[0] = CAR_MANUFACTURER_TYPE[selectedManufactureType];
                Intent returnIntent = new Intent();
                returnIntent.putExtra("MerkType", CAR_MANUFACTURER_TYPE[selectedManufactureType]);
                returnIntent.putExtra("Merk", CAR_MANUFACTURER[selectedManufacture]);
                GlobalVar.selectedCar = CAR_MANUFACTURER[selectedManufacture];
                GlobalVar.selectedCarType = CAR_MANUFACTURER_TYPE[selectedManufactureType];
                setResult(1, returnIntent);
                finish();
            }
        });


    }
}
