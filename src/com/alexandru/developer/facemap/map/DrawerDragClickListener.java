package com.alexandru.developer.facemap.map;

import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import com.alexandru.developer.facemap.MainActivity;
import com.alexandru.developer.facemap.R;
import com.alexandru.developer.facemap.timebars.TimeBar;
import com.alexandru.developer.facemap.timebars.VerticalTimeBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;

import java.io.IOException;
import java.util.ArrayList;

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

    VerticalTimeBar verticalTimeBar;
    TimeBar horizontalTimeBar;
    private Country ROMANIA;

    private String[] countries;
    private ArrayList<LatLng> track=new ArrayList<LatLng>();
    private ArrayList<String> activatedCountries=new ArrayList<String>();

    private MainActivity activity;

    public DrawerDragClickListener(Context context, GoogleMap googleMap,
                                   VerticalTimeBar verticalSeekBar, TimeBar horizontalBar, MainActivity activity){
        this.googleMap=googleMap;
        this.context=context;
        geocoder=new Geocoder(context);
        this.horizontalTimeBar=horizontalBar;
        this.verticalTimeBar=verticalSeekBar;
        this.activity = activity;
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
                googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));
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
                googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));
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
            Log.d(TAG, track.size()+"");
            //polygonOptions.add(marker.getPosition());
            polylineOptions.add(marker.getPosition());
            //Unite last and first point
            polylineOptions.add(polylineOptions.getPoints().get(0));
            track.add(track.get(0));
                googleMap.addCircle(new CircleOptions().center(marker.getPosition())
                        .radius(1000)
                        .fillColor(Color.BLACK));
            //ArrayList<LatLng> track=(ArrayList<LatLng>)pol
            //pygonOptions.getPoints();
            //polygon=googleMap.addPolygon(polygonOptions);
            polyline.remove();
            polyline=googleMap.addPolyline(polylineOptions);

            googleMap.addCircle(new CircleOptions().center(new LatLng(33.93911, 67.709953))
                    .radius(1000)
                    .fillColor(Color.BLACK));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    //Check if new country is added to queue
                    countries = context.getResources().getStringArray(R.array.countries);
                    /*String[] countryData = countries[0].split(" ");
                    Log.d(TAG, ""+Geometry.isPointInsidePolygon(new LatLng( Double.valueOf(countryData[2]),
                                                                            Double.valueOf(countryData[1])),
                                                                 track, googleMap, activity));*/
                    for(int i=0; i<track.size(); i++){

                            try {

                                ArrayList<Address> addresses = (ArrayList<Address>)geocoder.getFromLocation(track.get(i).latitude,
                                        track.get(i).longitude, 2);
                                //Log.d(TAG, "Working..");
                                int j, codeAdded = 1;
                                String currentCountryCode=addresses.get(0).getCountryCode();
                                for(j=0; j<activatedCountries.size(); j++)
                                    if(addresses.get(0).getCountryCode().equals(activatedCountries.get(j))){
                                        codeAdded = 0;
                                        break;
                                    }
                                if(codeAdded == 1)
                                    activatedCountries.add(addresses.get(0).getCountryCode());
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (IndexOutOfBoundsException e){
                                e.printStackTrace();
                            }

                    }

                    for(int i = 0; i<countries.length; i++){
                        String[] countryData = countries[i].split(" ");
                        int j, codeAdded=0;
                        for(j = 0; j < activatedCountries.size(); j++)
                            if(countryData[0].equals(activatedCountries.get(j))){
                                codeAdded = 1;
                                break;
                            }
                        if(codeAdded == 0){
                            if(Geometry.isPointInsidePolygon(new LatLng(Double.valueOf(countryData[2]),
                                                                        Double.valueOf(countryData[1]) ), track, googleMap, activity ))
                                activatedCountries.add(countryData[0]);
                        }
                    }

                    Log.d(TAG, "" + countries.length + "*" + activatedCountries.size());
                    for (int i=0;i<activatedCountries.size();i++)
                        Log.d(TAG, activatedCountries.get(i));



                }
            }).start();
            verticalTimeBar.setVisibility(View.VISIBLE);

            /*
            verticalSeekBar.setProgressBarListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    Log.d(TAG, ""+progress);
                    Marker marker=null;
                    if (progress>=1850 && progress<=1870){

                        ArrayList<Address> addresses= null;
                        try {
                            addresses = (ArrayList<Address>)geocoder.getFromLocationName("Botosani", 1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        LatLng latLng=new LatLng(addresses.get(0).getLatitude(),
                                addresses.get(0).getLongitude());
                        MarkerOptions markerOptions=new MarkerOptions().title("Mihai Eminescu").position(latLng);
                        marker=googleMap.addMarker(markerOptions);

                    }
                }

                @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });*/
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
