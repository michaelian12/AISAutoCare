package com.aisautocare.mobile.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ini on 2017/02/26.
 */

public class User implements Parcelable {
    private String name;
    private String         email;
    private String cellphone;
    private String         address;
    private String ref_occupation_id;
    private String         latitude;
    private String longitude;
    private String         ref_area_id;
    private String user_id;
    private String         device_id;
    private String uid;
    private String         type;

    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        cellphone = in.readString();
        address = in.readString();
        ref_occupation_id = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        ref_area_id = in.readString();
        user_id = in.readString();
        device_id = in.readString();
        uid = in.readString();
        type = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRef_occupation_id() {
        return ref_occupation_id;
    }

    public void setRef_occupation_id(String ref_occupation_id) {
        this.ref_occupation_id = ref_occupation_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRef_area_id() {
        return ref_area_id;
    }

    public void setRef_area_id(String ref_area_id) {
        this.ref_area_id = ref_area_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public User()  {


    }
    public User(JSONObject user) throws JSONException {
        name = user.getString("name");
                email = user.getString("email");
        cellphone = user.getString("cellphone");
                address= user.getString("address");
        ref_occupation_id= user.getString("ref_occupation_id");
                latitude= user.getString("latitude");
        longitude= user.getString("longitude");
                ref_area_id= user.getString("ref_area_id");
        user_id= user.getString("user_id");
                device_id= user.getString("device_id");
        uid= user.getString("uid");
                type= user.getString("type");

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(email);
        parcel.writeString(cellphone);
        parcel.writeString(address);
        parcel.writeString(ref_occupation_id);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(ref_area_id);
        parcel.writeString(user_id);
        parcel.writeString(device_id);
        parcel.writeString(uid);
        parcel.writeString(type);
    }
}
