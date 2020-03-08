package core.network.request;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import com.android.volley.toolbox.Volley;

public class Connection {


    private static Connection instance;
    private RequestQueue requestQueue;

    private Context ctx;

    private Connection(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized Connection getInstance(Context context) {
        if (instance == null) {
            instance = new Connection(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}
