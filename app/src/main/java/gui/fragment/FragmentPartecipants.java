package gui.fragment;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.util.ArrayList;
import java.util.List;

import core.model.Event;

import gui.adapter.CustomListAdapterPartecipants;

public class FragmentPartecipants extends Fragment {


    private Event event;


    public FragmentPartecipants(Event event) {
        this.event = event;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partecipant_layout, container, false);



        List<Pair<String, String>> events = new ArrayList<Pair<String, String>>();
        for (String key : event.getUsers_roles().keySet())
            events.add(new Pair<String, String>(key, event.getUsers_roles().get(key)));

        CustomListAdapterPartecipants whatever = new CustomListAdapterPartecipants(events);
        ListView eventList = view.findViewById(R.id.partecipants_list);
        eventList.setAdapter(whatever);

        return view;

    }

}
