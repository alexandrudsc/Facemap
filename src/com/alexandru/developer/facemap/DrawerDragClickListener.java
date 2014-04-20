package com.alexandru.developer.facemap;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alexandru on 4/15/14.
 */
public class DrawerDragClickListener implements GoogleMap.OnMarkerDragListener {
    public final String TAG=this.getClass().getCanonicalName().toUpperCase();

    private Context context;

    private PolylineOptions polylineOptions;
    private Polyline polyline;
    private GoogleMap googleMap;
    private Geocoder geocoder;

    private String[] countries;
    private ArrayList<LatLng> track=new ArrayList<LatLng>();
    private ArrayList<String> activatedCountries=new ArrayList<String>();
    private ExecutorService executorService= Executors.newSingleThreadExecutor();

    private Marker markerDummy;

    public DrawerDragClickListener(Context context, GoogleMap googleMap){
        this.googleMap=googleMap;
        this.context=context;
        geocoder=new Geocoder(context);
    }

    public void clearTrack(){
        this.activatedCountries.clear();
        this.track.clear();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        if(GoogleMapsView.selectOn){

            track.add(marker.getPosition());

            //Second polygon is drawn.Remove the old one
            if(polyline!=null)
                polyline.remove();

            //polygonOptions=new PolygonOptions();
            //polygonOptions.add(marker.getPosition());
            polylineOptions=new PolylineOptions();
            polylineOptions.add(marker.getPosition());
                /*googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));*/
            polyline=googleMap.addPolyline(polylineOptions);

            /*//Thread cheking if country was already selected.If not add it to track
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    ArrayList<Address> addresses;
                    try {
                        addresses = (ArrayList<Address>)geocoder.getFromLocation(markerDummy.getPosition().latitude,
                                                                         markerDummy.getPosition().longitude, 1);
                        track.add(addresses.get(0).getCountryCode());
                        Log.d(TAG, addresses.get(0).getCountryCode());
                    } catch (IOException e) {
                        Log.d(TAG, "Error!");
                    } catch (IndexOutOfBoundsException e){
                        Log.d(TAG, "Error!");
                    }
                }
            });*/

        }
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        if(GoogleMapsView.selectOn){
            track.add(marker.getPosition());
            //polygonOptions.add(marker.getPosition());
            polylineOptions.add(marker.getPosition());
                /*googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));*/
            polyline.remove();
            polyline=googleMap.addPolyline(polylineOptions);

            /*//Thread cheking if country was already selected.If not add it to track
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //Check if new country is added to queue
                    ArrayList<Address> addresses;
                    try {
                        Log.d(TAG, markerDummy.getPosition().toString());
                        addresses = (ArrayList<Address>)geocoder.getFromLocation(markerDummy.getPosition().latitude,
                                markerDummy.getPosition().longitude, 1);

                        int i, codeAdded=1;
                        for(i=0;i<track.size();i++)
                            if(track.get(i).equals(addresses.get(0).getCountryCode())){
                                codeAdded=0;
                                break;
                            }

                        if(codeAdded==1){
                            track.add(addresses.get(0).getCountryCode());
                            Log.d(TAG, addresses.get(0).getCountryCode());
                        }
                    } catch (IOException e) {
                        Log.d(TAG, "Error!");
                    } catch (IndexOutOfBoundsException e){
                        Log.d(TAG, "Error!");
                    }
                }
            });*/
        }
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if(GoogleMapsView.selectOn){

            track.add(marker.getPosition());

            //polygonOptions.add(marker.getPosition());
            polylineOptions.add(marker.getPosition());
            //Unite last and first point
            polylineOptions.add(polylineOptions.getPoints().get(0));
                /*googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));*/
            //ArrayList<LatLng> track=(ArrayList<LatLng>)pol
            //pygonOptions.getPoints();
            //polygon=googleMap.addPolygon(polygonOptions);
            polyline.remove();
            polyline=googleMap.addPolyline(polylineOptions);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Check if new country is added to queue

                    countries=context.getResources().getStringArray(R.array.countries);
                    for(int i=0;i<track.size();i++){

                            try {

                                ArrayList<Address> addresses = (ArrayList<Address>)geocoder.getFromLocation(track.get(i).latitude,
                                        track.get(i).longitude, 2);
                                //Log.d(TAG, "Working..");
                                int j, codeAdded=1;
                                String currentCountryCode=addresses.get(0).getCountryCode();
                                for(j=0;j<activatedCountries.size();j++)
                                    if(activatedCountries.get(j).equals(currentCountryCode)){
                                        codeAdded=0;
                                        break;
                                    }

                                if(codeAdded==1)
                                    activatedCountries.add(addresses.get(0).getCountryCode());

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (IndexOutOfBoundsException e){
                                e.printStackTrace();
                            }
                        }
                    Log.d(TAG, "" + countries.length + "*" + activatedCountries.size());
                    for (int i=0;i<activatedCountries.size();i++)
                        Log.d(TAG, activatedCountries.get(i));
                }
            }).start();


                /*for(int i=0;i<countries.length;i++){
                    String[] countryData=countries[i].split(" ");
                    for(int j=0;j<track.size();j++){
                            //addresses=(ArrayList<Address>)geocoder.getFromLocation
                              //      (track.get(j).latitude, track.get(j).longitude,1);
                            if(countryData[0].equals( track.get(j) ))
                                Log.d(TAG, countryData[0]);
                                //activatedCountries.add(countryData[0]);
                    }
                }*/
            //Log.d(TAG, activatedCountries.get(0).toString());
            //Log.d(TAG, ""+polygonOptions.getPoints().size());
            //track.clear();
        }
    }
}
