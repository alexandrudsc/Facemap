package com.alexandru.developer.facemap;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.alexandru.developer.facemap.map.Country;
import com.alexandru.developer.facemap.map.DrawerDragClickListener;
import com.alexandru.developer.facemap.map.GoogleMapsView;
import com.alexandru.developer.facemap.map.MapClickListener;
import com.alexandru.developer.facemap.timebars.TimeBar;
import com.alexandru.developer.facemap.timebars.VerticalSeekBar;
import com.alexandru.developer.facemap.timebars.VerticalTimeBar;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.*;

import java.sql.Time;

public class MainActivity extends Activity {

    public final String TAG=this.getClass().getCanonicalName().toUpperCase();
    public static Country Romania=new Country();

    private GoogleMap googleMap;
    private MapView mapView;

    private DrawerDragClickListener drawerDragClickListener;

    Button btn;
    /*
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //Add data to Romania
        Romania.cities.add(new City);
        //Create map
        mapView=((MapView)findViewById(R.id.map));
        mapView.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mapView.onResume();
        initilizeMap();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = mapView.getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }else {
                // latitude and longitude
                double latitude = 46.385044 ;
                double longitude = 26.486671;

                VerticalTimeBar verticalSeekBar = (VerticalTimeBar)findViewById(R.id.time_picker_region);
                TimeBar seekBar = (TimeBar)findViewById(R.id.time_picker_person);
                verticalSeekBar.setProgress(100);
                //Events at which the map will respond
                drawerDragClickListener=new DrawerDragClickListener(this, this.googleMap, verticalSeekBar, seekBar, this);
                googleMap.setOnMarkerDragListener(drawerDragClickListener);

                googleMap.setOnMapClickListener(new MapClickListener(getApplicationContext()));
            }
        }
        //No selection was made
        GoogleMapsView.selectOn=false;
    }

    public void clickSelector(View view){

        //Selection begins only if pointer is shown on the screens
        if(this.findViewById(R.id.get_pointer).isClickable()==false)

        if(GoogleMapsView.selectOn)
            GoogleMapsView.selectOn=false;
        else
            GoogleMapsView.selectOn=true;

    }

    public void getPointer(View view){
        view.setClickable(false);
        //Create pointer(marker)
        LatLng latLng=googleMap.getProjection().fromScreenLocation(new Point(300, 300));

        MarkerOptions marker = new MarkerOptions().position(latLng).title("Hello Maps ");

        marker.draggable(true);

        marker.rotation(-180);

        googleMap.addMarker(marker);

    }

    public void refresh(View view){

        googleMap.clear();

        //getPointer button is clickable
        this.findViewById(R.id.get_pointer).setClickable(true);

        //Selection hasn't begun yet
        GoogleMapsView.selectOn=false;
        drawerDragClickListener.clearTrack();
    }

    public void setProgress(View view){
        ProgressBar progressBar=(ProgressBar)view;
        progressBar.setProgress(progressBar.getProgress()+20);
    }

}
