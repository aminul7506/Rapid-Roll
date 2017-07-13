package com.example.user.rapidroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

/**
 * Created by Sojal on 29-Oct-16.
 */

public class Themes extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RadioButton rd1,rd2,rd3,rd4,rd5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.theme);
        rd5 = (RadioButton) findViewById(R.id.rd5);
        rd4 = (RadioButton) findViewById(R.id.rd4);
        rd3 = (RadioButton) findViewById(R.id.rd3);
        rd2 = (RadioButton) findViewById(R.id.rd2);
        rd1 = (RadioButton) findViewById(R.id.rd1);
        pref = getApplicationContext().getSharedPreferences("MyTheme", MODE_PRIVATE);
        int flag = pref.getInt("flag0",5);
        if(flag == 5)rd5.setChecked(true);
        else if (flag == 4)rd4.setChecked(true);
        else if (flag == 3)rd3.setChecked(true);
        else if (flag == 2)rd2.setChecked(true);
        else if (flag == 1)rd1.setChecked(true);
        rd5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor = pref.edit();
                editor.putInt("flag0",5);
                editor.commit();
            }
        });
        rd4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor = pref.edit();
                editor.putInt("flag0",4);
                editor.commit();
            }
        });
        rd3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor = pref.edit();
                editor.putInt("flag0",3);
                editor.commit();
            }
        });
        rd2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor = pref.edit();
                editor.putInt("flag0",2);
                editor.commit();
            }
        });
        rd1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                editor = pref.edit();
                editor.putInt("flag0",1);
                editor.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        GameActivity.sc = 0;
        Intent intent = new Intent(Themes.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return;
    }
}