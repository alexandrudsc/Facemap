package com.alexandru.developer.facemap.map;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Alexandru on 4/10/14.
 */
public class Country {
    private LatLng center;
    private String code, name;
    public  ArrayList<City> cities=new ArrayList<City>();

    public void setCenter(LatLng center) {
        this.center = center;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getCenter(){
        return this.center;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }


    public static class City{
        private String name;
        ArrayList<VIP> persons=new ArrayList<VIP>();

        public City(String name){
            this.name = name;
        }

        public String getName(){
            return this.name;
        }

        public void addPerson(VIP p){
            persons.add(p);
        }

    }

    public static class VIP{
        public String name;
        public int yearOfBirth;
        public VIP(String name, int yearOfBirth){
            this.name = name;
            this.yearOfBirth = yearOfBirth;
        }
    }

    public static class Event{
        int year;
        String city;
        public Event(){

        }
    }

}
