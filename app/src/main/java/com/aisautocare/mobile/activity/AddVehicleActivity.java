package com.aisautocare.mobile.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import info.androidhive.firebasenotifications.R;

import com.aisautocare.mobile.GlobalVar;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.Calendar;

public class AddVehicleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private static String[] CAR_MANUFACTURER = {"TOYOTA","Honda","Daihatsu","Suzuki","Mitsubishi","Nissan"};
    String [] CAR_MANUFACTURER_TYPE;
    private static String[] VEHICLE_TYPE= {"Mobil","Motor"};
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

        /* Set Vehicle Manufacturer Spinner */
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER);
        vehicleManufactureSpinner.setAdapter(arrayAdapter);
        vehicleManufactureSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedManufacture = i;

                if (i == 0 ){
                    CAR_MANUFACTURER_TYPE = new String[]{"Camry", "Altis", "Vios", "Agya", "Etios Valco", "Yaris", "Yaris Heykers", "Kijang Innova", "Kijang", "Sienta", "Alphard", "Avanza", "Cayla", "NAV1", "Venturer", "Vellfire", "Veloz", "Fortuner", "Land Cruiser", "Rush", "Hiace", "Hilux", "Toyota 86"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                } else if (i == 1){
                    CAR_MANUFACTURER_TYPE = new String[]{"Accord","BR-V","Brio","City","Civic","CR-V","CR-Z","Stream","Freed"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                } else if (i == 2){
                    CAR_MANUFACTURER_TYPE = new String[]{"Ayla","Ceria","Charade","Classy","Espasss","Feroza","Granmax","Luxio","Rocky","Sigra","Hijet","Taft","Sirion","Taruna","Xenia","YRV","Zebra"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                } else if (i == 3){
                    CAR_MANUFACTURER_TYPE = new String[]{"Aerio","APV","Baleno","Carry","Ertiga","Escudo","Esteem","Forza","Futura","Grand Vitara","Jimny","Karimun","Katana","Sidekick","Splash","Swift","SX-4","Vitara"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                } else if (i == 4){
                    CAR_MANUFACTURER_TYPE = new String[]{"Colt","Delica","Galant","Grandis","Kuda","L300","Lancer","Maven","Mirage","Outlander","Pajero","Strada","Triton"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                }else if (i == 5){
                    CAR_MANUFACTURER_TYPE = new String[]{"Terrano","X-Trail","Serena","Grand Livinia","March"};
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddVehicleActivity.this, android.R.layout.simple_dropdown_item_1line, CAR_MANUFACTURER_TYPE);
                    vehicleManufactureTypeSpinner.setAdapter(arrayAdapter);
                }
                if (chooseBefore != i)
                    vehicleManufactureTypeSpinner.setSelection(0);

                chooseBefore = i;
            }
        });

        /* Set Vehicle Manufacturer Model Spinner */
        vehicleManufactureTypeSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chooseBefore == -1){
                    new AlertDialog.Builder(AddVehicleActivity.this)
                            .setTitle("Merk Belum Dipilih")
                            .setMessage("Silahkan merk terlebih dahulu.")

                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

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
