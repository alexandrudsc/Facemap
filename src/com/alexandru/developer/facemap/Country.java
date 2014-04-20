package com.alexandru.developer.facemap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Alexandru on 4/10/14.
 */
public class Country {
    private LatLng center;
    private String code, name;

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
}
