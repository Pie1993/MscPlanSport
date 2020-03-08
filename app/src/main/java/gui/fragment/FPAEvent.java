package gui.fragment;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.test.R;

import core.model.Event;

public class FPAEvent extends FragmentPagerAdapter {

    private int NUM_ITEMS = 2;
    private FragmentEvent fragmentEvent;
    private FragmentPartecipants fragmentPartecipants;
    private Context context;


    public FPAEvent(FragmentManager fragmentManager, Event event, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
        fragmentEvent = new FragmentEvent(event);
        fragmentPartecipants = new FragmentPartecipants(event);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return fragmentEvent;
            case 1:
                return fragmentPartecipants;
        }
        return null;

    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return context.getResources().getString(R.string.info);
            default:
                return context.getResources().getString(R.string.partecipants);
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    public Event getEvent() {
        return fragmentEvent.getEvent();
    }

    public void stopProgressBar() {
        fragmentEvent.stopProgressBar();
    }

}
