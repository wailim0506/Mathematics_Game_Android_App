package com.example.itp4501_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.*;

public class gamePlay extends AppCompatActivity {


    private datebaseControl db = new datebaseControl();
    private Calendar calendar;

    private static int questionNum = 0;

    private static int time = 0;  //count time
    private static int pirateHitCount = 0;
    private static int knightHitCount = 0;
    private static String gameID;
    private static String username;

    private static int day,month,year,hour,minute;
    private static String date,gameStartTime;

    private ImageView pirate_imageView, knight_imageView, resultImage_imageView,
                    doneBtn_imageView, nextQuestionBtn_imageView, gameFinishBtn_imageView;
    private TextView firstNum_textView, secondNum_textView, operator_textView, timer_textView,
                    correctAnswer_textView, questionNum_textView, knightHit_textView,
                    pirateHit_textView;

    private EditText userInput_editText;

    private Handler handler;

    private knightState knightState;
    private pirateState pirateState;

    private int done_button_click_time; //count how many time user click done button in each question
    private int backgroundNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.game_play);

        randomBackground();
        initialize();
        questionGenerate();
        timerStart();



        nextQuestionBtn_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput_editText.setText("");
                recreate();
            }
        });

        gameFinishBtn_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(gamePlay.this, gameFinish.class);
                i.putExtra("gameID", gameID);
                i.putExtra("correctCount", knightHitCount);
                i.putExtra("time", time);
                i.putExtra("username", username);
                db.closeDB();
                resetStaticVariable();
                timerStop();
                startActivity(i);
            }
        });

        doneBtn_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (done_button_click_time == 0){ //if already click done button, cannot click the done
                                                //button again in the same question to avoid
                                                //the animation run again
                    checkAnswer();


                    if (questionNum == 10){
                        db.ExecuteNonQuery("INSERT INTO GamesLog (gameID, username, playDate, playTime, " +
                                        "duration, correctCount) VALUES (" + "'" + gameID + "', '" + username + "', " +
                                "'" + date + "', " + "'" + " " + gameStartTime + "', '" + time + "', " + knightHitCount + ")");


                    }
                }
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int numberGenerator(){
        Random random = new Random();
        return random.nextInt(100) + 1;  // random generate from 0-99,
                                               // but +1 mean it will become 1-100
    }

    private String operatorGenerator(){
        Random random = new Random();
        int num = random.nextInt(4); //0-3

        switch (num){
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            default:
                return "/";
        }
    }

    private void questionGenerate(){  //check the question is valid or invalid
        int num1 = numberGenerator();
        int num2 = numberGenerator();
        String operator = operatorGenerator();



        if (num1 < 1 || num1 > 100){ //double check if it exceed the range 1-100
            num1 = numberGenerator();
        }

        if (num2 < 1 || num2 > 100){ //same as above
            num2 = numberGenerator();
        }

        firstNum_textView.setText(""+num1);
        secondNum_textView.setText(""+num2);
        operator_textView.setText(operator);
        num1_translate_in();
        num2_translate_in();
        operator_translate_in();

        if (operator.compareTo("-") == 0){ //minus
            if(num1 < num2){ // if num1 < num2, result in negative number
                int tempNum = num1;
                num1 = num2;
                num2 = tempNum;
            }

            firstNum_textView.setText(""+num1);
            secondNum_textView.setText(""+num2);
            operator_textView.setText(operator);
            num1_translate_in();
            num2_translate_in();
            operator_translate_in();
        }

        if (operator.compareTo("/") == 0){ //division
            if(num1 < num2){  //if num1 < num2, not divisible
                int tempNum = num1;
                num1 = num2;
                num2 = tempNum;
            }

            int remainder = num1 % num2;  //check is divisible

            if(remainder != 0){  //not divisible, adjust num1 to nearest divisible value
                num1 -= remainder;
                remainder = num1 % num2;
            }

            if (remainder == 0){
                firstNum_textView.setText(""+num1);
                secondNum_textView.setText(""+num2);
                operator_textView.setText(operator);
                num1_translate_in();
                num2_translate_in();
                operator_translate_in();
            }
        }
    }

    private void pirateAttack(){  //when user answer wrong
        pirateState.run();
        pirate_translate_run();
        Intent knifeIntent = new Intent(this, knifeSoundEffectService.class);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(knifeIntent);
                pirateState.attack();
            }
        }, 1100);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pirateState.walkBack();
                pirate_translate_walk_back();
            }
        }, 2100);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pirateState.idle();
            }
        }, 5500);
    }

    private void knightAttack(){  //when user answer correct
        knightState.run();
        knight_translate_run();
        Intent knifeIntent = new Intent(this, knifeSoundEffectService.class);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(knifeIntent);
                knightState.attack();
            }
        }, 1100);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                knightState.walkBack();
                knight_translate_walk_back();
            }
        }, 2100);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                knightState.idle();
            }
        }, 5300);
    }

    private void checkAnswer(){  //check user answer is correct or not
        if (userInput_editText.getText().toString().compareTo("") != 0){  //check have answer input
            done_button_click_time++;
            int num1 = Integer.parseInt(firstNum_textView.getText().toString());
            int num2 = Integer.parseInt(secondNum_textView.getText().toString());
            String operator = operator_textView.getText().toString();

            int expectedAnswer;

            switch (operator){
                case "+":
                    expectedAnswer = num1 + num2;
                    break;
                case "-":
                    expectedAnswer = num1 - num2;
                    break;
                case "*":
                    expectedAnswer = num1 * num2;
                    break;
                default:
                    expectedAnswer = num1 / num2;
                    break;
            }
            int userAnswer;

            try{ //avoid too larger input that exceed the data type int
                userAnswer = Integer.parseInt(userInput_editText.getText().toString());
            }catch (Exception e){
                userAnswer = 99999; //largest possible answer is 10000
            }


            if (expectedAnswer == userAnswer){
                Intent correctIntent = new Intent(this, correctSoundEffectService.class);
                startService(correctIntent);
                resultImage_imageView.setImageResource(R.drawable.correct);
                result_translate_in();
                knightAttack();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        result_translate_out();
                    }
                }, 1800);
                knightHitCount++;
                knightHit_textView.setText("Hits: " + knightHitCount);
            }else{
                Intent wrongIntent = new Intent(this, wrongSoundEffectService.class);
                startService(wrongIntent);
                resultImage_imageView.setImageResource(R.drawable.wrong);
                correctAnswer_textView.setText("Answer is " + expectedAnswer + "!");
                result_translate_in();
                correctAnswer_translate_in();
                pirateAttack();
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        result_translate_out();
                    }
                }, 1800);
                pirateHitCount++;
                pirateHit_textView.setText("Hits: " + pirateHitCount);
            }
            userInput_editText.setTextColor(Color.BLACK);
            userInput_editText.setEnabled(false);

            //decide to show next question or finish game
            if (questionNum < 10){
                next_question_alpha_in();
            }else{
                nextQuestionBtn_imageView.setVisibility(View.GONE);
                game_finish_alpha_in();
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private void randomBackground(){  //random background every game
        LinearLayout main;
        main = findViewById(R.id.main);

        Random random = new Random();
        backgroundNumber = random.nextInt(4); //4 background in total

        if (questionNum == 10){ //to match gameFinish background
            backgroundNumber = 0;
        }

        switch (backgroundNumber){
            case 0:
                main.setBackgroundResource(R.drawable.game_bg1);
                return;
            case 1:
                main.setBackgroundResource(R.drawable.game_bg2);
                return;
            case 2:
                main.setBackgroundResource(R.drawable.game_bg3);
                return;
            default:
                main.setBackgroundResource(R.drawable.game_bg4);
        }


    }

    private void timerStart(){
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                time++;
                timer_textView.setText("Time: " + time + "s");
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void timerStop() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private String gameIDGenerator(){
        //count how many record in gameLog table first
        int count = 0;
        Cursor c = db.ExecuteQuery("SELECT COUNT(*) FROM GamesLog", null);
        if (c.moveToFirst()){
            count = c.getInt(0);
            count++;
        }else{
            return "G" + count;
        }
        return "G" + count;
    }

    private static void resetStaticVariable(){
        questionNum = 0;
        time = 0;
        pirateHitCount = 0;
        knightHitCount = 0;
        gameID = "";
        day = 0;
        month = 0;
        year = 0;
        hour = 0;
        minute = 0;
        date = "";
        gameStartTime = "";
    }




    private void initialize(){
        firstNum_textView = findViewById(R.id.firstNum);
        secondNum_textView = findViewById(R.id.secondNum);
        operator_textView = findViewById(R.id.operator);
        timer_textView = findViewById(R.id.timer);
        doneBtn_imageView = findViewById(R.id.doneBtn);
        userInput_editText = findViewById(R.id.userInput);
        pirate_imageView = findViewById(R.id.pirate);
        knight_imageView = findViewById(R.id.knight);
        resultImage_imageView = findViewById(R.id.resultImage);
        nextQuestionBtn_imageView = findViewById(R.id.gamePlay_next_question_button);
        gameFinishBtn_imageView = findViewById(R.id.gamePlay_finish_button);
        correctAnswer_textView = findViewById(R.id.correct_answer);
        questionNum_textView = findViewById(R.id.questionNum);
        pirateHit_textView = findViewById(R.id.pirateHit);
        knightHit_textView = findViewById(R.id.knightHit);

        knightState = new knightState(knight_imageView);
        pirateState = new pirateState(pirate_imageView);

        questionNum++;
        questionNum_textView.setText("Question " + questionNum);
        pirateHit_textView.setText("Hits: " + pirateHitCount);
        knightHit_textView.setText("Hits: " + knightHitCount);

        done_button_click_time = 0;

        if (questionNum == 1) {
            calendar = Calendar.getInstance();
            gameID = gameIDGenerator();
            username = getIntent().getStringExtra("username");
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
            hour = calendar.get(Calendar.HOUR_OF_DAY);
            minute = calendar.get(Calendar.MINUTE);

            date = day + "-" + month + "-" + year;

            if (minute < 10){
                gameStartTime = hour + ":" + "0" + minute;
            }else{
                gameStartTime = hour + ":" + minute;
            }
        }
    }






    //animation
    private void result_translate_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.result_translate_in);
        resultImage_imageView.setVisibility(View.VISIBLE);
        resultImage_imageView.startAnimation(translateAnimation);
    }

    private void result_translate_out(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.result_translate_out);
        resultImage_imageView.startAnimation(translateAnimation);
        resultImage_imageView.setVisibility(View.INVISIBLE);
    }

    private void num1_translate_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.num1_translate_in);
        firstNum_textView.startAnimation(translateAnimation);
    }

    private void num2_translate_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.num2_translate_in);
        secondNum_textView.startAnimation(translateAnimation);
    }

    private void operator_translate_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.operator_translate_in);
        operator_textView.startAnimation(translateAnimation);
    }

    private void correctAnswer_translate_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.corrans_alpha);
        correctAnswer_textView.setVisibility(View.VISIBLE);
        correctAnswer_textView.startAnimation(translateAnimation);
    }

    private void pirate_translate_run(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.pirate_translate_run);
        pirate_imageView.startAnimation(translateAnimation);
    }

    private void pirate_translate_walk_back(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.pirate_translate_walk_back);
        pirate_imageView.startAnimation(translateAnimation);
    }

    private void knight_translate_run(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.knight_translate_run);
        knight_imageView.startAnimation(translateAnimation);
    }

    private void knight_translate_walk_back(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.knight_translate_walk_back);
        knight_imageView.startAnimation(translateAnimation);
    }

    private void next_question_alpha_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.nextquestion_alpha_in);
        nextQuestionBtn_imageView.setVisibility(View.VISIBLE);
        nextQuestionBtn_imageView.startAnimation(translateAnimation);
    }

    private void game_finish_alpha_in(){
        Animation translateAnimation = AnimationUtils.loadAnimation(gamePlay.this, R.anim.gamefinish_alpha_in);
        gameFinishBtn_imageView.setVisibility(View.VISIBLE);
        gameFinishBtn_imageView.startAnimation(translateAnimation);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

}