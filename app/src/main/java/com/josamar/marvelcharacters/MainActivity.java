package com.josamar.marvelcharacters;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.josamar.marvelcharacters.adaptadores.Adaptador;
import com.josamar.marvelcharacters.model.CharacterResponse;
import com.josamar.marvelcharacters.model.InfoCharacter;
import com.josamar.marvelcharacters.model.Information;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cafsoft.foundation.Data;
import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLRequest;
import cafsoft.foundation.URLSession;

public class MainActivity extends AppCompatActivity {
    private final String host="https://gateway.marvel.com/";
    private final String service="v1/public/characters?name=";
    private final String service2="v1/public/characters?nameStartsWith=";

    private final String ts = "ts=1";
    private final String key="apikey=8cdcc2b1aae5031008dc6fced5d7a1c4";
    private final String hash="hash=960c8da07bc9a8c57aa90d3701716297";
    //---------------------------------------------------------
    private ImageView character = null;
    private TextView txName = null;
    private EditText inputName = null;
    private Button btnGet = null;
    private int i;
    private ListView characters = null;

    //---------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
    }
    private void initViews(){
        character=findViewById(R.id.imgCharacter);
        txName = findViewById(R.id.txName);
        inputName = findViewById(R.id.txEdName);
        btnGet = findViewById(R.id.btnGet);
        characters= findViewById(R.id.lsView);
    }
    private void initEvents(){
        btnGet.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                characterInfo();
            }
        });
    }
    public void characterInfo(){
        String characterName = inputName.getText().toString();
        if(characterName.length()>0){
            //getCharacter(characterName);
            getCharacters(characterName);
        }else{
            Toast.makeText(this, "fields_empty", Toast.LENGTH_SHORT).show();
        }
    }
    public void getCharacter(String characterName){
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
            character.setImageBitmap(image);
        });
    }

    public Bitmap dataToImage(Data data){
        return BitmapFactory.decodeByteArray(data.toBytes(),0,data.length());
    }
    //------------------------------------------------------------------------
    public void getCharacters(String characterName){
        String strUrl = host + service2 +characterName;
        String query = strUrl +'&'+ts+'&'+key+'&'+hash;
        Log.d("Response", query);
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
                InfoCharacter chInfo = json.fromJson(text,InfoCharacter.class);
                processImages(chInfo);
            }
        }).resume();
    }
    public void processImages(InfoCharacter chInfo) {
        List<String> strURLs = new ArrayList<>();
        Log.d("Response1", "Holaaaaa1");
        for (int i = 0; i < chInfo.getCantResults(); i++) {
            strURLs.add(chInfo.getImage(i));
        }
        List<URL> urls = new ArrayList<>();
        Log.d("Response2", "Holaaaaa2");
        try {
            for (int i = 0; i < strURLs.size(); i++) {
                urls.add(new URL(strURLs.get(i)));
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        List<Bitmap> images =new ArrayList<>();
        List<String> names =new ArrayList<>();
        List<Information> infos = new ArrayList<>();
        for (this.i=0; this.i < urls.size(); this.i++) {
            final String nombre= chInfo.getName(this.i);
            Log.d("ResponseFor"+String.valueOf(i), "Holaaaaa"+String.valueOf(i));
            URLRequest request = new URLRequest(urls.get(i));
            Log.d("reObj", String.valueOf(request));
            URLSession.getShared().dataTask(request, (data, response, error) -> {
                HTTPURLResponse resp = (HTTPURLResponse) response;
                if (resp.getStatusCode() == 200) {
                    final Bitmap image = dataToImage(data);
                    //images.add(image);
                    //names.add(chInfo.getName(this.i));
                    final Information info= new Information(nombre,image);
                    infos.add(info);
                    Log.d("ResponseImages", "Hola desde images");
                    if(this.i == urls.size()){
                        Log.d("ResponseIfListImages", "Hola desde el if ejej saludos");
                        showImages(images,names,infos);
                    }
                }
            }).resume();
        }
    }

    public void showImages(List<Bitmap> images,List<String> names,List<Information> infoChar){
        Adaptador miAdaptador = new Adaptador(this,R.layout.items,infoChar);
        runOnUiThread(() -> {
            //character.setImageBitmap(image);
            //String m =
            Log.d("RRRRRRRRRRRRRRR", "showImages: ");
            //Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
            //View charac = LayoutInflater.from(this).inflate(R.layout.activity_main,null);
            //ImageView chMarvel = (ImageView) charac.findViewById(R.layout.)
            //characters.addFooterView();
            //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.items,names);
            characters.setAdapter(miAdaptador);
            //Toast.makeText(this, infoChar.get(0).getName(), Toast.LENGTH_SHORT).show();
            Log.d("RRRRRRRRRRRRRRR", "showImages: " + infoChar.size());
            //character.setImageBitmap(infoChar.get(0).getImage());
        });
    }
}
