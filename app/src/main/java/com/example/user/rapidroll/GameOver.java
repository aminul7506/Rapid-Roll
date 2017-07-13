package com.example.user.rapidroll;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by User on 6/11/2016.
 */
public class GameOver extends AppCompatActivity {
    TextView showScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);
        Button exit = (Button)findViewById(R.id.exit);
        showScore = (TextView) findViewById(R.id.showScore);
        showScore.setText("Score : " + GameActivity.sc);
        HighScore.db = openOrCreateDatabase("SCOREDB1", Context.MODE_PRIVATE, null);
        HighScore.db.execSQL("CREATE TABLE IF NOT EXISTS hs1(score INTEGER);");
        HighScore.db.execSQL("INSERT INTO hs1 VALUES(" + GameActivity.sc  + ");");
        GameActivity.sc = 0;
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                //Intent intent = new Intent(GameOver.this,GameActivity.class);
                //startActivity(intent);
                GameActivity.sc = 0;
                Intent intent = new Intent(GameOver.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                //android.os.Process.killProcess(android.os.Process.myPid());
               // finish();
                //System.exit(1);
                //return;
            }
        });
    }

    @Override
    public void onBackPressed() {
        GameActivity.sc = 0;
        Intent intent = new Intent(GameOver.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return;
    }
}
