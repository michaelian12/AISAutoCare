package com.aisautocare.aisautocare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;

import com.aisautocare.aisautocare.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static String[] CAR_MANUFACTURER = {"Audi", "BMW", "Chery", "Chevrolet", "Citroen", "Daewoo", "Daihatsu", "Datsun", "Ford", "Geely",
                                        "Honda", "Hyundai", "Isuzu", "Jeep", "KIA", "Mazda", "Mercedes-Benz", "Mini", "Mitsubishi", "Nissan",
                                        "Opel", "Peugeot", "Proton", "Renault", "Suzuki", "Tata", "Toyota", "Volkswagen", "Others"};
    private static String[] BIKE_MANUFACTURER = {"Bajaj", "Ducati", "Harley Davidson", "Honda", "Kawasaki", "KTM", "Piaggio", "Suzuki", "TVS",
                                        "Yamaha", "Others"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.manufacturer_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);
    }
}
