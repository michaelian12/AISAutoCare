package com.aisautocare.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Michael on 1/5/2017.
 */

public class History {

    private String orderId; // order id
    private int imageResourceId; // service image
    private String date; // service date
    private String price; // service price
    private String vehicleName; // vehicle name
    private String serviceName; // service name
    private String address; // order address

    public History(String orderId, int imageResourceId, String date, String price, String vehicleName, String serviceName, String address) {
        this.orderId = orderId;
        this.imageResourceId = imageResourceId;
        this.date = date;
        this.price = price;
        this.vehicleName = vehicleName;
        this.serviceName = serviceName;
        this.address = address;
    }

    public History(JSONObject object) throws JSONException {
//        this.imageResourceId = object.getString("id");
        this.orderId = object.getString("order_id");
        this.date = object.getString("order_date");
        this.price = object.getString("vehicle_year"); // sementara ini dulu
        this.vehicleName = object.getString("brand_name") + " " + object.getString("vehicle_type") + " " + object.getString("vehicle_year");
        this.serviceName = object.getString("service_sub").trim() + " " + object.getString("service_name").trim();
        this.address = object.getString("order_service_location");
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
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
