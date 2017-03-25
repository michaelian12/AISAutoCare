package com.aisautocare.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ini on 2017/03/25.
 */

public class VehicleType {

    private String id, wheel, ref_brand_id, type, year, transmission, note;

    public VehicleType(String id, String wheel, String ref_brand_id, String type, String year, String transmission, String note) {
        this.id = id;
        this.wheel = wheel;
        this.ref_brand_id = ref_brand_id;
        this.type = type;
        this.year = year;
        this.transmission = transmission;
        this.note = note;
    }

    public VehicleType (JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.wheel = object.getString("wheel");
        this.ref_brand_id = object.getString("ref_brand_id");
        this.type = object.getString("type");
        this.year = object.getString("year");
        this.transmission = object.getString("transmission");
        this.note = object.getString("note");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }

    public String getRef_brand_id() {
        return ref_brand_id;
    }

    public void setRef_brand_id(String ref_brand_id) {
        this.ref_brand_id = ref_brand_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
