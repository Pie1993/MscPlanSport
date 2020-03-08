package gui.adapter;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.test.R;
import core.manager.ManagerActivity;
import java.util.List;
import core.model.Event;


public class CustomListAdapterJoinedEvents extends CustomListAdapterEvents {


    public CustomListAdapterJoinedEvents(List<Event> events) {
        super(events);

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

        structure.setBackgroundColor(Color.GREEN);
        sport.setBackgroundColor(Color.GREEN);
        date.setBackgroundColor(Color.GREEN);


        return rowView;
    }


}
