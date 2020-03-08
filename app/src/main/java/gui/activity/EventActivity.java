package gui.activity;


import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import com.example.test.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import core.manager.ManagerActivity;
import core.model.Event;
import gui.fragment.FPAEvent;


public class EventActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ManagerActivity.getInstance().registerActivity(this);
        ManagerActivity.getInstance().setContext(getApplicationContext());
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        ViewPager viewpager = findViewById(R.id.mainLayout);
        int key = Objects.requireNonNull(this.getIntent().getExtras()).getInt("event_key");
        Event event = ManagerActivity.getInstance().getEvents().get(key);
        FPAEvent fpaEvent = new FPAEvent(getSupportFragmentManager(),event,getApplicationContext());
        viewpager.setAdapter(fpaEvent);
        tabLayout.setupWithViewPager(viewpager);
        ManagerActivity.getInstance().setFragmentPageAdapter(fpaEvent);



    }
}
