package com.example.danielrocks.servicios;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.danielrocks.servicios.Collection.Item;
import com.example.danielrocks.servicios.Collection.ListAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadComponents();
    }
    private void loadComponents(){
        final ListView list = (ListView)this.findViewById(R.id.list_main);
        final ArrayList<Item> list_data = new ArrayList<Item>();
        /*for (int i=0; i<100; i++){
            Item p = new Item();
            p.id=i;
            p.title="Titulo" + i;
            p.description = "Descripcion " + i;
            p.url = "image " + i;
            list_data.add(p);
        }
        ListAdapter adapter=new ListAdapter(this,list_data);
        list.setAdapter(adapter);*/

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = null; //agregado por q sale error
        client.get("http://192.168.1.31:3000",params,new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                try {
                    JSONArray data = response.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        Item p = new Item();
                        JSONObject obj = data.getJSONObject(i);
                        p.id = i;
                        p.title = obj.getString("title");
                        p.description = obj.getString("descripcion");
                        p.url = obj.getString("image");
                        list_data.add(p);
                    }
                    ListAdapter adapter = new ListAdapter(MainActivity.this, list_data);
                    list.setAdapter(adapter);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable,JSONObject errorResponse){

            }

        });
    }

}

