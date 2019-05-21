package uk.ac.cam.gr3.weather.data.util;

import org.json.JSONObject;
import java.net.*;
import java.io.*;

//Gets a JASON object from the url given
class URLConnectionReader {
    static JSONObject getJSON(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;

        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return new JSONObject(content.toString());
    }
}
