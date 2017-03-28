package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.model.VehicleBrand;
import com.aisautocare.mobile.model.VehicleType;
import com.aisautocare.mobile.util.RestClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

import static android.R.attr.value;

public class EditVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private MaterialBetterSpinner vehicleTypeSpinner, vehicleBrandSpinner, vehicleModelSpinner;
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
    private String idModel;

    private int selectedBrand, selectedModel;

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
        vehicleModelSpinner = (MaterialBetterSpinner) findViewById(R.id.edit_vehicle_manufacture_type_spinner);
        vehicleYearEditText = (EditText) findViewById(R.id.edit_vehicle_year_edit_text);
        vehicleSubmitButton = (Button) findViewById(R.id.edit_vehicle_submit_button);

        pd = new ProgressDialog(EditVehicleActivity.this);
        pd.setMessage("Mendapatkan data kendaraan");

        /* Get vehicle id */
        SharedPreferences sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES, Context.MODE_PRIVATE);
        final String id = sharedPreferences.getString("idVehicleSelected", "");
        System.out.println("ada mobil dengan id " + id);

        arrayTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, VEHICLE_TYPE);
        vehicleTypeSpinner.setAdapter(arrayTypeAdapter);
        SharedPreferences shared = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
        String idCustomer = (shared.getString("idCustomer", ""));
        String link = "/getvehiclebyuserid?user_id=" + idCustomer;
//        vehicleTypeSpinner.setSelected(true);
        vehicleTypeSpinner.setListSelection(1);
//        vehicleTypeSpinner.setSelection(1);
        vehicleTypeSpinner.setText("Mobil");
        try {
            pd.show();
        }catch (Exception e){
            System.out.println(e);
        }

        RestClient.post(link, null, new JsonHttpResponseHandler() {
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
                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                JSONObject arrayBrands = data;
                try {
                    System.out.println("bisa try");
                    String link = "";
                    if (data.getString("wheel").equals("4")){
                        System.out.println("masuk if");
//                        vehicleTypeSpinner.setSelection(0);
                        vehicleTypeSpinner.setText("Mobil");
                        link = "/vehiclebrand?wheel=4";
                        vehicleBrandSpinner.setText(data.getString("brand"));
                        vehicleModelSpinner.setText(data.getString("name"));
                        vehicleYearEditText.setText(data.getString("year"));
                    }else{
//                        vehicleTypeSpinner.setSelection(1);
                        vehicleTypeSpinner.setText("Motor");
                        link = "/vehiclebrand?wheel=2";
                    }
                    RestClient.get(link, null, new JsonHttpResponseHandler() {
                        @Override
                        public void onStart()   {
                            super.onStart();
                            pd.show();
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

                                vehicleBrandSpinner.setAdapter(null);
                                vehicleModelSpinner.setAdapter(null);

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                vehicleBrands.clear();
                brandNames.clear();

                String link = null;
                try {
                    link = "/vehicletype?ref_brand_id=" + data.getString("ref_brand_id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

                            for (int i = 0; i < arrayType.length(); i++){
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

                Log.i("edit avct ", "data ORi " + data);

                arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
                vehicleBrandSpinner.setAdapter(arrayBrandAdapter);

                pd.hide();
            }
        });
        /* Set spinnner OnClickListener */
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
                    public void onStart()   {
                        super.onStart();
                        pd.show();
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

                            vehicleBrandSpinner.setAdapter(null);
                            vehicleModelSpinner.setAdapter(null);

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
        });

        vehicleBrandSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBrand = i;
                String link = "/vehicletype?ref_brand_id=" + vehicleBrands.get(i).getId();

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

                            for (int i = 0; i < arrayType.length(); i++){
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
        });

        vehicleModelSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedModel = i;
                idModel = vehicleModel.get(i).getId();
            }
        });

        /* Submit button OnClickListener */
        vehicleSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fill params
                RequestParams params = new RequestParams();
                params.put("id", value);
                params.put("user_id", GlobalVar.idCustomerLogged);
                params.put("ref_vehicle_type_id", idModel);
                params.put("year", vehicleYearEditText.getText());

                RestClient.get("/updatevehicle", params, new JsonHttpResponseHandler() {
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
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("Merk", vehicleBrands.get(selectedBrand).getName());
                        returnIntent.putExtra("MerkType", vehicleModel.get(selectedModel).getType());
                        GlobalVar.selectedCar = vehicleBrands.get(selectedBrand).getName();
                        GlobalVar.selectedCarType = vehicleModel.get(selectedModel).getType();
                        setResult(1, returnIntent);
                        finish();
                    }
                });
            }
        });

        /* Vehicle id validation not empty */
//        if (!id.equals("")) {
//            String link = "/getvehiclebyid?id=" + id;
//
//            /* Get vehicle data */
//            RestClient.get(link, null, new JsonHttpResponseHandler() {
//                @Override
//                public void onStart() {
//                    super.onStart();
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    super.onFailure(statusCode, headers, responseString, throwable);
//                    System.out.println("error" + responseString);
//                    Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data tipe kendaraan", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                    try {
//                        wheel = data.getString("wheel");
//                        idBrand = data.getString("brand_id");
//                        idType = data.getString("ref_vehicle_type_id");
//                        year = data.getString("year");
//
//                        /* Set vehicle type spinner */
//                        if (wheel == "4") {
//                            vehicleTypeSpinner.setSelection(0);
//                            System.out.println("spinnernya di set mobil");
//                        } else if (wheel == "2") {
//                            vehicleTypeSpinner.setSelection(1);
//                            System.out.println("spinnernya di set motor");
//                        }
//
//                        /* Initialize brands data */
//                        RestClient.get("/vehiclebrand?wheel=" + wheel, null, new JsonHttpResponseHandler() {
//                            @Override
//                            public void onStart() {
//                                super.onStart();
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                super.onFailure(statusCode, headers, responseString, throwable);
//                                System.out.println("error" + responseString);
//                                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data merk kendaraan", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                                try {
//                                    JSONArray arrayBrands = data.getJSONArray("data");
////
//                                    int selectedBrand = 0;
//
//                                    for (int i = 0; i < arrayBrands.length(); i++) {
//                                        JSONObject brand = arrayBrands.getJSONObject(i);
//                                        vehicleBrands.add(new VehicleBrand(brand));
//                                        if (idBrand == vehicleBrands.get(i).getId()) {
//                                            selectedBrand = i;
//                                        }
//                                        brandNames.add(vehicleBrands.get(i).getName());
//                                    }
//
//                                    ArrayAdapter<String> arrayBrandAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, brandNames);
//                                    vehicleBrandSpinner.setAdapter(arrayBrandAdapter);
//
//                                    /* Set vehicle brand spinner */
//                                    vehicleBrandSpinner.setSelection(selectedBrand);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        /* Initialize brand types data */
//                        RestClient.get("vehicletype?ref_brand_id=" + idBrand, null, new JsonHttpResponseHandler() {
//                            @Override
//                            public void onStart() {
//                                super.onStart();
//                            }
//
//                            @Override
//                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                                super.onFailure(statusCode, headers, responseString, throwable);
//                                System.out.println("error" + responseString);
//                                Toast.makeText(EditVehicleActivity.this, "Gagal mendapatkan data model kendaraan", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
//                                try {
//                                    JSONArray arrayType = data.getJSONArray("data");
//
//                                    int selectedType = 0;
//
//                                    for (int i = 0; i < arrayType.length(); i++) {
//                                        JSONObject brand = arrayType.getJSONObject(i);
//                                        vehicleTypes.add(new VehicleType(brand));
//                                        if (idType == vehicleTypes.get(i).getId()) {
//                                            selectedType = i;
//                                        }
//                                        typeNames.add(vehicleTypes.get(i).getType());
//                                    }
//
//                                    ArrayAdapter<String> arrayTypeAdapter = new ArrayAdapter<String>(EditVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, typeNames);
//                                    vehicleBrandTypeSpinner.setAdapter(arrayTypeAdapter);
//
//                                    /* Set vehicle brand type spinner */
//                                    vehicleBrandTypeSpinner.setSelection(selectedType);
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        /* Initialize vehicle year data */
//                        vehicleYearEditText.setText(year);
//
//                        pd.hide();
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        } // end if
    }
}
