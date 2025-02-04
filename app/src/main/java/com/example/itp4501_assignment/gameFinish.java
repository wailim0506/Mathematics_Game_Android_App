package com.example.itp4501_assignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class gameFinish extends AppCompatActivity {
    private datebaseControl db = new datebaseControl();
    private int correctCount,time;
    private TextView count_textView,time_textView,result_textView ;
    private ImageView continue_button,mainMenu_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_finish);
        initialize();
        displayResult();

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(gameFinish.this, gamePlay.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                db.closeDB();
                startActivity(i);
            }
        });

        mainMenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(gameFinish.this, mainMenu.class);
                i.putExtra("username",getIntent().getStringExtra("username"));
                db.closeDB();
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
        correctCount = getIntent().getIntExtra("correctCount",0);
        time = getTimeUsed();
        count_textView = findViewById(R.id.gameFinish_count_textView);
        time_textView = findViewById(R.id.gameFinish_time_textView);
        result_textView = findViewById(R.id.gameFinish_result_textView);
        continue_button = findViewById(R.id.gameFinish_continue_button);
        mainMenu_button = findViewById(R.id.gameFinish_main_menu_button);
    }
    public void displayResult(){
        count_textView.setText("Correct: " + correctCount + ", Wrong: "+(10-correctCount));
        time_textView.setText("Time: " + time + " seconds");

        int incorrectCount = 10 - correctCount;
        if (correctCount > incorrectCount){
            Intent serviceIntent = new Intent(this, winSoundEffectService.class);
            startService(serviceIntent);
            result_textView.setText("YOU WIN!");
        }else if(correctCount < incorrectCount){
            Intent serviceIntent = new Intent(this, loseSoundEffectService.class);
            startService(serviceIntent);
            result_textView.setText("YOU LOSE!");
        }else{
            result_textView.setText("DRAW!");
        }
    }

    public int getTimeUsed(){
        Cursor c = db.ExecuteQuery("SELECT duration FROM GamesLog WHERE gameID = ?",new String[]{getIntent().getStringExtra("gameID")});
        while(c.moveToNext()){
            int index = c.getColumnIndex("duration");
            return c.getInt(index);
        }
        return getIntent().getIntExtra("time",0);
    }
}