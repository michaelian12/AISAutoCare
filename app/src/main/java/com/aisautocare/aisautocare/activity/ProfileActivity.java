package com.aisautocare.aisautocare.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.aisautocare.aisautocare.R;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    LinearLayout nameLayout, phoneLayout, emailLayout, passwordLayout, addressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /* Set Toolbar */
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameLayout = (LinearLayout) findViewById(R.id.user_name_profile_layout);
        phoneLayout = (LinearLayout) findViewById(R.id.user_phone_profile_layout);
        emailLayout = (LinearLayout) findViewById(R.id.user_email_profile_layout);
        passwordLayout = (LinearLayout) findViewById(R.id.user_password_profile_layout);
        addressLayout = (LinearLayout) findViewById(R.id.user_password_profile_layout);

        addressLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }
}
