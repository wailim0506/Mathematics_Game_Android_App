package com.example.itp4501_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ranking extends AppCompatActivity {

    private RecyclerView recycler_view;
    private MyAdapter adapter;
    private String[] dataArray;
    private MyThread myThread;

    private ImageView back_button;

    //Assignment
    String url = "https://ranking-mobileasignment-wlicpnigvf.cn-hongkong.fcapp.run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ranking);

        back_button = findViewById(R.id.ranking_btnBack);

        recycler_view = findViewById(R.id.ranking_rV);

        // set RecyclerView as a list
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        //JSON
        myThread = new MyThread(url);
        myThread.fetchJSON();
        while (myThread.parsingComplete);
        dataArray = myThread.getList();
        //JSON

        // pass data to adapter
        adapter = new MyAdapter(this, dataArray);
        // set adapter to recycler_view
        recycler_view.setAdapter(adapter);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ranking.this, mainMenu.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}