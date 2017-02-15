package com.aisautocare.mobile.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import info.androidhive.firebasenotifications.R;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name, phone, email, password;
    private LinearLayout addressLayout;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.profile_name_edit_text);
        phone = (EditText) findViewById(R.id.profile_phone_edit_text);
        email = (EditText) findViewById(R.id.profile_email_edit_text);
        password = (EditText) findViewById(R.id.profile_password_edit_text);
        addressLayout = (LinearLayout) findViewById(R.id.profile_address_layout);
        saveButton = (Button) findViewById(R.id.profile_save_button);

    }
}
