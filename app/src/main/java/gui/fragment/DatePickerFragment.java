package gui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import core.manager.ManagerActivity;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return pickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        ManagerActivity.getInstance().getUser().setDate(day+"/"+(month+1)+"/"+year);

    }


}