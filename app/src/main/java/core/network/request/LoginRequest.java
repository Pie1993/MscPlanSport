package core.network.request;


import org.json.JSONException;
import org.json.JSONObject;

import core.manager.ManagerRequest;
import core.network.mapping.ActionMapping;
import core.network.mapping.ControllerMapping;


public class LoginRequest extends AbstractRequestType {
    public LoginRequest() {
        super(ControllerMapping.userLogin);

    }

    public void setJsonObject() {

        try {
            getJsonObject().put(ParametersRequests.username.toString(), getParameters().get(ParametersRequests.username));
            getJsonObject().put(ParametersRequests.password.toString(), getParameters().get(ParametersRequests.password));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getUrlRequest() {
        return super.getUrlRequest() + ActionMapping.login;
    }

    @Override
    public void updateStatus(JSONObject response, ManagerRequest managerRequest) {
        managerRequest.login(response);

    }


}
