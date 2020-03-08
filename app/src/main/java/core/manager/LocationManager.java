package core.manager;

import android.content.IntentSender;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import gui.activity.HomeUserActivity;

public class LocationManager {


    public void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    public void startLocationUpdates() {
        if(isRequestingLocationUpdates())
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }

    private void enableListenerForGPS() {

        final HomeUserActivity homeUserActivity = (HomeUserActivity) ManagerActivity.getInstance().getActivity(HomeUserActivity.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(homeUserActivity);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) {
                    return;
                }

                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    //   Toast.makeText(HomeUserActivity.this, "Aggiornamento arrivato  ", Toast.LENGTH_SHORT).show();
                    // Log.d("ECCOMI", location.getLatitude()+"   "+location.getLongitude());
                    LocationManager.this.location = location;
                    //requestEvents();
                    userBackup.setLatitude(location.getLatitude());
                    userBackup.setLongitude(location.getLongitude());
                    userBackup.setTime(System.currentTimeMillis());
                }
            }
        };


        createLocationRequest();

        // per ottenere continui aggiornamenti della posizione


    }

    private void createLocationRequest() {
        final HomeUserActivity homeUserActivity = (HomeUserActivity) ManagerActivity.getInstance().getActivity(HomeUserActivity.class);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(60000);
        locationRequest.setFastestInterval(30000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        SettingsClient client = LocationServices.getSettingsClient(homeUserActivity);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(homeUserActivity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                if (isRequestingLocationUpdates())
                    startLocationUpdates();
            }

        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(homeUserActivity,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }

            }
        });


    }

    public void setCityLocation(double latitude, double longitude) {

        cityBackup.setLatitude(latitude);
        cityBackup.setLongitude(longitude);
        cityBackup.setTime(System.currentTimeMillis());
        city.setLatitude(latitude);
        city.setLongitude(longitude);
    }

    public LatLng getLatLonBackup() {

        if (requestingLocationUpdates)
            return new LatLng(location.getLatitude(), location.getLongitude());
        if (city.getLatitude() != NO_VALUE && city.getLongitude() != NO_VALUE)
            return new LatLng(city.getLatitude(), city.getLongitude());

        if (cityBackup.getLatitude() != NO_VALUE && cityBackup.getLongitude() != NO_VALUE
                && userBackup.getLatitude() != NO_VALUE && userBackup.getLongitude() != NO_VALUE) {
            if (userBackup.getTime() > cityBackup.getTime())
                return new LatLng(userBackup.getLatitude(), userBackup.getLongitude());

            if (cityBackup.getTime() > userBackup.getTime())
                return new LatLng(cityBackup.getLatitude(), cityBackup.getLongitude());
        }

        if (cityBackup.getLatitude() != NO_VALUE && cityBackup.getLongitude() != NO_VALUE)
            return new LatLng(cityBackup.getLatitude(), cityBackup.getLongitude());
        if (userBackup.getLatitude() != NO_VALUE && userBackup.getLongitude() != NO_VALUE)
            return new LatLng(userBackup.getLatitude(), userBackup.getLongitude());

        return new LatLng(0, 0);
    }

    public Location getLastUserLocation() {
        if (isRequestingLocationUpdates()) {
            return location;
        } else if (city.getLatitude() == NO_VALUE && city.getLongitude() == NO_VALUE) {

            if (userBackup.getLatitude() == NO_VALUE && userBackup.getLongitude() == NO_VALUE)
                return cityBackup;

            if (cityBackup.getLatitude() == NO_VALUE && cityBackup.getLongitude() == NO_VALUE)
                return userBackup;

            if (userBackup.getTime() > cityBackup.getTime())
                return userBackup;

            if (cityBackup.getTime() > userBackup.getTime())
                return cityBackup;

        } else {
            location.setLatitude(NO_VALUE);
            location.setLongitude(NO_VALUE);
        }
        return location;
    }

    public void resetCityLocation() {
        city.setLatitude(NO_VALUE);
        city.setLongitude(NO_VALUE);
    }

    public Location getCityLocation() {
        return city;
    }

    public boolean isRequestingLocationUpdates() {
        return requestingLocationUpdates;
    }

    public void setRequestingLocationUpdates(boolean requestingLocationUpdates) {
        this.requestingLocationUpdates = requestingLocationUpdates;
        if (requestingLocationUpdates)
            startLocationUpdates();
        else
            stopLocationUpdates();
    }

    public static LocationManager getInstance() {
        if (locationManager == null)
            locationManager = new LocationManager();
        return locationManager;
    }

    private LocationManager() {
        location.setLatitude(NO_VALUE);
        location.setLongitude(NO_VALUE);
        userBackup.setLatitude(NO_VALUE);
        userBackup.setLongitude(NO_VALUE);
        cityBackup.setLatitude(NO_VALUE);
        cityBackup.setLongitude(NO_VALUE);

        resetCityLocation();

        enableListenerForGPS();
    }

    public static double NO_VALUE = 1000;
    private Location city = new Location("City position");
    private Location location = new Location("User position");
    private Location userBackup = new Location("User backup position");
    private Location cityBackup = new Location("City backup position");
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates = false;
    private static final int REQUEST_CHECK_SETTINGS = 111;
    private LocationRequest locationRequest;
    private static LocationManager locationManager;


}
