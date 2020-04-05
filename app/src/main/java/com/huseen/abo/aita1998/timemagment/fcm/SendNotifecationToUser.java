package com.huseen.abo.aita1998.timemagment.fcm;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class SendNotifecationToUser {

    static final private String contentType = "application/json";
    static final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    static final private String serverKey = "key=" + "AAAAImbv_RE:APA91bGEnVBuipI6PaY2UNuGgdefLRbCbDk4maChYIGaLY_iCQ1vOXr57AHXr3dW_kdzYnsvu5BDF4G6uoSfXoA3TMbMNZriMf1LVUKaWE8AmmuQ9SNxjBpoVIBDV_qgav4Il_JXcAZM";


    public static void send(String type ,String title ,String token ,String message , String topic, Context context){
        FirebaseMessaging.getInstance().subscribeToTopic("/topics/"+topic);
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();

        try {
            notifcationBody.put("title", title);
            notifcationBody.put("type", type);
            notifcationBody.put("message", message) ;  //Enter your notification message
            notification.put("to", token);
            notification.put("data", notifcationBody);
            sendDataToUser(notification,context);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private static void sendDataToUser(JSONObject notification, Context context) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("AAAA","sendDataToUser == >success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("AAAA","sendDataToUser == >error");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
