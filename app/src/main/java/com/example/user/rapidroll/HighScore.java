package com.example.user.rapidroll;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static com.example.user.rapidroll.GameActivity.sc;
import static java.sql.Types.INTEGER;

/**
 * Created by User on 6/11/2016.
 */
public class HighScore extends AppCompatActivity {
    TextView o1,o2,o3,o4,o5;
    static SQLiteDatabase db;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscore);
        o1 = (TextView) findViewById(R.id.o1);
        o2 = (TextView) findViewById(R.id.o2);
        o3 = (TextView) findViewById(R.id.o3);
        o4 = (TextView) findViewById(R.id.o4);
        o5 = (TextView) findViewById(R.id.o5);
        o1.setText("1.          " + 0);
        o2.setText("2.          " + 0);
        o3.setText("3.          " + 0);
        o4.setText("4.          " + 0);
        o5.setText("5.          " + 0);
        int sco = GameActivity.sc;
        db = openOrCreateDatabase("SCOREDB1", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS hs1(score INTEGER);");
       // db.execSQL("INSERT INTO hs VALUES(" + sco  + ");");
        db.execSQL("DELETE FROM hs1 WHERE score=" + 0 + "");
        ArrayList <Integer> buffer = new ArrayList();
        c = db.rawQuery("SELECT * FROM hs1", null);
        if (c.getCount() == 0) {
            buffer.add(0);
            buffer.add(0);
            buffer.add(0);
            buffer.add(0);
            buffer.add(0);

        }
        else {
            while (c.moveToNext()) {
                buffer.add(c.getInt(0));
            }
        }
        buffer.add(0);
        buffer.add(0);
        buffer.add(0);
        buffer.add(0);
        buffer.add(0);
        Collections.sort(buffer);

        int len = buffer.size();
            o1.setText("1.          " + buffer.get(len-1));
            o2.setText("2.          " + buffer.get(len-2));
            o3.setText("3.          " + buffer.get(len-3));
            o4.setText("4.          " + buffer.get(len-4));
            o5.setText("5.          " + buffer.get(len-5));
        int temp = buffer.get(len-5);
        db.execSQL("DELETE FROM hs1 WHERE score<" + temp + "");
    }
    @Override
    public void onBackPressed() {
        GameActivity.sc = 0;
        Intent intent = new Intent(HighScore.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return;
    }
}
