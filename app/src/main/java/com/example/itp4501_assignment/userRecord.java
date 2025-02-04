package com.example.itp4501_assignment;

import android.content.Intent;
import android.database.Cursor;
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

public class userRecord extends AppCompatActivity {

    private datebaseControl db = new datebaseControl();
    private RecyclerView recycler_view;
    private MyAdapter adapter;
    private String[] dataArray;

    private ImageView back_button;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.user_record);

        username = getIntent().getStringExtra("username");

        back_button = findViewById(R.id.record_btnBack);

        recycler_view = findViewById(R.id.record_rV);

        // set RecyclerView as a list
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        dataArray = getUserRecord(username);


        // pass data to adapter
        adapter = new MyAdapter(this, dataArray);
        // set adapter to recycler_view
        recycler_view.setAdapter(adapter);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userRecord.this, mainMenu.class);
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

    private String[] getUserRecord(String username){
        String[] list = new String[getNumberOfRecord(username)];
        Cursor c = db.ExecuteQuery("SELECT * FROM GamesLog WHERE username = ?",new String[]{username});
        int i = 0;
        while(c.moveToNext()){
            int dateIndex = c.getColumnIndex("playDate");
            int timeIndex = c.getColumnIndex("playTime");
            int correctCountIndex = c.getColumnIndex("correctCount");
            int durationIndex = c.getColumnIndex("duration");

            list[i] = c.getString(dateIndex) + ", " + c.getString(timeIndex) + "," +
                    " " + c.getInt(correctCountIndex) + " correct," +
                    " " + c.getString(durationIndex) + " sec";
            i++;
        }
        c.close();
        return list;
    }

    private int getNumberOfRecord(String username){
        Cursor c = db.ExecuteQuery("SELECT COUNT(*) FROM GamesLog WHERE username = ?", new String[]{username});
        int count = 0;
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }
}