package com.example.katnapper.todo;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by KATnapper on 21-05-2017.
 */

public class DownloadItem extends AsyncTask<String,Void,JSONObject> {


    private DownloadCallBack callback;

    public DownloadItem(DownloadCallBack callback) {
        this.callback = callback;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            return downloadData(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Cannot read file",e.getMessage());
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSON not found",e.getMessage());
            return null;
        }

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject)
    {
        super.onPostExecute(jsonObject);
        if(jsonObject!=null)
        {
            this.callback.updatestufffromdownload(jsonObject);
        }
    }

    private JSONObject downloadData(String link) throws IOException, JSONException {
        URL url = new URL(link);
        JSONObject myobj = null;
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");
        connection.connect();
        if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            String inputdata;
            StringBuilder builder = new StringBuilder();

            while ((inputdata = bufferedReader.readLine()) != null){
                builder.append(inputdata);

            }

            myobj = new JSONObject(builder.toString());
        }

        return myobj;
    }



}
