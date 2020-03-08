package gui.fragment;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.test.R;

import java.util.HashMap;

import core.model.Event;

public class FPAHomeUser extends FragmentPagerAdapter {

    private int NUM_ITEMS = 3;
    private Context context;

    private FragmentPreferences fragmentPreferences;
    private FragmentEventsList fragmentEventsList;
    private FragmentMap fragmentMap;

    public FPAHomeUser(FragmentManager fragmentManager, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        fragmentPreferences = new FragmentPreferences();
        fragmentEventsList = new FragmentEventsList();
        fragmentMap = new FragmentMap();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fragmentPreferences;
            case 1:
                return fragmentEventsList;
            case 2:
                return fragmentMap;
        }
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.preferences);
            case 1:
                return context.getResources().getString(R.string.eventList);
            default:
                return context.getResources().getString(R.string.map);
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public void updateMapLocation(boolean b) {
            fragmentMap.setMyLocationEnabled(b);
    }

    public void updateMap(HashMap<Integer, Event> events) {
        fragmentMap.update(events);
    }

    public void updateList(HashMap<Integer, Event> generalEvents, HashMap<Integer, Event> userEvents,HashMap<Integer, Event> otherEvents) {
        fragmentEventsList.update(generalEvents, userEvents,otherEvents);
    }

    public Activity getActivity() {
        return fragmentEventsList.getActivity();
    }

    public void stopRefresh() {
        fragmentEventsList.stopRefresh();
    }

    public void enableLocationPermission() {
        fragmentMap.enableLocationPermission();
    }
}
