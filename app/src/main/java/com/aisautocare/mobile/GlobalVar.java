package com.aisautocare.mobile;

/**
 * Created by ini on 2017/01/21.
 */

public class GlobalVar {
    public static String selectedSubService;
    public static String selectedService;
    public static String idCustomerLogged;
    public static String hostAPI = "http://119.235.250.107/API/public/api";
    public static boolean isVehicleSelected = false;
    public static String selectedCar;
    public static String selectedCarType;
    public static String selectedCarYear;
    public static boolean sudahSelesai;
    public static double selectedLat;
    public static double selectedLon;
    public static String idOrder;
    public static boolean statusBerangkat= false;

    public static double bengkelLat;
    public static double bengkelLon;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static int waktuTempuh = 900; //second
    public static int bengkelID;
    public static Double orderLat;
    public static Double orderLon;

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
