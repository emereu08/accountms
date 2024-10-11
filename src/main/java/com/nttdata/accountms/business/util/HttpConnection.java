package com.nttdata.accountms.business.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@Component
public class HttpConnection {
    public StringBuffer ejecutarSolicitudHttp(String uri, String tipoConexion) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(tipoConexion);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
        } catch (Exception e){
            System.out.println("Error: "+e.getMessage());
        }
        return response;
    }

    public Map<String, Object> stringToMap(String response){
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.toMap();
    }




}
