package com.example.gkhan.androidhaber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override//http://192.168.1.36:22536/api/First/Get
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new HttpAsyncTask().execute("http://192.168.1.37:22536/api/First/Get");
        setContentView(R.layout.activity_main);


    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
      protected void onPreExecute() {
         super.onPreExecute();

      }
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                result = result.replaceAll("\\\\","");
                JSONObject com = new JSONObject(result.substring(1,result.lastIndexOf("\"")));
                // Getting JSON Array node
                JSONArray contacts = com.getJSONArray("haber");
                // looping through All Contacts
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    HaberList.HaberIds.add(c.getInt("Id"));
                    HaberList.HaberTurs.add(c.getString("tur"));
                    HaberList.HaberHeaders.add(c.getString("HaberHeader"));
                    HaberList.HaberImages.add(R.drawable.team);
                    HaberList.HaberContents.add(c.getString("HaberContent"));
                    HaberList.HaberLikes.add(c.getInt("LikeNum"));
                    HaberList.HaberUnlikes.add(c.getInt("UnlikeNum"));
                    HaberList.ViewNums.add(c.getInt("ViewNum"));
                }

                recyclerView = (RecyclerView) findViewById(R.id.recylerview);
                HaberAdapter haberAdapter = new HaberAdapter(MainActivity.this, HaberList.getData());
                recyclerView.setAdapter(haberAdapter);
                CardView card = (CardView) findViewById(R.id.Card);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }
        }
    }
}