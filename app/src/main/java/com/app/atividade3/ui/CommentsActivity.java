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
import com.app.atividade3.model.Comment;
import com.app.atividade3.model.User;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CommentsActivity extends AppCompatActivity implements Response.Listener<JSONArray>, Response.ErrorListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Volley (Biblioteca da requisição)
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, "https://jsonplaceholder.typicode.com/comments",
                null, this, this);

        queue.add(req);
    }

    @Override
    public void onResponse(JSONArray response) {
        ListView commentsList = findViewById(R.id.commentsList);
        ArrayList<String> arrayList = new ArrayList<>();
        String commentDataString;
        try {
            for(int i = 0; i < response.length(); i++){
                commentDataString = response.getJSONObject(i).toString();
                Comment commentObject = commentObjectMapper(commentDataString);
                commentDataString = formatTextToList(commentObject);
                if (commentDataString.equals("") || commentDataString == null){
                    commentDataString = "Sem dados de comentário!";
                    arrayList.add(commentDataString);
                }else {
                    arrayList.add(commentDataString);
                }
                if (i == 10){
                    i = response.length();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
            commentsList.setAdapter(arrayAdapter);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(CommentsActivity.this, "Erro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private Comment commentObjectMapper(String jsonString){
        Gson gson = new Gson();
        Comment comment = gson.fromJson(jsonString, Comment.class);
        return comment;
    }

    private String formatTextToList(Comment comment){
        String text =
                        "\t\tPostId: "     + comment.getPostId()    + "\n" +
                        "\t\tId: "         + comment.getId()        + "\n" +
                        "\t\tNome: "       + comment.getName()      + "\n" +
                        "\t\tE-mail: "     + comment.getEmail()     + "\n" +
                        "\t\tCorpo/Body: " + comment.getBody()      + "\n";
        return text;
    }
}