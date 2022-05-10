package com.example.myapplication.checkin_guest.viewModel.Executor;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendNotification {
    private static final String SERVERKEY = "AAAAMiuk2SU:APA91bHPgK9Y5eEDEFKTJwEWEq9yvTaNTo-ny_8VvPT6rVQZYDGr6qRNmJe8A70jspHTVNVgXUNpum0mHKdpCnlIgzxpegpeUg7HQ8Ue300UvmqBrPL3r29xCH6ah3Kzp2inB5v4oyCq";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static void sendNotification(String regToken, String title, String message){
        new Thread(()->{
            OkHttpClient client = new OkHttpClient();
            JSONObject json = new JSONObject();
            JSONObject dataJson = new JSONObject();
            try {
                dataJson.put("body", message);
                dataJson.put("title", title);
                json.put("notification", dataJson);
                json.put("to", regToken);

                RequestBody body = RequestBody.create(JSON, json.toString());
                Request request = new Request.Builder()
                        .header("Authorization", "key="+SERVERKEY)
                        .url("https://fcm.googleapis.com/fcm/send")
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
                String finalResponse = response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("error", e+"");
            }
        }).start();
    }
}
