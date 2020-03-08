package core.network.request;

import org.json.JSONException;
import org.json.JSONObject;


import core.manager.ManagerRequest;
import core.network.mapping.ActionMapping;
import core.network.mapping.ControllerMapping;

public class EventRequest extends AbstractRequestType {

    public EventRequest() {
        super(ControllerMapping.msc20Events);

    }

    public void setJsonObject() {

        try {
            getJsonObject().put(ParametersRequests.username.toString(), getParameters().get(ParametersRequests.username));
            getJsonObject().put(ParametersRequests.preferredSport.toString(), getParameters().get(ParametersRequests.preferredSport));
            getJsonObject().put(ParametersRequests.userLat.toString(), Double.valueOf(getParameters().get(ParametersRequests.userLat)));
            getJsonObject().put(ParametersRequests.userLong.toString(), Double.valueOf(getParameters().get(ParametersRequests.userLong)));
            getJsonObject().put(ParametersRequests.cityLat.toString(), Double.valueOf(getParameters().get(ParametersRequests.cityLat)));
            getJsonObject().put(ParametersRequests.cityLong.toString(), Double.valueOf(getParameters().get(ParametersRequests.cityLong)));
            getJsonObject().put(ParametersRequests.city.toString(), getParameters().get(ParametersRequests.city));
            getJsonObject().put(ParametersRequests.hour.toString(), getParameters().get(ParametersRequests.hour));
            getJsonObject().put(ParametersRequests.minute.toString(), getParameters().get(ParametersRequests.minute));
            getJsonObject().put(ParametersRequests.date.toString(), getParameters().get(ParametersRequests.date));
            getJsonObject().put(ParametersRequests.role.toString(),getParameters().get(ParametersRequests.role));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getUrlRequest() {
        return super.getUrlRequest() + ActionMapping.events;
    }

    @Override
    public void updateStatus(JSONObject response, ManagerRequest managerRequest) {

    managerRequest.updateEvents(response);

    }

}
