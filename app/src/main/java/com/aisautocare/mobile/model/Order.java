package com.aisautocare.mobile.model;

/**
 * Created by ini on 2017/01/21.
 */

public class Order {
    private String id;
    private String customer_id;
    private String         order_date;
    private String  order_time;
    private String        service_date;
    private String service_time;
    private String         service_location;
    private String latitude;
    private String         longitude;
    private String area_id;
    private String         is_emergency;
    private String license_plate;
    private String         ref_service_id;
    private String status;
    private String         method;
    private String payment_status;
    private String car_manufacture;
    private String idService;

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getCar_manufacture() {
        return car_manufacture;
    }

    public void setCar_manufacture(String car_manufacture) {
        this.car_manufacture = car_manufacture;
    }

    public String getCar_manufacture_type() {
        return car_manufacture_type;
    }

    public void setCar_manufacture_type(String car_manufacture_type) {
        this.car_manufacture_type = car_manufacture_type;
    }

    public String getCar_year() {
        return car_year;
    }

    public void setCar_year(String car_year) {
        this.car_year = car_year;
    }

    public String getIdservice() {
        return idservice;
    }

    public void setIdservice(String idservice) {
        this.idservice = idservice;
    }

    private String car_manufacture_type;
    private String        car_year;
    private String idservice;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }

    public String getService_time() {
        return service_time;
    }

    public void setService_time(String service_time) {
        this.service_time = service_time;
    }

    public String getService_location() {
        return service_location;
    }

    public void setService_location(String service_location) {
        this.service_location = service_location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getIs_emergency() {
        return is_emergency;
    }

    public void setIs_emergency(String is_emergency) {
        this.is_emergency = is_emergency;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getRef_service_id() {
        return ref_service_id;
    }

    public void setRef_service_id(String ref_service_id) {
        this.ref_service_id = ref_service_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }




}
