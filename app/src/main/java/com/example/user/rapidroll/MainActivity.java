package com.example.user.rapidroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    SharedPreferences pref1,pref;
    SharedPreferences.Editor editor,editor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button continue1 = (Button) findViewById(R.id.con);
        Button button = (Button) findViewById(R.id.start);
        Button high_score = (Button)findViewById(R.id.high_score);
        Button instruction = (Button) findViewById(R.id.instruction);
        Button about = (Button)findViewById(R.id.about);
        Button th = (Button) findViewById(R.id.th);
        //GameActivity.sc = 0;
        pref1 = getApplicationContext().getSharedPreferences("MyPrefI", MODE_PRIVATE);
        pref = getApplicationContext().getSharedPreferences("MyPrefJ", MODE_PRIVATE);
        int flag = pref1.getInt("flag",-1);
        if(flag == 1){
            continue1.setVisibility(View.VISIBLE);
            continue1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    Intent intent = new Intent(MainActivity.this,GameActivity.class);
                    startActivity(intent);
                }
            });
        }
        else if(flag == 0 || flag == -1){
            continue1.setVisibility(View.GONE);
            editor1 = pref1.edit();
            editor = pref.edit();
            editor1.putInt("flag",0);
            editor1.commit();
            editor.clear();
            editor.commit();
            GameActivity.sc = 0;
        }


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor1 = pref1.edit();
                editor = pref.edit();
                editor1.putInt("flag",0);
                editor1.commit();
                editor.clear();
                editor.commit();
                GameActivity.sc = 0;
                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
                GameActivity.sc = 0;

            }
        });
        th.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this, Themes.class);
                startActivity(intent);
            }
        });
        high_score.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this, HighScore.class);
                startActivity(intent);
            }
        });
        instruction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this,Instruction.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(MainActivity.this,About.class);
                startActivity(intent);
            }
        });

    }
    public void onBackPressed() {
        GameActivity.sc = 0;
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        System.exit(0);
        return;
    }
}
