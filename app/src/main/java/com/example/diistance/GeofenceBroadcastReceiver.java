package com.example.diistance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        //counter
        int PEOPLE_COUNT = 0;
        int MAX_PEOPLE_COUNT = 0;

        //get transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            PEOPLE_COUNT++;
        } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            PEOPLE_COUNT--;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
//        Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        if (geofenceTransition == transitionType) {
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                if (PEOPLE_COUNT > MAX_PEOPLE_COUNT){
                    Toast.makeText(context, "Too Many People in the Geofence", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendNotification("Too Many People in the Geofence", MapsActivity.class);
                }else {
                    Toast.makeText(context, "Entering the Geofence", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendNotification("Entering the Geofence", MapsActivity.class);
                }

            }else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                if (PEOPLE_COUNT > MAX_PEOPLE_COUNT){
                    Toast.makeText(context, "Too Many People in the Geofence", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendNotification("Too Many People in the Geofence", MapsActivity.class);
                }else {
                    Toast.makeText(context, "In the Geofence", Toast.LENGTH_SHORT).show();
                }
            }else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                if (PEOPLE_COUNT > MAX_PEOPLE_COUNT){
                    Toast.makeText(context, "Still Too Many People in the Geofence", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendNotification("Still Too Many People in the Geofence", MapsActivity.class);
                }else {
                    Toast.makeText(context, "Leaving the Geofence", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendNotification("Leaving the Geofence", MapsActivity.class);
                }
            }
        }

    }


}