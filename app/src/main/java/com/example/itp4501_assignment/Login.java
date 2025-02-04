package com.example.itp4501_assignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {

    private TextView username_textView, password_textView, message_textView;

    private ImageView login_imageView, register_imageView;
    private datebaseControl db = new datebaseControl();
    private passwordHandler passwordHandler = new passwordHandler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login);

        initialize();
        initializeDB();

        Intent serviceIntent = new Intent(this, BackgroundMusicService.class);
        startService(serviceIntent);


        login_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        register_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    @SuppressLint("ResourceAsColor")
    public void login(){
        String uname = getUsernameInput();
        String pw = getPWInput();

        if (uname.compareTo("") == 0 || pw.compareTo("") == 0){
            message_textView.setVisibility(View.VISIBLE);
            message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
            message_textView.setText("Username and password cannot be empty!");
            return;
        }

        boolean passwordMatch = false;
        String[] arg = {uname};
        Cursor cursor = db.ExecuteQuery("SELECT * FROM User WHERE username = ?",arg);
        if (cursor.moveToFirst()){ //check have user
            int passwordIndex = cursor.getColumnIndex("password");
            String pwInDB = cursor.getString(passwordIndex);
            String decryptedPassword = passwordHandler.passwordDecrypt(pwInDB);

            if (decryptedPassword.compareTo(pw) == 0){
                passwordMatch = true;
            }else{
                message_textView.setVisibility(View.VISIBLE);
                message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
                message_textView.setText("Password incorrect!");
            }
        }else{//no user match username
            message_textView.setVisibility(View.VISIBLE);
            message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
            message_textView.setText("User not found!");
        }

        if (passwordMatch){
            Intent i = new Intent(Login.this, mainMenu.class);
            i.putExtra("username",uname);
            db.closeDB();
            startActivity(i);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void register(){
        String uname = getUsernameInput();
        String pw = getPWInput();

        if (uname.compareTo("") == 0 || pw.compareTo("") == 0){
            message_textView.setVisibility(View.VISIBLE);
            message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
            message_textView.setText("Username and password cannot be empty!");
            return;
        }

        String[] arg = {uname};
        Cursor cursor = db.ExecuteQuery("SELECT * FROM User WHERE username = ?",arg);
        if (cursor.moveToFirst()){ //check username is used
            message_textView.setVisibility(View.VISIBLE);
            //message_textView.setTextColor(R.color.red);
            message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
            message_textView.setText("Username already used!");
        }else{  //username not used
            boolean formatCorrect = passwordHandler.checkPwFormat(pw);
            if (formatCorrect){
                String encryptedPassword = passwordHandler.passwordEncrypt(pw);
                if (db.ExecuteNonQuery("INSERT INTO User (username, password) VALUES (" + "'" + uname + "','" + encryptedPassword + "')")){
                    message_textView.setVisibility(View.VISIBLE);
                    message_textView.setTextColor(ContextCompat.getColor(this, R.color.black));
                    message_textView.setText("Register success, you can now login.");
                }else{
                    message_textView.setVisibility(View.VISIBLE);
                    message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
                    message_textView.setText("Please try again.");
                }
            }else{
                    message_textView.setVisibility(View.VISIBLE);
                    message_textView.setTextColor(ContextCompat.getColor(this, R.color.red));
                    message_textView.setText("Password format: a-z, A-Z, 0-9, !@$%&#*?/");
            }
        }
    }

    public void initialize(){
        username_textView = findViewById(R.id.login_username_editText);
        password_textView = findViewById(R.id.login_password_editText);
        register_imageView = findViewById(R.id.login_register_button);
        message_textView = findViewById(R.id.login_message_textView);
        login_imageView = findViewById(R.id.login_login_button);
    }

    public void initializeDB(){
        //db.ExecuteNonQuery("DROP TABLE if exists GamesLog;");
        //db.ExecuteNonQuery("DROP TABLE if exists User;");
        boolean r = db.ExecuteNonQuery("CREATE TABLE IF NOT EXISTS GamesLog (gameID text PRIMARY KEY, username text,playDate text , playTime text, duration text, correctCount int);");
        boolean rr = db.ExecuteNonQuery("CREATE TABLE IF NOT EXISTS User (username text PRIMARY KEY, password text)");
    }

    public String getUsernameInput(){
        return username_textView.getText().toString();
    }

    public String getPWInput(){
        return password_textView.getText().toString();
    }
}

