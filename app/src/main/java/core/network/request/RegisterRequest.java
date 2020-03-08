package core.network.request;



import org.json.JSONException;
import org.json.JSONObject;

import core.manager.ManagerRequest;
import core.network.mapping.ActionMapping;
import core.network.mapping.ControllerMapping;


public class RegisterRequest extends AbstractRequestType {
    public RegisterRequest() {
        super(ControllerMapping.userManagment);

    }

    public void setJsonObject(){

        try {
            getJsonObject().put(ParametersRequests.username.toString(),getParameters().get(ParametersRequests.username));
            getJsonObject().put(ParametersRequests.password.toString(),getParameters().get(ParametersRequests.password));
            getJsonObject().put(ParametersRequests.email.toString(),getParameters().get(ParametersRequests.email));
            getJsonObject().put(ParametersRequests.name.toString(),getParameters().get(ParametersRequests.name));
            getJsonObject().put(ParametersRequests.surname.toString(),getParameters().get(ParametersRequests.surname));
            getJsonObject().put(ParametersRequests.company.toString(),Boolean.valueOf(getParameters().get(ParametersRequests.company)));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getUrlRequest() {
        return super.getUrlRequest()+ ActionMapping.signup;
    }

    @Override
    public void updateStatus(JSONObject response, ManagerRequest managerRequest) {

        managerRequest.register(response);

    }


}
