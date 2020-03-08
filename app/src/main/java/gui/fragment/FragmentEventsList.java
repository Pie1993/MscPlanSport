package gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.test.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.manager.ManagerActivity;
import core.model.Event;
import gui.adapter.CustomListAdapterEvents;
import gui.adapter.CustomListAdapterJoinedEvents;


public class FragmentEventsList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private View view;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.list_layout, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_list_id);
        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }


    public void update(HashMap<Integer, Event> generalEvents, HashMap<Integer, Event> userEvents, HashMap<Integer, Event> otherEvents) {

        List<Event> myGeneralEvents = new ArrayList<Event>();
        List<Event> myUserEvents = new ArrayList<Event>();
        List<Event> myOtherEvents = new ArrayList<Event>();


        for (Integer key : generalEvents.keySet()) {
            Event event = generalEvents.get(key);
            myGeneralEvents.add(event);
        }


        for (Integer key : userEvents.keySet()) {
            Event event = userEvents.get(key);
            myUserEvents.add(event);
        }

        for (Integer key : otherEvents.keySet()) {
            Event event = otherEvents.get(key);
            myOtherEvents.add(event);
        }


        if(generalEvents.isEmpty())
            Toast.makeText(getContext(), "Nessun evento per la data odierna in base ai tuoi parametri di ricerca", Toast.LENGTH_LONG).show();

        CustomListAdapterJoinedEvents joinedEvents = new CustomListAdapterJoinedEvents(myUserEvents);
        final ListView joinedEventList = view.findViewById(R.id.joined_event_list);
        joinedEventList.setAdapter(joinedEvents);
        initListener(joinedEventList);

        CustomListAdapterEvents whatever = new CustomListAdapterEvents(myGeneralEvents);
        ListView eventList = view.findViewById(R.id.event_list);
        eventList.setAdapter(whatever);
        initListener(eventList);


        CustomListAdapterEvents relatedEvents = new CustomListAdapterEvents(myOtherEvents);
        ListView relatedEventList = view.findViewById(R.id.related_event_list);
        relatedEventList.setAdapter(relatedEvents);
        initListener(relatedEventList);


    }

    private void initListener(final ListView list)
    {
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list.getChildAt(0) != null)
                    swipeRefreshLayout.setEnabled(list.getFirstVisiblePosition() == 0 && list.getChildAt(0).getTop() == 0);
            }
        });
    }

    @Override
    public void onRefresh() {
        ManagerActivity.getInstance().requestEvents();
    }

    public void stopRefresh() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }
}
