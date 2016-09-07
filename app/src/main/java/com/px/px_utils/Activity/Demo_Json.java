package com.px.px_utils.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.px.px_utils.Application;
import com.px.px_utils.Json.ApkInformation;
import com.px.px_utils.Json.LoadApkInfoTask;
import com.px.px_utils.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by PX on 2016/8/28.
 */
public class Demo_Json extends BaseActivity {

    private TextView tv_Json;
    private String url = "http://158.69.229.104:8090/json/test.html";
    private String url1 = "http://158.69.229.104:8090/json/test1.json";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_json);

        tv_Json = (TextView) findViewById(R.id.tv_json);

//       new LoadApkInfoTask().execute(url1);
       getData1(url1);
    }

    public void getData(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("----px----",s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("----px----",volleyError.getMessage());
            }
        });
        Application.getVolleryRequest().add(stringRequest);
    }

    public void getData1(String url) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("----px----",jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = null;
                        try {
                            name = URLDecoder.decode(jsonObject.getString("apkName"),"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Log.d("----px----",name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("----px----",volleyError.getMessage());
            }
        });
        Application.getVolleryRequest().add(jsonArrayRequest);
    }


//    {
//        @Override
//        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
//        JSONArray jsonArray;
//        try {
//            jsonArray = new JSONArray(new String(response.data,"UTF-8"));
//            return Response.success(jsonArray, HttpHeaderParser.parseCacheHeaders(response));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            return Response.error(new ParseError(e));
//        }
//    }
//    }
}
