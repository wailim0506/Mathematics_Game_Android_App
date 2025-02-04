package com.example.itp4501_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mainMenu extends AppCompatActivity {

    private ImageView play_imageView, howToPlay_imageView, ranking_imageView,
            record_imageView, close_imageView, logout_imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_menu);
        initialize();
        Intent serviceIntent = new Intent(this, BackgroundMusicService.class);


        play_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainMenu.this, gamePlay.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        howToPlay_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainMenu.this, howToPlay.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });
        ranking_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainMenu.this, ranking.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        record_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainMenu.this, userRecord.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        close_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(serviceIntent);
                finishAffinity();
            }
        });

        logout_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainMenu.this, Login.class);
                startActivity(i);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void initialize(){
        play_imageView = findViewById(R.id.main_menu_play_button);
        howToPlay_imageView = findViewById(R.id.main_menu_how_to_play_button);
        ranking_imageView = findViewById(R.id.main_menu_ranking_button);
        record_imageView = findViewById(R.id.main_menu_record_button);
        close_imageView = findViewById(R.id.main_menu_close_button);
        logout_imageView = findViewById(R.id.main_menu_logout_button);
    }
}