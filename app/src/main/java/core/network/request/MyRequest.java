package core.network.request;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import core.manager.ManagerRequest;
import core.network.mapping.JsonParameter;



public class MyRequest {

    private Context context;

    private ManagerRequest managerRequest;


    public MyRequest(Context context,ManagerRequest managerRequest) {
        this.context = context;
        this.managerRequest=managerRequest;
    }

    public void createRequest(final AbstractRequestType requestType) {


        final JSONObject jsonObject = requestType.getJsonObject();



        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, requestType.getUrlRequest(), jsonObject, new Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        requestType.updateStatus(response,managerRequest);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put(JsonParameter.connection.toString(), JsonParameter.error.toString());
                            requestType.updateStatus(jsonObject,managerRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

        Connection.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


}
