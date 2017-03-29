package com.aisautocare.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ini on 2017/03/29.
 */

public class ServiceType {

    private String id;
    private String wheel;
    private String name;
    private String sub;
    private String description;
    private String note;
    private String price;
    private String parent;

    public ServiceType(JSONObject data) throws JSONException {
        this.id = data.getString("id");
        this.wheel = data.getString("wheel");
        this.name = data.getString("name");
        this.sub = data.getString("sub");
        this.description = data.getString("description");
        this.note = data.getString("note");
        this.price = data.getString("price");
        this.parent = data.getString("parent");

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
