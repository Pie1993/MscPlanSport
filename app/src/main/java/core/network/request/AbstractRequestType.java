package core.network.request;


import org.json.JSONObject;
import java.util.HashMap;

import core.manager.ManagerRequest;
import core.network.mapping.ControllerMapping;


public abstract class AbstractRequestType {

    private String url = "http://10.0.2.2:8080/msc20/";
    private ControllerMapping controllerMapping;
    private HashMap<ParametersRequests,String> parameters = new HashMap<>();
    private JSONObject jsonObject = new JSONObject();

    AbstractRequestType(ControllerMapping controllerMapping){
        this.controllerMapping=controllerMapping;
    }

    public String getUrlRequest()
    {
        return url + controllerMapping.toString()+"/";
    }

    HashMap<ParametersRequests, String> getParameters() {
        return parameters;
    }

    JSONObject getJsonObject()
    {
        return  jsonObject;
    }

    public void addParameter(ParametersRequests key, String value)
    {
        parameters.put(key,value);
    }

    abstract void setJsonObject();

    abstract void updateStatus(JSONObject response, ManagerRequest managerRequest);
}
