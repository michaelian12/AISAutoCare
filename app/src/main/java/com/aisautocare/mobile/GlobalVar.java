package com.aisautocare.mobile;

/**
 * Created by ini on 2017/01/21.
 */

public class GlobalVar {
    public String hostAPI = "http://119.235.250.107/API/public/api";
    public static boolean isVehicleSelected = false;
    public static double selectedLat;
    public static double selectedLon;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static int waktuTempuh = 900; //second
    public boolean isVehicleSelected() {
        return isVehicleSelected;
    }

    public void setVehicleSelected(boolean vehicleSelected) {
        isVehicleSelected = vehicleSelected;
    }

    public String getHostAPI() {
        return hostAPI;
    }

}
