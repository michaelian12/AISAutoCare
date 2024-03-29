package com.aisautocare.mobile.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.firebasenotifications.R;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.adapter.ServiceRecyclerViewAdapter;
import com.aisautocare.mobile.model.Service;

import org.json.JSONArray;
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

/**
 * Created by Michael on 1/14/2017.
 */

public class CareFragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ServiceRecyclerViewAdapter adapter;
    ArrayList<Service> cares = new ArrayList<Service>();
    public CareFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.list_view, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);


        cares.add(new Service(R.drawable.ic_aki, "Ganti Aki/Acuu", "Rp 40.000"));
        cares.add(new Service(R.drawable.ic_car_wash, "Cuci/Salon Mobil", "Rp 60.000"));
        cares.add(new Service(R.drawable.ic_tire_repair, "Ganti Ban", "Rp 140.000"));
        cares.add(new Service(R.drawable.ic_emergency, "Bengkel Darurat", "Rp 140.000"));
        cares.add(new Service(R.drawable.ic_backup_car, "Mobil Cadangan", "Rp 140.000"));
        cares.add(new Service(R.drawable.ic_towing, "Derek", "Rp 140.000"));


        adapter = new ServiceRecyclerViewAdapter(getActivity(), cares);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        //new CareFragment.GETCare().execute("");
        return rootView;
    }

    private String URLServiceCare = new GlobalVar().hostAPI + "/service_type?category=3";
    public class GETCare extends AsyncTask<String, Void, List<Service>> {

        private final String LOG_TAG = CareFragment.GETCare.class.getSimpleName();

        private List<Service> getRepairDataFromJson(String jsonStr) throws JSONException, NoSuchFieldException, IllegalAccessException {
            //jsonStr = jsonStr.substring(23);
//            jsonStr = jsonStr.substring(23, jsonStr.length()-3);
//            System.out.println("JSON STR : " + jsonStr);
            JSONObject movieJson = new JSONObject(jsonStr);
            JSONArray movieArray = movieJson.getJSONArray("data");
//            System.out.println("movie json : " + movieJson  );
//            System.out.println("itemsarray : " + movieArray  );
            // System.out.println(" Data JSON Items" + jsonStr);
            List<Service> results = new ArrayList<>();

            for(int i = 0; i < movieArray.length(); i++) {
                JSONObject berita = movieArray.getJSONObject(i);
                Service beritaModel = new Service(berita);
                results.add(beritaModel);
            }

            return results;
        }

        @Override
        protected List<Service> doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonStr = null;

            try {


//                Uri builtUri = Uri.parse(BASE_URL).buildUpon()
//                        .appendQueryParameter(SORT_BY_PARAM, params[0])
//                        .appendQueryParameter(API_KEY_PARAM, getString(R.string.tmdb_api_key))
//                        .build();

//                URL url = new URL(builtUri.toString());
                URL url = new URL(URLServiceCare );

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
        protected void onPostExecute(List<Service> services) {

            if (services != null) {
                cares.clear();
                cares.addAll(services);
                System.out.println("Service ketika set adapter : " +  services.toString());

                //rcAdapter = new RecyclerViewAdapterBerita(getActivity(), movies);
                //adapter = new ServiceRecyclerViewAdapter();

                //rcAdapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);
                //swipeContainer.setRefreshing(false);

                //adapter.setLoaded();

                //pageBerita++;
            }
        }
    }
}
