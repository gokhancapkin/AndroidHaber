package com.example.gkhan.androidhaber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.gkhan.androidhaber.HaberList.HaberContents;
import static com.example.gkhan.androidhaber.HaberList.HaberHeaders;

public class Haber extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haber);
        TextView hHeader = (TextView)  findViewById(R.id.hHeader);
        ImageView haberImage = (ImageView)  findViewById(R.id.hImage);
        TextView hContent = (TextView)  findViewById(R.id.hContent);
        Bundle hlist = getIntent().getExtras();
        int pos = hlist.getInt("pos");
        hHeader.setText(HaberHeaders.get(pos));
        hContent.setText(HaberContents.get(pos));
        //postData(2, pos);
    }
    public void postData(int id,int col) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.36:22536/api/First/Up");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("col", String.valueOf(col)));
            nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(id)));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
}
