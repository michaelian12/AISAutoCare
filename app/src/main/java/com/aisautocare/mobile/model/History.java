package com.aisautocare.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 1/5/2017.
 */

public class History {

    private String orderId; // order id
    private String wheel; // wheel
    private String date; // service date
    private String price; // service price
    private String vehicleName; // vehicle name
    private String serviceName; // service name
    private String address; // order address

    public History(String orderId, String wheel, String date, String price, String vehicleName, String serviceName, String address) {
        this.orderId = orderId;
        this.wheel = wheel;
        this.date = date;
        this.price = price;
        this.vehicleName = vehicleName;
        this.serviceName = serviceName;
        this.address = address;
    }

    public History(JSONObject object) throws JSONException {
        this.orderId = object.getString("id");
        this.wheel = object.getString("wheel");
        this.date = object.getString("order_date");
        this.price = object.getString("total_harga");
        this.vehicleName = object.getString("name") + " " + object.getString("type");// + " " + object.getString("vehicle_year");
        this.serviceName = object.getString("sub").trim();// + " " + object.getString("service_name").trim();
        this.address = object.getString("service_location");
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWeel(String wheel) {
        this.wheel = wheel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
