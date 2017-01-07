package com.aisautocare.aisautocare.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.aisautocare.aisautocare.R;

import java.util.ArrayList;
import java.util.List;

public class AddVehicleActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Set Spinner */
        spinner = (Spinner) findViewById(R.id.manufacturer_spinner);
        spinner.setOnItemSelectedListener(this);
        List<String> manufacturers = new ArrayList<String>();
        manufacturers.add("Audi");
        manufacturers.add("BMW");
        manufacturers.add("Chevrolet");
        manufacturers.add("Daihatsu");
        manufacturers.add("Datsun");
        manufacturers.add("Ford");
        manufacturers.add("Honda");
        manufacturers.add("Hyundai");
        manufacturers.add("Isuzu");
        manufacturers.add("KIA");
        manufacturers.add("Mazda");
        manufacturers.add("Mercedes-Benz");
        manufacturers.add("Mitsubishi");
        manufacturers.add("Nissan");
        manufacturers.add("Peugeot");
        manufacturers.add("Suzuki");
        manufacturers.add("Toyota");
        manufacturers.add("Volkswagen");
        manufacturers.add("Other");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, manufacturers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
