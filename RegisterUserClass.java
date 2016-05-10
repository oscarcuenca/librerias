package com.amg_eservices.iot.RegistroyAcceso;


/**
 * Created by Propietario on 27/02/2016.
 */
import android.util.Log;

import com.amg_eservices.iot.UtilitiesGlobal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class RegisterUserClass {

    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                response = br.readLine();
            }
            else {
                response="Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        // este método es el que fallaba, lo demas estaba bastante bien. Te lo he reescrito, fijate en
        // la ventana de logs y veras que funciona bien.
        String result;
        String user_name = params.get(UtilitiesGlobal.USER_NAME);
        String user_email = params.get(UtilitiesGlobal.USER_EMAIL);
        String contrasena = params.get(UtilitiesGlobal.USER_PASSWORD);

        result = UtilitiesGlobal.USER_NAME + "=" + user_name + "&"
                + UtilitiesGlobal.USER_EMAIL + "=" + user_email + "&"
                + UtilitiesGlobal.USER_PASSWORD + "=" + contrasena;

        Log.i(UtilitiesGlobal.TAG, "result: " + result);

        return result;
    }
}

