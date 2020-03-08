package gui.activity;

import android.Manifest;

import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.example.test.R;
import com.google.android.material.tabs.TabLayout;
import core.manager.ManagerActivity;
import gui.fragment.FPALoginRegister;



public class LoginRegisterActivity extends AppCompatActivity {
    private int INTERNET_PERMISSION_CODE = 1;

    TabLayout tabLayout;
    ViewPager viewpager;
    FPALoginRegister fpaLoginRegister;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        viewpager = findViewById(R.id.mainLayout);
        tabLayout = findViewById(R.id.sliding_tabs);

        fpaLoginRegister = new FPALoginRegister(getSupportFragmentManager(),getApplicationContext());
        viewpager.setAdapter(fpaLoginRegister);
        tabLayout.setupWithViewPager(viewpager);

        ManagerActivity.getInstance().registerActivity(this);
        ManagerActivity.getInstance().setContext(getApplicationContext());
        ManagerActivity.getInstance().setFragmentPageAdapter(fpaLoginRegister);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == INTERNET_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void login() {

        if (!checkInternetPermission()) {
            requestInternetPermission();
            return;
        }
        ManagerActivity.getInstance().login(this);
    }

    public void register() {
        if (!checkInternetPermission()) {
            requestInternetPermission();
            return;
        }


        ManagerActivity.getInstance().register(this);
    }

    private boolean checkInternetPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    private void requestInternetPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(this)
                    .setTitle("Internet permission needed")
                    .setMessage("This permission is needed for the correct use of the application")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(LoginRegisterActivity.this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);

                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, INTERNET_PERMISSION_CODE);

        }
    }


}