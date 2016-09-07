package com.px.px_utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by PX on 2016/8/27.
 */
public class Application extends android.app.Application {
    private static RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getVolleryRequest (){
        return requestQueue;
    }
}
