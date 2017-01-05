package com.aisautocare.aisautocare.model;

/**
 * Created by Michael on 1/5/2017.
 */

public class Service {

    private int imageResourceId; // service image
    private String name; // service name

    public Service(int imageResourceId, String name) {
        this.imageResourceId = imageResourceId;
        this.name = name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }
}
