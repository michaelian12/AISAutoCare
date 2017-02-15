package com.aisautocare.mobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.akexorcist.googledirection.model.Line;

import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/02/14.
 */

public class RatingActivity extends AppCompatActivity {
    private Button btnSubmit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rating);
        btnSubmit = (Button) findViewById(R.id.rating_submit_button);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RatingActivity.this, "Terimakasih telah menggunakan layanan kami",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });

    }
}
