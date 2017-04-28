package com.aisautocare.mobile;

import com.aisautocare.mobile.model.Order;

/**
 * Created by ini on 2017/01/21.
 */

public class GlobalVar {
    public  static Order order;
    public static String selectedSubService;
    public static String selectedService;
    public static String idCustomerLogged;
    public static String UPLOAD_URL = "http://119.235.250.107/API/public/upload.php";
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

    public static String customerName;
    public static String customerCellphone;
    public static String customerEmail;

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
