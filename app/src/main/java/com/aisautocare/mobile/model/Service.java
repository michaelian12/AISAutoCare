package com.aisautocare.mobile.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;

import info.androidhive.firebasenotifications.R;

/**
 * Created by Michael on 1/5/2017.
 */

public class Service implements Parcelable {
    private String id ;
    private int imageResourceId = R.drawable.ic_engine; // service image
    private String name; // service name
    private String price = "1000"; // service price

    public Service(int imageResourceId, String name, String price) {

        this.imageResourceId = imageResourceId;
        this.name = name;
        this.price = price;
    }

    public Service(JSONObject service) throws JSONException, NoSuchFieldException, IllegalAccessException {
        //Drawable.class.getDeclaredField("ic_engine.png");
        //R.drawable.ic_engine;
        this.imageResourceId = getResId(service.getString("imageResourceId"), R.drawable.class);
        this.id = service.getString("id");
        this.name = service.getString("name");

    }

    public static final Creator<Service> CREATOR = new Creator<Service>() {
        @Override
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        @Override
        public Service[] newArray(int size) {
            return new Service[size];
        }
    };

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(String.valueOf(this.imageResourceId));
        parcel.writeString(this.name);
        parcel.writeString(this.id);
        //parcel.writeString(this.price);
    }
    protected Service(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.imageResourceId = Integer.valueOf(in.readString());
        //parcel.writeString(this.price);
    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
