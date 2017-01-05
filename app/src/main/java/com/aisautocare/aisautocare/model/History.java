package com.aisautocare.aisautocare.model;

/**
 * Created by Michael on 1/5/2017.
 */

public class History {

    private int imageResourceId; // service image
    private String name; // service name
    private String notes; // service notes
    private String date; // service date
    private String time; // service time

    public History(int imageResourceId, String name, String notes, String date, String time) {
        this.imageResourceId = imageResourceId;
        this.name = name;
        this.notes = notes;
        this.date = date;
        this.time = time;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
