package task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import factory.Task;

public class BitcoinInfo extends Task {

    @Override
    public String executeTask() throws IOException {
        String all = getRequest("https://www.bitstamp.net/api/ticker/");
        response = all;
        return "All information got from site!";
    }

    public String getRequest(String url) throws IOException {
        StringBuilder response = new StringBuilder();

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
 
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        if (in != null) in.close();

        return response.toString();
    }
}
