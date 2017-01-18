package com.aisautocare.aisautocare.model;

/**
 * Created by Michael on 1/5/2017.
 */

public class Garage {

    private int imageResourceId; // vehicle icon
    private String manufacturer; // vehicle manufacturer name
    private String model; // vehicle model name
    private String transmission; // vehicle transmission type
//    private String capacity; // vehicle machine capacity (in cc)
    private String year; // vehicle production year

    public Garage(int imageResourceId, String manufacturer, String model, String transmission, String year) {
        this.imageResourceId = imageResourceId;
        this.manufacturer = manufacturer;
        this.model = model;
        this.transmission = transmission;
//        this.capacity = capacity;
        this.year = year;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    public String getTransmission() {
        return transmission;
    }

//    public String getCapacity() {
//        return capacity;
//    }

    public String getYear() {
        return year;
    }
}
