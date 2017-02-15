package com.aisautocare.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import info.androidhive.firebasenotifications.R;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static String[] CAR_MANUFACTURER = {"Audi", "BMW", "Chery", "Chevrolet", "Citroen", "Daewoo", "Daihatsu", "Datsun", "Ford", "Geely",
            "Honda", "Hyundai", "Isuzu", "Jeep", "KIA", "Mazda", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan",
            "Opel", "Peugeot", "Proton", "Renault", "Suzuki", "Tata", "Toyota", "Volkswagen", "Others"};
    private static String[] BIKE_MANUFACTURER = {"Bajaj", "Ducati", "Harley Davidson", "Honda", "Kawasaki", "KTM", "Piaggio", "Suzuki", "TVS",
            "Yamaha", "Others"};
    private static String[] TRANSMISSION = {"Auto", "Manual"};
    private static String[] YEAR = {"2001", "2010"};

    private MaterialBetterSpinner vehicleManufactureSpinner;
    private MaterialBetterSpinner vehicleTransmissionSpinner;
    private MaterialBetterSpinner vehicleYearSpinner;

    private Button vehicleSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleManufactureSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_manufacturer_spinner);
        vehicleTransmissionSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_transmission_spinner);
        vehicleYearSpinner = (MaterialBetterSpinner) findViewById(R.id.vehicle_year_spinner);
        vehicleSubmitButton = (Button) findViewById(R.id.vehicle_submit_button);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER);
        vehicleManufactureSpinner.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, TRANSMISSION);
        vehicleTransmissionSpinner.setAdapter(arrayAdapter);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, YEAR);
        vehicleYearSpinner.setAdapter(arrayAdapter);

        vehicleSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "caw";
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", result);
                setResult(1, returnIntent);
                finish();
            }
        });
    }
}
