package com.example.shaoo.blooddonationapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ehsan on 03-10-2017.
 */

public interface VolleyCallback {
    void onSuccessResponse(JSONObject result) throws JSONException;
}