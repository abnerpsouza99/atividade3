package com.app.atividade3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.app.atividade3.R;
import com.app.atividade3.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        // Volley (Biblioteca da requisição)
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/users",
                null, this, this);

        queue.add(req);

    }

    @Override
    public void onResponse(JSONArray response) {
        ListView usersList = findViewById(R.id.usersList);
        ArrayList<String> arrayList = new ArrayList<>();
        String userDataString;
        try {
            for(int i = 0; i < response.length(); i++){
                userDataString = response.getJSONObject(i).toString();
                User userObject = userObjectMapper(userDataString);
                userDataString = formatTextToList(userObject);
                if (userDataString.equals("") || userDataString == null){
                    userDataString = "Sem dados de Usuário!";
                    arrayList.add(userDataString);
                }else {
                    arrayList.add(userDataString);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            usersList.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(UsersActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private User userObjectMapper(String jsonString){
        Gson gson = new Gson();
        User user = gson.fromJson(jsonString, User.class);
        return user;
    }

    private String formatTextToList(User user){
        String text =
                "\t\tuserId: "   + user.getId()       + "\n" +
                "\t\tNome: "     + user.getName()     + "\n" +
                "\t\tUsername: " + user.getUsername() + "\n" +
                "\t\tE-mail: "   + user.getEmail()    + "\n" +
                "\t\tTelefone: " + user.getPhone()    + "\n" +
                "\t\tSite: "     + user.getWebsite()  + "\n";
        return text;
    }
}