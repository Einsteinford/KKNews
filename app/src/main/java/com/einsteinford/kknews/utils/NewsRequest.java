package com.einsteinford.kknews.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.einsteinford.kknews.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KK on 2016-10-31.
 */

public class NewsRequest extends StringRequest {

    public static final String APP_KEY = "Authorization";
    public static final String APP_CODE = "APPCODE 9ca28ededa62458e989311aecb491f5d";

    public NewsRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.GET,url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put(APP_KEY,APP_CODE);
        return headers;
    }
}
