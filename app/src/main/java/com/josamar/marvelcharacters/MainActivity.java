package com.josamar.marvelcharacters;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.IpSecManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.josamar.marvelcharacters.model.CharacterResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import cafsoft.foundation.Data;
import cafsoft.foundation.DataTaskCompletionHandler;
import cafsoft.foundation.Error;
import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLResponse;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {
    private final String host="https://gateway.marvel.com/";
    private final String service="v1/public/characters?name=";
    private final String ts = "ts=1";
    private final String key="apikey=8cdcc2b1aae5031008dc6fced5d7a1c4";
    private final String hash="hash=960c8da07bc9a8c57aa90d3701716297";
    //---------------------------------------------------------
    private ImageView p = null;
    //---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        getCharacter();
    }
    private void initViews(){
        p=findViewById(R.id.personaje);
    }
    public void getCharacter(){
        String characterName="wasp";
        String strUrl = host + service +characterName;
        String query = strUrl +'&'+ts+'&'+key+'&'+hash;
        URL url = null;
        try {
            url = new URL(query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLRequest request = new URLRequest(url);
        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            if (resp.getStatusCode() == 200) {
                String text = data.toText();

                Gson json = new Gson();
                CharacterResponse chInfo = json.fromJson(text,CharacterResponse.class);
                processImage(chInfo);
            }
        }).resume();
    }
    public void processImage(CharacterResponse chInfo) {
        String strURL = chInfo.getImage();
        URL url = null;
        try {
            url = new URL(strURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLRequest request = new URLRequest(url);
        URLSession.getShared().dataTask(request, (data, response, error) -> {
            HTTPURLResponse resp = (HTTPURLResponse) response;
            if (resp.getStatusCode() == 200) {
                final Bitmap image = dataToImage(data);
                showImage(image);
            }
        }).resume();
    }
    public void showImage(Bitmap image){

        runOnUiThread(() -> {
            p.setImageBitmap(image);
        });
    }

    public Bitmap dataToImage(Data data){
        return BitmapFactory.decodeByteArray(data.toBytes(),0,data.length());
    }
}
