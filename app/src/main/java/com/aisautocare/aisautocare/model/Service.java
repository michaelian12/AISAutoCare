package com.aisautocare.aisautocare.model;

/**
 * Created by Michael on 1/5/2017.
 */

public class Service {

    private int imageResourceId; // service image
    private String name; // service name
    private String price; // service price

    public Service(int imageResourceId, String name, String price) {
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.price = price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
