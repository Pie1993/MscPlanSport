package gui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.test.R;

import java.util.Objects;

import core.manager.LocationManager;
import core.model.User;
import core.manager.ManagerActivity;
import gui.activity.HomeUserActivity;


public class FragmentPreferences extends Fragment {

    private AutoCompleteTextView country;
    private AutoCompleteTextView sports;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.preferences_layout, container, false);

        initAutoCompleteCountries(view);
        initAutoCompleteSports(view);
        initListeners(view);
        initSpinner();

        return view;
    }

    protected void initSpinner() {
        User user = ManagerActivity.getInstance().getUser();
        Resources resources = getResources();
        String[] ROLES = resources.getStringArray(getArrayId());
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, ROLES);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0, true);
        if (getArrayId() == R.array.no_role) {
            spinner.setVisibility(View.INVISIBLE);
        } else {
            spinner.setVisibility(View.VISIBLE);
            for (int i = 0; i < ROLES.length; i++)
                if (user.getRole().equals(ROLES[i]))
                    spinner.setSelection(i, true);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                if (textView.getText().toString().equals("Select a role"))
                    ManagerActivity.getInstance().getUser().setRole("");
                else
                    ManagerActivity.getInstance().getUser().setRole(textView.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private int getArrayId() {

        switch (sports.getText().toString()) {
            case "Calcio":
                return R.array.role_calcio;
            case "Calciotto":
                return R.array.role_calcio;
            case "Calcetto":
                return R.array.role_calcio;
            case "Tennis":
                return R.array.role_tennis;
            default:
                return R.array.no_role;
        }

    }

    private void initAutoCompleteSports(View view) {

        String[] SPORTS;
        Resources resources = getResources();
        SPORTS = resources.getStringArray(R.array.sports);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_dropdown_item_1line, SPORTS);

        sports = view.findViewById(R.id.autoCompleteTextSports);
        sports.setAdapter(adapter);

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ManagerActivity.getInstance().getUser().setSport(sports.getText().toString());
                initSpinner();
            }
        };

        sports.addTextChangedListener(textWatcher);


    }

    private void initAutoCompleteCountries(View view) {
        String[] COUNTRIES;
        Resources resources = getResources();
        COUNTRIES = resources.getStringArray(R.array.countries);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        country = view.findViewById(R.id.autoCompleteTextCountries);
        country.setAdapter(adapter);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ManagerActivity.getInstance().getUser().setCity(country.getText().toString());
            }
        };

        country.addTextChangedListener(textWatcher);
    }

    private void initListeners(View view) {
        User user = ManagerActivity.getInstance().getUser();
        final Switch gps_switch = view.findViewById(R.id.switch_gps);
        TextView username = view.findViewById(R.id.user_texview_id);
        ImageButton date_button = view.findViewById(R.id.date_button_id);
        ImageButton time_button = view.findViewById(R.id.time_button_id);
        spinner = view.findViewById(R.id.spinner_roles);


        time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new TimePickerFragment();
                fragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "timePicker");
            }
        });

        date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(Objects.requireNonNull(getActivity()).getSupportFragmentManager(), "datePicker");
            }
        });

        username.setText(user.getUsername());
        country.setText(user.getCity());
        sports.setText(user.getSport());

        country.dismissDropDown();
        sports.dismissDropDown();

        final LocationManager locationManager = LocationManager.getInstance();

        gps_switch.setChecked(locationManager.isRequestingLocationUpdates());
        gps_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                HomeUserActivity homeUserActivity = (HomeUserActivity) ManagerActivity.getInstance().getActivity(HomeUserActivity.class);

                if (!homeUserActivity.checkLocationPermission()) {
                    gps_switch.setChecked(false);
                    homeUserActivity.requestLocationPermission();
                    return;
                }

                if (isChecked) {
                    locationManager.setRequestingLocationUpdates(true);
                    ManagerActivity.getInstance().updateMapLocation(true);
                } else {
                    locationManager.setRequestingLocationUpdates(false);
                    ManagerActivity.getInstance().updateMapLocation(false);
                }


            }
        });

    }

}
