package com.alexandru.developer.facemap.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Alexandru on 3/29/14.
 */
public class GoogleMapsView extends MapView {

    public final String TAG=this.getClass().getCanonicalName().toUpperCase();

    //If true the marker is drawing when it's dragged
    public static boolean selectOn;

    //Track of the marker
    public static ArrayList<LatLng> track;

    public GoogleMapsView(Context context) {
        super(context);
        init();
    }

    public GoogleMapsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GoogleMapsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GoogleMapsView(Context context, GoogleMapOptions options) {
        super(context, options);
        init();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
    }

    public void init(){
        track=new ArrayList<LatLng>();

    }

    public class GeoPoint extends Point {
        public float x,y;

        public GeoPoint(float x, float y){
            this.x=x;
            this.y=y;
        }

    }

}
