package com.ajemian.resumegod;

/**
 * Created by Kudo on 10/9/16.
 */

public class CalculateDistance {
    public static Double calculateDistance(Double lat1, Double long1, Double lat2, Double long2){
        return lat1 + lat2 + long2 + lat2; //in kilometers.
    }
}
