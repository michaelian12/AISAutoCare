package com.aisautocare.mobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.model.VehicleBrand;
import com.aisautocare.mobile.util.RestClient;
import com.akexorcist.googledirection.model.Line;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import info.androidhive.firebasenotifications.R;

/**
 * Created by ini on 2017/02/14.
 */

public class RatingActivity extends AppCompatActivity {
    private Button btnSubmit;
    private EditText editText;
    private RatingBar ratingBar;
    private ProgressDialog pd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rating);
        editText = (EditText) findViewById(R.id.comment_edit_text);
        ratingBar = (RatingBar) findViewById(R.id.dialog_rating_bar);
        btnSubmit = (Button) findViewById(R.id.rating_submit_button);

//        Progress dialog
        pd = new ProgressDialog(RatingActivity.this);
        pd.setMessage("loading");
        pd.setCanceledOnTouchOutside(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get rating data
                String review = editText.getText().toString();
                int rating = ratingBar.getNumStars();

                String link = "/add_review";
                RequestParams params = new RequestParams();

                params.put("order_id", GlobalVar.idOrder);
                params.put("bengkel_id", GlobalVar.bengkelID);
                params.put("penilaian", rating);
                params.put("feedback", review);
                Log.i("Rating", "Log param " + params.toString());

                RestClient.post(link, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        try {
                            pd.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        System.out.println("error" + responseString);
                        pd.hide();
                        Toast.makeText(RatingActivity.this, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject data) {
                        // Pull out the first event on the public timeline
                        pd.hide();
                        try {
                            JSONArray arrayBrands = data.getJSONArray("data");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        pd.hide();
                        Toast.makeText(RatingActivity.this, "Terimakasih telah menggunakan layanan kami",
                                Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RatingActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        finish();
                    }
                });
            }
        });

    }
}
