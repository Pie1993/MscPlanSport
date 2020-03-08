package core.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import core.model.Event;
import core.model.User;
import gui.activity.EventActivity;
import gui.activity.HomeUserActivity;
import gui.fragment.FPAEvent;
import gui.fragment.FPAHomeUser;
import gui.fragment.FPALoginRegister;


public class ManagerActivity {

    public Map getUserEvents() {
        return userEvents;
    }

    public void registerActivity(Activity activity) {
        activities.put(activity.getClass(), activity);
    }

    public void stopProgressBarLoginRegister() {
        fpaLoginRegister.stopProgressBar();
    }

    public void terminateEventActivity() {
        EventActivity eventActivity = (EventActivity) activities.get(EventActivity.class);
        fpaEvent.stopProgressBar();
        eventActivity.finish();
    }

    public void closeKeyBoard(Activity activity) {
/*        View view = activity.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)
                   activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

 */
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public HashMap<Integer, Event> getEvents() {
        HashMap<Integer, Event> map = new HashMap<>();
        map.putAll(generalEvents);
        map.putAll(userEvents);
        map.putAll(otherEvents);
        return map;
    }

    public void goToEvent(int id) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra("event_key", id);
        context.startActivity(intent);
    }

    public void goToHomeUser() {
        Intent intent = new Intent(context, HomeUserActivity.class);
        context.startActivity(intent);
    }

    public void requestEvents() {
        managerRequest.requestEvents(user);
        fpaHomeUser.stopRefresh();
    }

    public void updateEvents(HashMap<Integer, Event> general_events, HashMap<Integer, Event> userEvents, HashMap<Integer, Event> otherEvents) {
        this.generalEvents = general_events;
        this.userEvents = userEvents;
        this.otherEvents = otherEvents;
        updateGui();
        fpaHomeUser.stopRefresh();
    }

    public void updateGui() {
        updateList();
        updateMap();
    }

    public void joinDeleteEvent(int event_id) {
        managerRequest.joinDeleteEventRequest(user.getUsername(), event_id);
    }

    public void login(Activity activity) {
        closeKeyBoard(activity);
        fpaLoginRegister.startProgressBar();
        managerRequest.requestLogin(fpaLoginRegister);
    }

    public void enableLocationPermission() {
        fpaHomeUser.enableLocationPermission();
    }

    public void register(Activity activity) {
        closeKeyBoard(activity);
        fpaLoginRegister.startProgressBar();
        managerRequest.requestRegister(fpaLoginRegister);
    }

    public void setUser(User user) {
        this.user = user;
    }

    private void updateList() {
        fpaHomeUser.updateList(generalEvents, userEvents, otherEvents);
    }

    private void updateMap() {
        fpaHomeUser.updateMap(getEvents());
    }

    public void updateMapLocation(boolean b) {
        fpaHomeUser.updateMapLocation(b);
    }

    public void setFragmentPageAdapter(FPAHomeUser fpaHomeUser) {
        this.fpaHomeUser = fpaHomeUser;
    }

    public void setFragmentPageAdapter(FPAEvent fpaEvent) {
        this.fpaEvent = fpaEvent;
    }

    public void setFragmentPageAdapter(FPALoginRegister fpaLoginRegister) {
        this.fpaLoginRegister = fpaLoginRegister;
    }

    public static ManagerActivity getInstance() {
        if (managerActivity == null)
            managerActivity = new ManagerActivity();
        return managerActivity;

    }

    private ManagerActivity() {
        activities = new HashMap<>();
        managerRequest = new ManagerRequest(this);
    }

    public User getUser() {
        return user;
    }

    public void setContext(Context context) {
        this.context = context;
        managerRequest.setContext(context);
    }

    public Activity getActivity(Class activity) {
        return activities.get(activity);
    }

    private static ManagerActivity managerActivity;

    private Context context;

    private HashMap<Integer, Event> generalEvents;

    private HashMap<Integer, Event> userEvents;

    private HashMap<Integer, Event> otherEvents;

    private User user;

    private ManagerRequest managerRequest;

    private FPAHomeUser fpaHomeUser;

    private FPALoginRegister fpaLoginRegister;

    private FPAEvent fpaEvent;

    private HashMap<Class, Activity> activities;

}
