package com.example.itp4501_assignment;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyThread {
    private String url;
    String[] list;
    String data="";
    boolean parsingComplete = true;

    public MyThread(String url){
        this.url = url;
    }

    public String[] getList(){
        return list;
    }

    public void fetchJSON(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL urlLink = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) urlLink.openConnection();
                    connection.setConnectTimeout(5000);
                    connection.setRequestMethod("GET");
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    int inputStreamData = inputStreamReader.read();
                    while(inputStreamData >= 0){
                        char current = (char) inputStreamData;
                        inputStreamData = inputStreamReader.read();
                        data += current;
                    }
                    readAndParseJSON(data);
                    inputStream.close();
                }catch(Exception e){
                    Log.d("error1",""+e);
                }
            }
        });
        thread.start();
    }

    public void readAndParseJSON(String data){
        try{
            //assignment
            JSONArray jsonArray = new JSONArray(data);
            list = new String[jsonArray.length()];

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("Name");
                int correct = jsonObject.getInt("Correct");
                int time = jsonObject.getInt("Time");
                list[i] = "Rank " + (i+1) + ", " + name + ", " + correct + " corrects, " + time + " secs";
            }

            parsingComplete = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
