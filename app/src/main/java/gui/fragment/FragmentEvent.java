package gui.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.test.R;
import core.manager.ManagerActivity;
import core.model.Event;
import core.model.User;

public class FragmentEvent extends Fragment {



    private Event event;
    private Dialog dialog;

    public FragmentEvent(Event event) {
        this.event = event;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.event_layout, container, false);

        TextView structureName = view.findViewById(R.id.structureName_id);
        TextView structureType = view.findViewById(R.id.structureType_id);
        TextView covered = view.findViewById(R.id.covered_id);
        TextView distance = view.findViewById(R.id.distance_id);
        TextView price = view.findViewById(R.id.price_id);
        Button button = view.findViewById(R.id.join_event_button_id);
        TextView organiser = view.findViewById(R.id.organiser_id);
        TextView numberOfPartecipants = view.findViewById(R.id.numberOfPartecipants_id);
        TextView empty_slot = view.findViewById(R.id.empty_slot_id);
        TextView date = view.findViewById(R.id.date_id);
        View role_layout = view.findViewById(R.id.layout_role_id);
        TextView role = view.findViewById(R.id.role_id);



        role_layout.setVisibility(View.GONE);

        User user = ManagerActivity.getInstance().getUser();

        if (ManagerActivity.getInstance().getUserEvents().containsKey(event.getId())) {
            button.setText(R.string.disjoin_event);
            role_layout.setVisibility(View.VISIBLE);
        } else {
            if ((event.getLimitPartecipant() - event.getNumberOfPartecipant()) == 0)
                button.setVisibility(View.INVISIBLE);
            button.setText(R.string.join_event);
        }

        structureName.setText(event.getStructureName());
        structureType.setText(event.getTypeOfStructure());

        if (event.isCovered())
            covered.setText(R.string.covered_string);
        else
            covered.setText(R.string.no_covered_string);

        distance.setText(event.getDistance() + " km");
        price.setText(event.getPrice() + " €");

        organiser.setText(event.getOrganiser());
        numberOfPartecipants.setText(event.getNumberOfPartecipant() + "");
        empty_slot.setText((event.getLimitPartecipant() - event.getNumberOfPartecipant()) + "");
        date.setText(event.getDate());
        role.setText(event.getRoleByUser(user.getUsername()));

        view.setId(event.getId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgressBar();
                ManagerActivity.getInstance().joinDeleteEvent(event.getId());
                v.setOnClickListener(null);
            }
        });


        return view;

    }


    private void startProgressBar() {
        dialog = ProgressDialog.show(getActivity(), "Wait",
                "Loading. Please wait...", true);
    }


    public void stopProgressBar() {
        dialog.dismiss();
    }

    public Event getEvent() {
        return event;
    }
}
