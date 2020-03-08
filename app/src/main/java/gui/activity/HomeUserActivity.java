package gui.activity;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.test.R;
import com.google.android.material.tabs.TabLayout;

import core.manager.LocationManager;
import core.manager.ManagerActivity;
import gui.fragment.FPAHomeUser;


public class HomeUserActivity extends FragmentActivity {


    private int LOCATION_PERMISSION_CODE = 1;


    private FPAHomeUser fpaHomeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ManagerActivity.getInstance().registerActivity(this);
        ManagerActivity.getInstance().setContext(getApplicationContext());
        setContentView(R.layout.main);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        ViewPager viewpager = findViewById(R.id.mainLayout);
        fpaHomeUser = new FPAHomeUser(getSupportFragmentManager(), getApplicationContext());
        viewpager.setAdapter(fpaHomeUser);
        tabLayout.setupWithViewPager(viewpager);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==1)
                    ManagerActivity.getInstance().requestEvents();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ManagerActivity.getInstance().setFragmentPageAdapter(fpaHomeUser);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ManagerActivity.getInstance().closeKeyBoard(this);
        LocationManager.getInstance().startLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ManagerActivity.getInstance().requestEvents();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocationManager.getInstance().stopLocationUpdates();
    }

    public boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Geolocalization permission needed")
                    .setMessage("This permission is needed for the correct use of the application")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(HomeUserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);

                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE)
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ManagerActivity.getInstance().enableLocationPermission();
            }
    }
}