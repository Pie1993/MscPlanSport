package gui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;
import core.manager.ManagerActivity;
import core.model.User;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        User user = ManagerActivity.getInstance().getUser();
        int hour = user.getHour();
        int minute = user.getMinute();

        return new TimePickerDialog(getActivity(), this, hour, minute,
                true);

    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        ManagerActivity.getInstance().getUser().setPreferredHour(hourOfDay, minute);
    }
}
