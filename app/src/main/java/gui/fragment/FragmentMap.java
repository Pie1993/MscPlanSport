package gui.fragment;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;
import core.manager.LocationManager;
import core.manager.ManagerActivity;
import core.model.Event;
import gui.activity.HomeUserActivity;



public class FragmentMap extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private boolean permission = false;
    private GoogleMap map;
    private int padding_in_pixel = 100;
    private LatLngBounds latLngBounds;
    private HashMap<Integer, Event> events;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_layout, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    private void initMap() {
        if (LocationManager.getInstance().isRequestingLocationUpdates() && isLocationPermissionGranted()) {
            map.setMyLocationEnabled(true);
        } else {
            map.setMyLocationEnabled(false);
        }
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        UiSettings uiSettings = map.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        if(events!=null) {
            update(events);
            events=null;
        }

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "Andiamo alla tua posizione", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Sei tu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        HomeUserActivity homeUserActivity = (HomeUserActivity) ManagerActivity.getInstance().getActivity(HomeUserActivity.class);
        map = googleMap;
        initMap();
        if (!homeUserActivity.checkLocationPermission())
            homeUserActivity.requestLocationPermission();
        else
            enableLocationPermission();
    }

    public void update(HashMap<Integer, Event> events) {
        if(map==null) {
            this.events=events;
            return;
        }
        map.clear();
        LocationManager locationManager=LocationManager.getInstance();

        HashMap<LatLng, Event> map_events = new HashMap<>();
        for (Integer key : events.keySet()) {
            map_events.put(new LatLng(events.get(key).getLatitude(), events.get(key).getLongitude()), events.get(key));
        }

            latLngBounds = new LatLngBounds(locationManager.getLatLonBackup(), locationManager.getLatLonBackup());

        for (LatLng latLng : map_events.keySet()) {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(map_events.get(latLng).getStructureName());
            map.addMarker(markerOptions);

            latLngBounds = latLngBounds.including(latLng);
        }


        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(FragmentMap.this.latLngBounds, FragmentMap.this.padding_in_pixel));
            }
        });

    }

    public void enableLocationPermission() {
        permission=true;
    }

    public boolean isLocationPermissionGranted()
    {
        return permission;
    }

    public void setMyLocationEnabled(boolean b) {
        if(map!= null && isLocationPermissionGranted())
            map.setMyLocationEnabled(b);

    }
}
