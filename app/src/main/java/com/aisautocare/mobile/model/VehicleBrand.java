package com.aisautocare.mobile.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ini on 2017/03/25.
 */

public class VehicleBrand {

    private String id, name, wheel;

    public VehicleBrand(String id, String name, String wheel) {
        this.id = id;
        this.name = name;
        this.wheel = wheel;
    }

    public VehicleBrand(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.name = object.getString("name");
        this.wheel = object.getString("wheel");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }
}
