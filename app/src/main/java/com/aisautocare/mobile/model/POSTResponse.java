package com.aisautocare.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ini on 2017/01/22.
 */

public class POSTResponse implements Parcelable {
    private String api_status;
    private String api_message;

    public String getApi_status() {
        return api_status;
    }

    public void setApi_status(String api_status) {
        this.api_status = api_status;
    }

    public String getApi_message() {
        return api_message;
    }

    public void setApi_message(String api_message) {
        this.api_message = api_message;
    }
    public POSTResponse(JSONObject service) throws JSONException, NoSuchFieldException, IllegalAccessException {
        //Drawable.class.getDeclaredField("ic_engine.png");
        //R.drawable.ic_engine;
        //Field idField = Drawable.class.getDeclaredField(service.getString("imageResourceId").toString());
        //this.imageResourceId = idField.getInt(idField);
        this.api_message = service.getString("api_message");
        this.api_status = service.getString("api_status");

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
