package core.network.request;



import org.json.JSONException;
import org.json.JSONObject;

import core.manager.ManagerRequest;
import core.network.mapping.ActionMapping;
import core.network.mapping.ControllerMapping;

public class JoinDeleteRequest extends AbstractRequestType {

    public JoinDeleteRequest() {
        super(ControllerMapping.msc20Events);

    }

    public void setJsonObject(){

        try {
            getJsonObject().put(ParametersRequests.username.toString(),getParameters().get(ParametersRequests.username));
            getJsonObject().put(ParametersRequests.event_key.toString(),getParameters().get(ParametersRequests.event_key));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getUrlRequest() {
        return super.getUrlRequest()+ ActionMapping.joinDeleteEvent;
    }

    @Override
    public void updateStatus(JSONObject response, ManagerRequest managerRequest) {
       managerRequest.joinDeleteEvent(response);

    }


}
