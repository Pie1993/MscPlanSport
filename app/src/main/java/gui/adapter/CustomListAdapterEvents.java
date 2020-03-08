package gui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.R;
import java.util.List;
import core.model.Event;
import gui.activity.HomeUserActivity;
import core.manager.ManagerActivity;


public class CustomListAdapterEvents extends ArrayAdapter<Event> {


    protected HomeUserActivity homeUserActivity;

    protected List<Event> events;

    public CustomListAdapterEvents(List<Event> events) {
        super(ManagerActivity.getInstance().getActivity(HomeUserActivity.class), R.layout.row_layout, events);
        homeUserActivity= (HomeUserActivity) ManagerActivity.getInstance().getActivity(HomeUserActivity.class);
        this.events=events;

    }


    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = homeUserActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.row_layout, null, true);

        //this code gets references to objects in the listview_row.xml file
        View row = rowView.findViewById(R.id.id_row);
        TextView structure = rowView.findViewById(R.id.structure_id);
        TextView sport = rowView.findViewById(R.id.sport_id);
        TextView distance = rowView.findViewById(R.id.distance_id);
        ImageView image = rowView.findViewById(R.id.image_id);
        TextView date = rowView.findViewById(R.id.date_id);


        sport.setText(events.get(position).getTypeOfStructure());
        structure.setText(events.get(position).getStructureName());
        distance.setText(events.get(position).getDistance()+" km");
        image.setImageResource(getImageId(events.get(position).getTypeOfStructure()));
        date.setText(events.get(position).getDate());

        row.setId(events.get(position).getId());
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagerActivity.getInstance().goToEvent(v.getId());
                v.setOnClickListener(null);
            }
        });
        return rowView;
    }



    protected int getImageId(String sport) {
        switch (sport) {

            case "Tennis":
                return R.drawable.tennis_icon;
            case "Calcio":
                return R.drawable.calcio_icon;
            case "Calcetto":
                return R.drawable.calcetto_icon;
            case "Calciotto":
                return R.drawable.calciotto_icon;
            default:
                return R.drawable.calcio_icon;

        }

    }

}
