package com.aisautocare.mobile.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.firebasenotifications.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText name, email, password, passwordRetype, phone;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = (EditText) findViewById(R.id.register_name_edit_text);
        email = (EditText) findViewById(R.id.register_email_edit_text);
        password = (EditText) findViewById(R.id.register_password_edit_text);
        passwordRetype = (EditText) findViewById(R.id.register_password_retype_edit_text);
        phone = (EditText) findViewById(R.id.register_phone_edit_text);
        registerButton = (Button) findViewById(R.id.register_button);
    }
}
