package com.example.consumoapivolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQue;
    private TextView txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtInfo = findViewById(R.id.txtVisualiza);

        requestQue = Volley.newRequestQueue(this);
        consumeApiVolley();

    }

    private void consumeApiVolley(){
        String url="https://gorest.co.in/public/v1/users";
        JsonObjectRequest requestJson = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Response(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error de Conexión:"+
                                error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue.add(requestJson);
    }

    private void Response(JSONObject jObject){
        txtInfo.setText("");
        try {
            //Lo Guardo en un arreglo para obtener los datos por posición
            JSONArray jsonArray = new JSONArray(jObject.get("data").toString());

            for (int i=0 ; i < jsonArray.length(); i++){
                //Lo Guardo en un objeto el arreglo para obtener sus parámetros
                JSONObject objJson=new JSONObject(jsonArray.get(i).toString());

                txtInfo.append("ID: " + objJson.getString("id") + "\n\n");
                txtInfo.append("Name: " + objJson.getString("name") + "\n\n");
                txtInfo.append("Email: " + objJson.getString("email") + "\n\n");
                txtInfo.append("Gender: " + objJson.getString("gender") + "\n\n");
                txtInfo.append("Status: " + objJson.getString("status") + "\n\n");
                txtInfo.append("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n\n");
            }
        }catch (JSONException e){
            Toast.makeText(this,"Error al cargar lista: "+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}