package com.aisautocare.mobile.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.model.POSTResponse;
import com.aisautocare.mobile.model.User;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.firebasenotifications.R;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Toolbar toolbar;
    private EditText name, phone, email;
    private Button saveButton;

    private FirebaseAuth auth;
    public String regId;
    private String uid;

    User user = new User();

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
        saveButton = (Button) findViewById(R.id.profile_save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataEmail = email.getText().toString().trim();

                if (TextUtils.isEmpty(dataEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new ProfileActivity.POSTRegister().execute(name.getText().toString(), phone.getText().toString(), email.getText().toString());

                auth = FirebaseAuth.getInstance();
//                progressBar.setVisibility(View.VISIBLE);

            }
        });

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        new ProfileActivity.GETidbyuid().execute("");
    }

    public class POSTRegister extends AsyncTask<String, Void, List<POSTResponse>> {
        private String URLRegister = new GlobalVar().hostAPI + "/updatecustomer";
        private final String LOG_TAG = ProfileActivity.class.getSimpleName();

        private List<POSTResponse> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject customerJson = new JSONObject(jsonStr);
            //JSONArray movieArray = customerJson.getJSONArray();
//            System.out.println("movie json : " + customerJson  );
//            System.out.println("itemsarray : " + movieArray  );
            // System.out.println(" Data JSON Items" + jsonStr);
            List<POSTResponse> results = new ArrayList<>();
            JSONObject customer = customerJson;
            POSTResponse beritaModel = new POSTResponse(customer);
            results.add(beritaModel);
            return results;
        }

        @Override
        protected List<POSTResponse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);
                String id = sharedPreferences.getString("id", "");
                Log.i(TAG, "id setelah register"+ id);
                Log.i(TAG, "regid yang akan dikirim "+ regId);

//                Log.i(TAG, sharedPreferences.getAll().get("name").toString());

                user.setName(sharedPreferences.getString("name",""));
                user.setCellphone(sharedPreferences.getString("cellphone",""));
                user.setUid(auth.getCurrentUser().getUid());
                user.setEmail(sharedPreferences.getString("email",""));
                user.setType("1");

                Uri builtUri = Uri.parse(URLRegister).buildUpon()
                        .appendQueryParameter("id", id)
                        .appendQueryParameter("name", params[0])
                        .appendQueryParameter("cellphone", params[1])
                        .appendQueryParameter("email", params[2])
                        .build();

                URL url = new URL(builtUri.toString());

                Log.i(TAG, "URL register " + url);
                //URL url = new URL(URLServiceRepair );

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                System.out.println("DATA BALIKAN " + jsonStr);
                return getRepairDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(List<POSTResponse> responses) {
            //Log.i(TAG, responses.get(0).toString());
            if (responses != null) {
                //repairs.clear();
                //repairs.addAll(services);
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences(GlobalVar.MyPREFERENCES,  Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    String id = responses.get(0).getId();
//                                    editor.putString("id", id);
                editor.putString("idCustomer", responses.get(0).getId());
                editor.commit();
                System.out.println("responses ketika set adapter : " + responses.toString());
//                if (Integer.valueOf(responses.get(0).getApi_status()) == 1) {
//                    finish();
//                    Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getApplicationContext().startActivity(intent);
//                } else {
//                    Log.e("AIS", "Error POST Order");
//                    Log.e("AIS", "API Message : " + responses.get(0).getApi_message().toString());
//                }
                //rcAdapter = new RecyclerViewAdapterBerita(getActivity(), movies);
                //adapter = new ServiceRecyclerViewAdapter();

                //rcAdapter.notifyDataSetChanged();

                //recyclerView.setAdapter(adapter);
                //adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //swipeContainer.setRefreshing(false);

                //adapter.setLoaded();

                //pageBerita++;
//                Intent intent = new Intent(getApplicationContext(), WaitOrderActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                getApplicationContext().startActivity(intent);
            }

            finish();
        }
    }

    // get customer data
    public class GETidbyuid extends AsyncTask<String, Void, List<POSTResponse>> {
        private String URLRegister = new GlobalVar().hostAPI + "/customerbyuid";
        private final String LOG_TAG = MainActivity.class.getSimpleName();

        private List<POSTResponse> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject customerJson = new JSONObject(jsonStr);
            //JSONArray movieArray = customerJson.getJSONArray();
//            System.out.println("movie json : " + customerJson  );
//            System.out.println("itemsarray : " + movieArray  );
            // System.out.println(" Data JSON Items" + jsonStr);
            List<POSTResponse> results = new ArrayList<>();

            JSONObject berita = customerJson;
            try {
                name.setText(berita.getString("name"));
                phone.setText(berita.getString("cellphone"));
                email.setText(berita.getString("email"));
            }catch (Exception e){

            }
            POSTResponse beritaModel = new POSTResponse(berita);

            JSONObject customer = customerJson;

            results.add(beritaModel);
            GlobalVar.customerName = customer.getString("name");
            GlobalVar.customerCellphone =  customer.getString("cellphone");
            GlobalVar.customerEmail =  customer.getString("email");
            return results;
        }

        @Override
        protected List<POSTResponse> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {

                Uri builtUri = Uri.parse(URLRegister).buildUpon()
                        .appendQueryParameter("uid", auth.getCurrentUser().getUid())
                        .build();


                URL url = new URL(builtUri.toString());

                Log.i(TAG, "url uid " + url);
                //URL url = new URL(URLServiceRepair );

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                System.out.println("DATA BALIKAN " + jsonStr);
                return getRepairDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }
    }
}
