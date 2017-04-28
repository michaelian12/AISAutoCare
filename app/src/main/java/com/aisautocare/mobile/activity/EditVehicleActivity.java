package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.model.VehicleBrand;
import com.aisautocare.mobile.model.VehicleType;
import com.aisautocare.mobile.util.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

public class EditVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private Spinner vehicleTypeSpinner, vehicleBrandSpinner, vehicleModelSpinner;
    private EditText vehicleYearEditText;
    private Button vehicleSubmitButton;

    private static String[] VEHICLE_TYPE = {"Mobil", "Motor"};
    private ArrayList<VehicleBrand> vehicleBrands = new ArrayList<VehicleBrand>();
    private ArrayList<String> brandNames = new ArrayList<String>();
    private ArrayList<VehicleType> vehicleModel = new ArrayList<VehicleType>();
    private ArrayList<String> modelNames = new ArrayList<String>();

    /* Spinner adapter */
    private ArrayAdapter<String> arrayTypeAdapter;
    private ArrayAdapter<String> arrayBrandAdapter;
    private ArrayAdapter<String> arrayModelAdapter;

    private ProgressDialog pd;

    /* Vehicle Data Variables */
    String wheel;
    String idBrand;
    String idModel;
    String year;

    private String link;
    private int indexSelectedType, indexSelectedBrand, indexSelectedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vehicleTypeSpinner = (Spinner) findViewById(R.id.edit_vehicle_type_spinner);
        vehicleBrandSpinner = (Spinner) findViewById(R.id.edit_vehicle_manufacture_spinner);
        vehicleModelSpinner = (Spinner) findViewById(R.id.edit_vehicle_manufacture_type_spinner);
        vehicleYearEditText = (EditText) findViewById(R.id.edit_vehicle_year_edit_text);
        vehicleSubmitButton = (Button) findViewById(R.id.edit_vehicle_submit_button);

        pd = new ProgressDialog(EditVehicleActivity.this);
        pd.setMessage("Mendapatkan data kendaraan");

        /* Get user id */
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
        String idCustomer = sharedPreferences.getString("idCustomer", "");
        System.out.println("id customer tuh " + idCustomer);

        /* Set type spinner */
        arrayTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, VEHICLE_TYPE);
        vehicleTypeSpinner.setAdapter(arrayTypeAdapter);

        vehicleBrandSpinner.setEnabled(false);
        vehicleModelSpinner.setEnabled(false);

        /* Get vehicle data */
//        RestClient.post("/getvehiclebyuserid?user_id=" + idCustomer, null, new JsonHttpResponseHandler() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                pd.show();
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//                System.out.println("error" + responseString);
//                pd.hide();
//                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                try {
//                    wheel = data.getString("wheel");
//                    idBrand = data.getString("ref_brand_id");
//                    idModel = data.getString("ref_vehicle_type_id");
//                    year = data.getString("year");
////                    String link = "";
//
//                    if (wheel.equals("4")){
////                        link = "/vehiclebrand?wheel=4";
//                        vehicleTypeSpinner.setSelection(0);
//                        vehicleTypeSpinner.setText(VEHICLE_TYPE[0]);
//                    } else if (wheel.equals("2")) {
////                        link = "/vehiclebrand?wheel=2";
//                        vehicleTypeSpinner.setSelection(1);
//                        vehicleTypeSpinner.setText(VEHICLE_TYPE[1]);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                /* Get Vehicle Brands */
//                RestClient.get("/vehiclebrand?wheel=" + wheel, null, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onStart()   {
//                        super.onStart();
//                        pd.show();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                        System.out.println("error" + responseString);
//                        pd.hide();
//                        Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                        try {
//                            JSONArray arrayBrands = data.getJSONArray("data");
//
//                            vehicleBrands.clear();
//                            brandNames.clear();
//                            vehicleModel.clear();
//                            modelNames.clear();
//
//                            vehicleBrandSpinner.setAdapter(null);
//                            vehicleBrandSpinner.setText("");
//                            vehicleModelSpinner.setAdapter(null);
//                            vehicleModelSpinner.setText("");
//
//                            for (int i = 0; i < arrayBrands.length(); i++) {
//                                JSONObject brand = arrayBrands.getJSONObject(i);
//                                vehicleBrands.add(new VehicleBrand(brand));
//                                brandNames.add(vehicleBrands.get(i).getName());
//                                if (vehicleBrands.get(i).getId().equals(idBrand)) {
//                                    indexSelectedBrand = i;
//                                }
//                            }
//
//                            arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
//                            vehicleBrandSpinner.setAdapter(arrayBrandAdapter);
//                            System.out.println(brandNames);
//                            System.out.println(arrayBrandAdapter);
//                            System.out.println("panjang array = " + vehicleBrandSpinner.length());
//                            System.out.println("index selected brand = " + indexSelectedBrand);
////                            vehicleBrandSpinner.setSelection(indexSelectedBrand);
//                            vehicleBrandSpinner.setText(brandNames.get(indexSelectedBrand));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        pd.hide();
//                    }
//                });
//
////                vehicleBrands.clear();
////                brandNames.clear();
//
////                String link = null;
////                try {
////                    link = "/vehicletype?ref_brand_id=" + data.getString("ref_brand_id");
////                } catch (JSONException e) {
////                    e.printStackTrace();
////                }
//
//                /* Get Vehicle Model */
//                RestClient.get("/vehicletype?ref_brand_id=" + idBrand, null, new JsonHttpResponseHandler() {
//                    @Override
//                    public void onStart() {
//                        super.onStart();
//                        pd.show();
//                    }
//
//                    @Override
//                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                        super.onFailure(statusCode, headers, responseString, throwable);
//                        System.out.println("error" + responseString);
//                        pd.hide();
//                        Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data model kendaraan", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                        try {
//                            JSONArray arrayType = data.getJSONArray("data");
//
//                            vehicleModel.clear();
//                            modelNames.clear();
//
//                            vehicleModelSpinner.setAdapter(null);
//                            vehicleModelSpinner.setText("");
//
//                            for (int i = 0; i < arrayType.length(); i++){
//                                JSONObject type = arrayType.getJSONObject(i);
//                                vehicleModel.add(new VehicleType(type));
//                                modelNames.add(vehicleModel.get(i).getType());
//                                if (vehicleModel.get(i).getId().equals(idModel)) {
//                                    indexSelectedModel = i;
//                                }
//                            }
//
//                            arrayModelAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, modelNames);
//                            vehicleModelSpinner.setAdapter(arrayModelAdapter);
////                            vehicleModelSpinner.setSelection(indexSelectedModel);
//                            vehicleModelSpinner.setText(modelNames.get(indexSelectedModel));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        pd.hide();
//                    }
//                });
//
//                vehicleYearEditText.setText(year);
//
//                Log.i("edit avct ", "data ORi " + data);
//
////                arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
////                vehicleBrandSpinner.setAdapter(arrayBrandAdapter);
//
//                pd.hide();
//            }
//        });

        /* Set OnClickListener */
        /* Vehicle Type Spinner */
        vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String link = "";
                indexSelectedBrand = -1;
                indexSelectedModel = -1;

                if (i == 0) {
                    link = "/vehiclebrand?wheel=4";
                } else if (i == 1) {
                    link = "/vehiclebrand?wheel=2";
                }

                /* Get Vehicle Brands */
                RestClient.get(link, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart()   {
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
                        Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                        try {
                            JSONArray arrayBrands = data.getJSONArray("data");

                            vehicleBrands.clear();
                            brandNames.clear();
                            vehicleModel.clear();
                            modelNames.clear();

                            vehicleBrandSpinner.setAdapter(null);
//                            vehicleBrandSpinner.setText("");
                            vehicleModelSpinner.setAdapter(null);
//                            vehicleModelSpinner.setText("");

                            vehicleBrandSpinner.setEnabled(true);
                            vehicleModelSpinner.setEnabled(false);

                            for (int i = 0; i < arrayBrands.length(); i++) {
                                JSONObject brand = arrayBrands.getJSONObject(i);
                                vehicleBrands.add(new VehicleBrand(brand));
                                brandNames.add(vehicleBrands.get(i).getName());
                            }

                            arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
                            vehicleBrandSpinner.setAdapter(arrayBrandAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pd.hide();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        vehicleTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
//
//            }
//        });

        /* Vehicle Brand Spinner */
        vehicleBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (indexSelectedType == -1) {
                    Toast.makeText(EditVehicleActivity.this, "Jenis kendaraan belum dipilih", Toast.LENGTH_SHORT).show();
                } else {

                    indexSelectedBrand = i;
                    indexSelectedModel = -1;
                    String link = "/vehicletype?ref_brand_id=" + vehicleBrands.get(i).getId();

                /* Get Vehicle Model */
                    RestClient.get(link, null, new JsonHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            super.onStart();
                            pd.show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            System.out.println("error" + responseString);
                            pd.hide();
                            Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data model kendaraan", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                            try {
                                JSONArray arrayType = data.getJSONArray("data");

                                vehicleModel.clear();
                                modelNames.clear();

                                vehicleModelSpinner.setAdapter(null);
//                                vehicleModelSpinner.setText("");

                                vehicleModelSpinner.setEnabled(true);

                                for (int i = 0; i < arrayType.length(); i++) {
                                    JSONObject type = arrayType.getJSONObject(i);
                                    vehicleModel.add(new VehicleType(type));
                                    modelNames.add(vehicleModel.get(i).getType());
                                }

                                arrayModelAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, modelNames);
                                vehicleModelSpinner.setAdapter(arrayModelAdapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            pd.hide();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        vehicleBrandSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

        /* Vehicle Model Spinner */
        vehicleModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                indexSelectedModel = i;
                idModel = vehicleModel.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        vehicleModelSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//        });

        /* Submit Button */
        vehicleSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = vehicleYearEditText.getText().toString();

                if(vehicleYearEditText.getText().equals("") || vehicleYearEditText.length()>4 || vehicleYearEditText.length() <4 ){
                    vehicleYearEditText.setError("Masukan tahun dengan benar");

                    return;

                }



                /* If all data full filled */
                if ((indexSelectedBrand >= 0) && (indexSelectedModel >= 0) && (year != "")) {
                    pd.setMessage("Menyimpan data kendaraan");
                    pd.show();
                    SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES, Context.MODE_PRIVATE);
                    String idVehicle = sharedPreferences.getString("idVehicle", "");
                    System.out.println("id vehicle pas get di edit" + idVehicle);
                    System.out.println("id model " + idModel);

                    // Fill params
                    RequestParams params = new RequestParams();
                    params.put("id", idVehicle);
                    params.put("user_id", GlobalVar.idCustomerLogged);
                    params.put("ref_vehicle_type_id", idModel);
                    params.put("year", vehicleYearEditText.getText());
                    System.out.println("parameter update kendaraan " + params);

                    RestClient.post("/updatevehicle", params, new JsonHttpResponseHandler() {
                        @Override
                        public void onStart() {
                            super.onStart();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                            System.out.println("error" + responseString);
                            pd.hide();
                            Toast.makeText(EditVehicleActivity.this, "Gagal mengubah data kendaraan", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                            System.out.println("data update kendaraan" +data);

                            Intent returnIntent = new Intent();
                            returnIntent.putExtra("Merk", vehicleBrands.get(indexSelectedBrand).getName());
                            returnIntent.putExtra("MerkType", vehicleModel.get(indexSelectedModel).getType());
                            GlobalVar.selectedCar = vehicleBrands.get(indexSelectedBrand).getName();
                            GlobalVar.selectedCarType = vehicleModel.get(indexSelectedModel).getType();
                            setResult(1, returnIntent);
                            pd.hide();
                            finish();
                        }
                    });
                } else {    // Not all data are full filled
                    if (indexSelectedBrand == -1) {
                        Toast.makeText(EditVehicleActivity.this, "Merk kendaraan belum diisi", Toast.LENGTH_SHORT).show();
                    }

                    if (indexSelectedModel == -1) {
                        Toast.makeText(EditVehicleActivity.this, "Model kendaraan belum diisi", Toast.LENGTH_SHORT).show();
                    }

                    if (year == "") {
                        Toast.makeText(EditVehicleActivity.this, "Tahun kendaraan belum diisi", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}