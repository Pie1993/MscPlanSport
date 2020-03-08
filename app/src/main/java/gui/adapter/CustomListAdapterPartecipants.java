package gui.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.test.R;
import java.util.List;
import core.manager.ManagerActivity;
import gui.activity.EventActivity;


public class CustomListAdapterPartecipants extends ArrayAdapter <Pair<String,String>> {

    private EventActivity eventActivity;


    public CustomListAdapterPartecipants(List<Pair<String,String>> events) {
        super(ManagerActivity.getInstance().getActivity(EventActivity.class), R.layout.partecipant_row_layout, events);
        this.eventActivity = (EventActivity) ManagerActivity.getInstance().getActivity(EventActivity.class);
    }


    @Override
    public View getView(int position,  View view,  ViewGroup parent) {
        LayoutInflater inflater = eventActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.partecipant_row_layout, null, true);

        View row = rowView.findViewById(R.id.partecipant_id);
        TextView user=row.findViewById(R.id.user_id);
        TextView role=row.findViewById(R.id.role_id);
        Pair<String,String> pair =getItem(position);
        user.setText(pair.first);
        role.setText(pair.second);

        return rowView;
    }
}

