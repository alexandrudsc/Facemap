package com.alexandru.developer.facemap.map;

import android.graphics.Color;
import android.util.Log;
import com.alexandru.developer.facemap.MainActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by Alexandru on 4/17/14.
 * Solves point in a polygon problem (rays algorithm)
 * Longitude is x, latitude is y
 */

public class Geometry {
    public static final String TAG="COM.ALEXANDRU.DEVELOPER.FACEMAP.MAP.GEOMETRY";

    private static GoogleMap googleMap;
    private static MainActivity activity;
    private static LatLng point2;
    private static  double latitudeOfIntersectionPoint2;
    //Error when intersetion point is in a vertex of the polygon.MUST SOLVE THAT!
    public static boolean isPointInsidePolygon(LatLng point, ArrayList<LatLng> polygon, GoogleMap gM, MainActivity act){
        float pointsOfIntersection=0;
        int i;
        googleMap = gM;
        activity = act;
        for(i=0;i<polygon.size()-1;i++){
            LatLng pointA, pointB;
            pointA=polygon.get(i);
            pointB=polygon.get(i+1);
            if(pointA.latitude> point.latitude || pointB.latitude>point.latitude){
                    pointsOfIntersection += checkIntersection(pointA, pointB, point);
            }
        }
        Log.d(TAG, ""+pointsOfIntersection);
        if(pointsOfIntersection % 2 == (float) 1 ){

            return true;
        }
        return false;
    }

    static float checkIntersection(LatLng pointA, LatLng pointB, LatLng point){
        //The equation of a liniar function that cuts two points: A and B
         double latitudeOfIntersectionPoint=(point.longitude-pointA.longitude) * (pointB.latitude - pointA.latitude)
                                            / (pointB.longitude - pointA.longitude ) + pointA.latitude;

        point2 = point;
        if(point.longitude == pointA.longitude || point.longitude == pointB.longitude){
            Log.d(TAG, "Equality of x-es");
            return (float) 0.5;
        }
        if(doesNumberBelongsToInterval(latitudeOfIntersectionPoint, pointA.latitude, pointB.latitude) &&
           doesNumberBelongsToInterval(point.longitude, pointA.longitude, pointB.longitude)){
            latitudeOfIntersectionPoint2 = latitudeOfIntersectionPoint;
            Log.d(TAG, pointA.toString()+ "-" + pointB.toString());
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //googleMap.addPolyline(new PolylineOptions().add(point2).
                      //      add(new LatLng(latitudeOfIntersectionPoint2, point2.longitude)));
                    googleMap.addCircle(new CircleOptions().radius(10000).center(
                            new LatLng(latitudeOfIntersectionPoint2, point2.longitude)
                    ).fillColor(Color.YELLOW));
                }
            });

            Log.d(TAG, "Intersection");
            return 1;
        }

        return (float)0.0;
    }

    static private boolean doesNumberBelongsToInterval( double c, double a, double b){
        if(c <= a && c >= b) return true;
        if(c >= a && c <= b) return true;
        return false;
    }

}
