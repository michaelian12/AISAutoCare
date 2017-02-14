package com.aisautocare.mobile.model;

/**
 * Created by Michael on 1/5/2017.
 */

public class History {

    private int imageResourceId; // vehicle image
    private String date; // service date
    private String price; // service price
    private String vehicleName; // vehicle name
    private String serviceName; // service name

    public History(int imageResourceId, String date, String price, String vehicleName, String serviceName) {
        this.imageResourceId = imageResourceId;
        this.date = date;
        this.price = price;
        this.vehicleName = vehicleName;
        this.serviceName = serviceName;
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
}
