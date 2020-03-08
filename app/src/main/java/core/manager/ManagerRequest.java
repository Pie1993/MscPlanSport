package core.manager;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import core.model.Event;
import core.model.User;
import core.network.mapping.JsonParameter;
import core.network.request.EventRequest;
import core.network.request.JoinDeleteRequest;
import core.network.request.LoginRequest;
import core.network.request.MyRequest;
import core.network.request.ParametersRequests;
import core.network.request.RegisterRequest;
import gui.fragment.FPALoginRegister;

public class ManagerRequest {

    private Context context;

    private ManagerActivity managerActivity;

    ManagerRequest(ManagerActivity managerActivity) {
        this.managerActivity = managerActivity;
    }

    public void login(JSONObject response) {
        managerActivity.stopProgressBarLoginRegister();
        if (checkResponse(response, true))
            managerActivity.goToHomeUser();
    }

    public void register(JSONObject response) {
        managerActivity.stopProgressBarLoginRegister();
        if (checkResponse(response, false))
            managerActivity.goToHomeUser();
    }

    public void joinDeleteEventRequest(String user_username, int event_id) {
        JoinDeleteRequest joinDeleteRequest = new JoinDeleteRequest();
        MyRequest myRequest = new MyRequest(context, this);
        joinDeleteRequest.addParameter(ParametersRequests.username, user_username);
        joinDeleteRequest.addParameter(ParametersRequests.event_key, String.valueOf(event_id));
        joinDeleteRequest.setJsonObject();
        myRequest.createRequest(joinDeleteRequest);
    }

    public void requestLogin(FPALoginRegister fpaLoginRegister) {
        if (fpaLoginRegister.getUsername().isEmpty()) {
            Toast.makeText(context, "Username can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        if (fpaLoginRegister.getPassword().isEmpty()) {
            Toast.makeText(context, "Password can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        MyRequest myRequest = new MyRequest(context, this);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.addParameter(ParametersRequests.username, fpaLoginRegister.getUsername());
        loginRequest.addParameter(ParametersRequests.password, fpaLoginRegister.getPassword());
        loginRequest.setJsonObject();
        myRequest.createRequest(loginRequest);


    }

    public void requestRegister(FPALoginRegister fpaLoginRegister) {


        if (fpaLoginRegister.getRegisterUsername().isEmpty()) {
            Toast.makeText(context, "Username can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        if (fpaLoginRegister.getRegisterPassword().isEmpty()) {
            Toast.makeText(context, "Password can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        if (fpaLoginRegister.getRegisterEmail().isEmpty()) {
            Toast.makeText(context, "Email can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        if (fpaLoginRegister.getRegisterName().isEmpty()) {
            Toast.makeText(context, "Name can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }
        if (fpaLoginRegister.getRegisterSurname().isEmpty()) {
            Toast.makeText(context, "Surname can not be empty", Toast.LENGTH_SHORT).show();
            managerActivity.stopProgressBarLoginRegister();
            return;
        }


        MyRequest myRequest = new MyRequest(context, this);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.addParameter(ParametersRequests.username, fpaLoginRegister.getRegisterUsername());
        registerRequest.addParameter(ParametersRequests.password, fpaLoginRegister.getRegisterPassword());
        registerRequest.addParameter(ParametersRequests.email, fpaLoginRegister.getRegisterEmail());
        registerRequest.addParameter(ParametersRequests.surname, fpaLoginRegister.getRegisterSurname());
        registerRequest.addParameter(ParametersRequests.name, fpaLoginRegister.getRegisterName());
        registerRequest.addParameter(ParametersRequests.company, fpaLoginRegister.getRegisterAsCompany());
        registerRequest.setJsonObject();

        myRequest.createRequest(registerRequest);


    }

    private boolean checkResponse(JSONObject response, boolean login) {

        try {
            if (response.isNull("status") && response.isNull("connection")) {
                HashMap<Integer, Event> events = new HashMap<Integer, Event>();

                JSONObject json = response.getJSONObject("user");
                User user = new User();
                user.setCompany(json.getBoolean("company"));
                user.setEmail(json.getString("email"));
                user.setName(json.getString("name"));
                // user.setEvents(events);
                user.setSport(json.getString("sport"));
                user.setSurname(json.getString("surname"));
                user.setUsername(json.getString("username"));
                user.setCity(json.getString("city"));
                user.setHour(json.getInt("hour"));
                user.setMinute(json.getInt("minute"));
                user.setRole(json.getString("role"));

                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                user.setDate(day + "/" + (month + 1) + "/" + year);

                managerActivity.setUser(user);
                return true;

            } else if (!response.isNull("status") && response.getString("status").equals(JsonParameter.error.toString())) {
                if (login)
                    Toast.makeText(context, "Username or password wrong", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "Username non disponibile", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(context, "Connection error", Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;


    }

    public void updateEvents(JSONObject response) {
        HashMap<Integer, Event> general_events = readFromJSON(response, "events");
        HashMap<Integer, Event> userEvents = readFromJSON(response, "userEvents");
        HashMap<Integer, Event> otherEvents = readFromJSON(response, "otherEvents");
        managerActivity.updateEvents(general_events, userEvents, otherEvents);
    }

    private HashMap<Integer, Event> readFromJSON(JSONObject response, String nameList) {
        HashMap<Integer, Event> map = new HashMap<Integer, Event>();

        try {

            JSONObject container = response.getJSONObject("events");
            JSONArray events = container.getJSONArray(nameList);
            int size = events.length();
            for (int i = 0; i < size; i++) {
                JSONObject obj = events.getJSONObject(i);
                HashMap<String, String> users_roles = new HashMap<>();

                JSONObject roles = obj.getJSONObject("users_roles");
                Iterator<String> key = roles.keys();
                while (key.hasNext()) {
                    String k = key.next();
                    String v = roles.getString(k);
                    users_roles.put(k, v);
                }

                Event event = new Event(
                        obj.getString("organiser"),
                        obj.getString("structureName"),
                        obj.getString("typeOfStructure"),
                        obj.getDouble("latitude"),
                        obj.getDouble("longitude"),
                        obj.getBoolean("covered"),
                        obj.getDouble("price"),
                        obj.getInt("numberOfPartecipant"),
                        obj.getInt("id"),
                        obj.getDouble("distance"),
                        obj.getString("date"),
                        obj.getInt("limitPartecipant"),
                        users_roles);
                map.put(event.getId(), event);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;

    }

    public void requestEvents(User user) {

        LocationManager locationManager = LocationManager.getInstance();
        List<Address> addresses = null;

        if (!user.getCity().isEmpty()) {
            addresses = computeCityCoordinate(user.getCity());
            if (addresses != null && !addresses.isEmpty())
                locationManager.setCityLocation(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            else
                locationManager.resetCityLocation();
        } else {
            locationManager.resetCityLocation();
        }




/*        if (!locationManager.isRequestingLocationUpdates() && !user.getCity().isEmpty()) {
            if (addresses != null && !addresses.isEmpty())
                locationManager.setBackupLocation(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());

        }
        if (!locationManager.isRequestingLocationUpdates() && user.getCity().isEmpty()) {
            locationManager.setBackupLocation();
            Toast.makeText(context, "Richiesta basata sulla tua ultima posizione", Toast.LENGTH_LONG).show();
        }


 */

        MyRequest myRequest = new MyRequest(context, this);
        EventRequest eventRequest = new EventRequest();
        eventRequest.addParameter(ParametersRequests.username, user.getUsername());
        eventRequest.addParameter(ParametersRequests.preferredSport, user.getSport());
        eventRequest.addParameter(ParametersRequests.userLat, String.valueOf(locationManager.getLastUserLocation().getLatitude()));
        eventRequest.addParameter(ParametersRequests.userLong, String.valueOf(locationManager.getLastUserLocation().getLongitude()));
        eventRequest.addParameter(ParametersRequests.city, user.getCity());
        eventRequest.addParameter(ParametersRequests.hour, String.valueOf(user.getHour()));
        eventRequest.addParameter(ParametersRequests.minute, String.valueOf(user.getMinute()));
        eventRequest.addParameter(ParametersRequests.date, user.getDate());
        eventRequest.addParameter(ParametersRequests.role, user.getRole());
        eventRequest.addParameter(ParametersRequests.cityLat, String.valueOf(locationManager.getCityLocation().getLatitude()));
        eventRequest.addParameter(ParametersRequests.cityLong, String.valueOf(locationManager.getCityLocation().getLongitude()));


        eventRequest.setJsonObject();
        myRequest.createRequest(eventRequest);

    }

    public void joinDeleteEvent(JSONObject response) {

        try {
            if (!response.isNull("status") && response.getString("status").equals("success")) {
                Toast.makeText(context, "Perfetto", Toast.LENGTH_SHORT).show();
                managerActivity.terminateEventActivity();

            } else
                Toast.makeText(context, "OPS", Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private List<Address> computeCityCoordinate(String city) {
        List<Address> list = null;
        Geocoder geocoder = new Geocoder(context, Locale.ITALY);
        if (geocoder.isPresent())
            try {
                list = geocoder.getFromLocationName(city, 1);
            } catch (IOException e) {
                Toast.makeText(context, "Impossibile ricercare per la tua città al momento", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        return list;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
