package com.alexandru.developer.facemap.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;
import com.alexandru.developer.facemap.map.GoogleMapsView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alexandru on 3/29/14.
 */
public class MapClickListener implements GoogleMap.OnMapClickListener {

    private final String TAG=this.getClass().getCanonicalName().toUpperCase();

    private Context context;

    public MapClickListener(Context context){
        this.context=context;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(GoogleMapsView.selectOn){
            Geocoder geocoder=new Geocoder(context);
            Toast toast;
            ArrayList<Address> addresses=null;
            try{
                addresses= (ArrayList<Address>)geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                toast=Toast.makeText(context,addresses.get(0).getCountryName() + " " +addresses.get(0).getCountryCode()+" "+
                                             addresses.get(0).getLatitude()+" "+addresses.get(0).getLongitude(),
                        Toast.LENGTH_SHORT);
            }catch (IOException e){
                toast=Toast.makeText(context, "Error", Toast.LENGTH_SHORT);
            }
            catch (IndexOutOfBoundsException e){

                toast=Toast.makeText(context, String.valueOf(latLng.longitude)+" "+ String.valueOf(latLng.latitude),
                                    Toast.LENGTH_SHORT);
            }
                toast.show();
        }
    }
}
