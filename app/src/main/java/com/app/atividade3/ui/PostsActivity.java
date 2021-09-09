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
import com.app.atividade3.model.Post;
import com.app.atividade3.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PostsActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        // Volley (Biblioteca da requisição)
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/posts",
                null, this, this);

        queue.add(req);
    }

    @Override
    public void onResponse(JSONArray response) {
        ListView postsList = findViewById(R.id.postsList);
        ArrayList<String> arrayList = new ArrayList<>();
        String postDataString;
        try {
            for(int i = 0; i < response.length(); i++){
                postDataString = response.getJSONObject(i).toString();
                Post postObject = postObjectMapper(postDataString);
                postDataString = formatTextToList(postObject);
                if (postDataString.equals("") || postDataString == null){
                    postDataString = "Sem dados de Post!";
                    arrayList.add(postDataString);
                }else {
                    arrayList.add(postDataString);
                }
                if (i == 10){
                    i = response.length();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            postsList.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(PostsActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Post postObjectMapper(String jsonString){
        Gson gson = new Gson();
        Post post = gson.fromJson(jsonString, Post.class);
        return post;
    }

    private String formatTextToList(Post post){
        String text =
                        "\t\tuserId: "      + post.getUserId()   + "\n" +
                        "\t\tId: "          + post.getId()       + "\n" +
                        "\t\tTítulo: "      + post.getTitle()    + "\n" +
                        "\t\tCorpo/body: "  + post.getBody()     + "\n";
        return text;
    }
}