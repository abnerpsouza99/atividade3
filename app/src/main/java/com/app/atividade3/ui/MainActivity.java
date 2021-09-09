package com.app.atividade3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.atividade3.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnUsers = findViewById(R.id.btnUsers);
        Button btnComments = findViewById(R.id.btnComments);
        Button btnPosts = findViewById(R.id.btnPosts);
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });
        btnComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CommentsActivity.class);
                startActivity(intent);
            }
        });
        btnPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });
    }
}