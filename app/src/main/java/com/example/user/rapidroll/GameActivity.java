package com.example.user.rapidroll;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

@SuppressWarnings("deprecation") public class GameActivity extends AppCompatActivity implements SensorEventListener {
    TextView score, life;
    Button pause;
    SharedPreferences pref,pref1;
    SharedPreferences.Editor editor,editor1;
    LinearLayout lh;
    int lhH = 0;
    int level = 1;
    int die = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    int rect1y,rect2y,rect3y,rect4y,rect5y,rect6y,rect7y,rect8y;
    int rect1x,rect2x,rect3x,rect4x,rect5x,rect6x,rect7x,rect8x;
    int des1x,des2x,des3x,des4x,des1y,des2y,des3y,des4y;
    int recX = 90,recY = 25;
    int cx = 0,cy = 0,radius = 16;
    int x ,y;
    static int sc = 0;
    int scCount = 0;
    int button = 0,ind = 0;
    int scLife = 3;
    Random rand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        LinearLayout l = (LinearLayout)findViewById(R.id.l);
        lh = (LinearLayout)findViewById(R.id.lh);
        score = (TextView)findViewById(R.id.score);
        life = (TextView)findViewById(R.id.life);
        pause = (Button)findViewById(R.id.pause);
        rand = new Random();
        x = getWindowManager().getDefaultDisplay().getWidth();
        y = getWindowManager().getDefaultDisplay().getHeight();
        lhH = lh.getHeight();

      //  if(pref1 == null) {
            pref1 = getApplicationContext().getSharedPreferences("MyPrefI", MODE_PRIVATE);
            //editor1 = pref1.edit();
        //}
        //if(pref == null) {
            pref = getApplicationContext().getSharedPreferences("MyPrefJ", MODE_PRIVATE);
            //editor = pref.edit();
        //}
        int flag = pref1.getInt("flag", -1);
        if(flag == 0 || flag == -1) {
            sc = 0;
            rect1y = y / 4;
            rect2y = y - y / 7;
            rect3y = y / 2;
            rect4y = y - y / 12;
            rect5y = y / 8;
            rect6y = y - y / 16;
            rect7y = y / 5 - 25;
            rect8y = y - y / 4;
            des1y = y - y / 5;
            des2y = y / 6;
            des3y = y - y / 6;
            des4y = y / 32;
            rect1x = rand.nextInt(x / 2);
            rect2x = rand.nextInt(x / 2);
            rect3x = rand.nextInt(x / 2);
            rect4x = rand.nextInt(x / 2);
            des1x = rand.nextInt(x / 2);
            des2x = rand.nextInt(x / 2);
            rect5x = rand.nextInt(x / 2) + x / 2;
            if (rect5x + recX > x) rect5x -= recX;
            rect6x = rand.nextInt(x / 2) + x / 2;
            if (rect6x + recX > x) rect6x -= recX;
            rect7x = rand.nextInt(x / 2) + x / 2;
            if (rect7x + recX > x) rect7x -= recX;
            rect8x = rand.nextInt(x / 2) + x / 2;
            if (rect8x + recX > x) rect8x -= recX;
            des3x = rand.nextInt(x / 2) + x / 2;
            if (des3x + recX > x) des3x -= recX;
            des4x = rand.nextInt(x / 2) + x / 2;
            if (des4x + recX > x) des4x -= recX;
            cx = rect8x + recX / 2;
            cy = rect8y - radius;
            life.setText("Lives : " + scLife);
            score.setText("Score : " + sc);

        }

        else if(flag == 1){
            rect1x = pref.getInt("rect1x", 0);
            rect2x = pref.getInt("rect2x", 0);
            rect3x = pref.getInt("rect3x", 0);
            rect4x = pref.getInt("rect4x", 0);
            rect5x = pref.getInt("rect5x", 0);
            rect6x = pref.getInt("rect6x", 0);
            rect7x = pref.getInt("rect7x", 0);
            rect8x = pref.getInt("rect8x", 0);
            rect1y = pref.getInt("rect1y", 0);
            rect2y = pref.getInt("rect2y", 0);
            rect3y = pref.getInt("rect3y", 0);
            rect4y = pref.getInt("rect4y", 0);
            rect5y = pref.getInt("rect5y", 0);
            rect6y = pref.getInt("rect6y", 0);
            rect7y = pref.getInt("rect7y", 0);
            rect8y = pref.getInt("rect8y", 0);
            des1x = pref.getInt("des1x",0);
            des2x = pref.getInt("des2x",0);
            des3x = pref.getInt("des3x",0);
            des4x = pref.getInt("des4x",0);
            des1y = pref.getInt("des1y",0);
            des2y = pref.getInt("des2y",0);
            des3y = pref.getInt("des3y",0);
            des4y = pref.getInt("des4y",0);
            cx = pref.getInt("cx",0);
            cy = pref.getInt("cy",0);
            sc = pref.getInt("sc",0);
            scCount = pref.getInt("scCount",0);
            scLife = pref.getInt("scLife",0);
            life.setText("Lives : " + scLife);
            score.setText("Score : " + sc);
        }
        getSupportActionBar().hide();
        l.addView(new DrawRect(this));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(button%2 == 0){
                    pause.setText("Resume");
                    button++;
                }
                else {
                    pause.setText("Pause");
                    button++;
                }
            }
        });
    }
    public class DrawRect extends View
    {
        Paint paint;

        public DrawRect(Context context)
        {
            super(context);

            paint = new Paint();


        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            SharedPreferences pref0 = getApplicationContext().getSharedPreferences("MyTheme", MODE_PRIVATE);
            int flag0 = pref0.getInt("flag0",5);
            if(flag0 == 5) paint.setColor(Color.LTGRAY);
            else if(flag0 == 4) paint.setColor(Color.WHITE);
            else if(flag0 == 3) paint.setColor(Color.BLACK);
            else if(flag0 == 2) paint.setColor(Color.MAGENTA);
            else if(flag0 == 1) paint.setColor(Color.GREEN);
            canvas.drawPaint(paint);
            paint.setColor(Color.parseColor("#FF474FF1"));
            scCount++;
            if (scCount == 10000000) scCount = 0;
            if (scCount % 10 == 0) sc++;
            score.setText("Score : " + sc);
            if(sc != 0 && scCount % 10000 == 0 && scLife <= 5){
                scLife++;
                life.setText("Lives : " + scLife);
                score.setText("Score : " + sc);
            }
            if (sc == 400) {
                level = 2;
                func();
                //cy++;
            } else if (sc == 800) {
                level = 3;
                func();
            }
            else if (sc == 1200){
                level = 4;
                func();
            }
            else if (sc == 1600){
                level = 5;
                func();
            }

            if (die == 1) die++;
            else if (die == 2) die = 0;
            else die = 0;

                if (Math.abs(cy - y) <= 6) {
                    //cx = 80;
                    //cy = 100;
                    die = 1;
                    if (rect1y >= y / 2) {
                        cx = rect1x + recX / 2;
                        cy = rect1y - radius;
                    } else if (rect5y >= y / 2) {
                        cx = rect5x + recX / 2;
                        cy = rect5y - radius;
                    } else if (rect2y >= y / 2) {
                        cx = rect2x + recX / 2;
                        cy = rect2y - radius;
                    } else if (rect6y >= y / 2) {
                        cx = rect6x + recX / 2;
                        cy = rect6y - radius;
                    } else if (rect3y >= y / 2) {
                        cx = rect3x + recX / 2;
                        cy = rect3y - radius;
                    } else if (rect7y >= y / 2) {
                        cx = rect7x + recX / 2;
                        cy = rect7y - radius;
                    } else if (rect4y >= y / 2) {
                        cx = rect4x + recX / 2;
                        cy = rect4y - radius;
                    } else if (rect8y >= y / 2) {
                        cx = rect8x + recX / 2;
                        cy = rect8y - radius;
                    }
                    if (scLife > 0) scLife--;
                    life.setText("Lives : " + scLife);
                }
                if (cy <= lhH + radius) {
                    die = 1;
                    if (rect1y >= y / 2) {
                        cx = rect1x + recX / 2;
                        cy = rect1y - radius;
                    } else if (rect5y >= y / 2) {
                        cx = rect5x + recX / 2;
                        cy = rect5y - radius;
                    } else if (rect2y >= y / 2) {
                        cx = rect2x + recX / 2;
                        cy = rect2y - radius;
                    } else if (rect6y >= y / 2) {
                        cx = rect6x + recX / 2;
                        cy = rect6y - radius;
                    } else if (rect3y >= y / 2) {
                        cx = rect3x + recX / 2;
                        cy = rect3y - radius;
                    } else if (rect7y >= y / 2) {
                        cx = rect7x + recX / 2;
                        cy = rect7y - radius;
                    } else if (rect4y >= y / 2) {
                        cx = rect4x + recX / 2;
                        cy = rect4y - radius;
                    } else if (rect8y >= y / 2) {
                        cx = rect8x + recX / 2;
                        cy = rect8y - radius;
                    }
                    if (scLife > 0) scLife--;
                    life.setText("Lives : " + scLife);
                }
                if (rect1y <= lhH + radius) {
                    rect1x = rand.nextInt(x / 2);
                    rect1y = y;
                }
                if (rect2y <= lhH + radius) {
                    rect2x = rand.nextInt(x / 2);
                    rect2y = y;
                }
                if (rect3y <= lhH + radius) {
                    rect3x = rand.nextInt(x / 2);
                    rect3y = y;
                }
                if (rect4y <= lhH + radius) {
                    rect4x = rand.nextInt(x / 2);
                    //rect4x = 100;
                    rect4y = y;
                }
                if (rect5y <= lhH + radius) {
                    rect5x = rand.nextInt(x / 2) + x / 2;
                    if (rect5x + recX > x) rect5x -= recX;
                    rect5y = y;
                }
                if (rect6y <= lhH + radius) {
                    rect6x = rand.nextInt(x / 2) + x / 2;
                    if (rect6x + recX > x) rect6x -= recX;
                    rect6y = y;
                }
                if (rect7y <= lhH + radius) {
                    rect7x = rand.nextInt(x / 2) + x / 2;
                    if (rect7x + recX > x) rect7x -= recX;
                    rect7y = y;
                }
                if (rect8y <= lhH + radius) {
                    rect8x = rand.nextInt(x / 2) + x / 2;
                    if (rect8x + recX > x) rect8x -= recX;
                    rect8y = y;
                }
                if (des1y <= lhH + radius) {
                    des1x = rand.nextInt(x / 2);
                    des1y = y;
                }
                if (des2y <= lhH + radius) {
                    des2x = rand.nextInt(x / 2);
                    des2y = y;
                }
                if (des3y <= lhH + radius) {
                    des3x = rand.nextInt(x / 2) + x / 2;
                    if (des3x + recX > x) des3x -= recX;
                    des3y = y;
                }
                if (des4y <= lhH + radius) {
                    des4x = rand.nextInt(x / 2) + x / 2;
                    if (des4x + recX > x) des4x -= recX;
                    des4y = y;
                }
            if (button % 2 == 0) {

                if (level == 1) {
                    if (cx >= rect1x && cx <= rect1x + recX && ((radius + cy == rect1y || Math.abs(rect1y - cy - radius) == 1 || Math.abs(rect1y - cy - radius) == 2))) {
                        if (cy + radius == rect1y) cy -= 2;
                        else if (Math.abs(rect1y - cy - radius) == 1) {
                            cy--;
                        }
                        ind = 1;
                    } else if (cx >= rect2x && cx <= rect2x + recX && ((radius + cy == rect2y || Math.abs(rect2y - cy - radius) == 1 || Math.abs(rect2y - cy - radius) == 2))) {
                        if (cy + radius == rect2y) cy -= 2;
                        else if (Math.abs(rect2y - cy - radius) == 1) {
                            cy--;
                        }
                        ind = 1;
                    } else if (cx >= rect3x && cx <= rect3x + recX && ((radius + cy == rect3y || Math.abs(rect3y - cy - radius) == 1 || Math.abs(rect3y - cy - radius) == 2))) {
                        if (cy + radius == rect3y) cy -= 2;
                        else if (Math.abs(rect3y - cy - radius) == 1) {
                            cy--;
                        }
                        ind = 1;
                    } else if (cx >= rect4x && cx <= rect4x + recX && ((radius + cy == rect4y || Math.abs(rect4y - cy - radius) == 1 || Math.abs(rect4y - cy - radius) == 2))) {
                        if (cy + radius == rect4y) cy -= 2;
                        else if (Math.abs(rect4y - cy - radius) == 1) {
                            cy--;
                        }
                        ind = 1;
                    } else if (cx >= rect5x && cx <= rect5x + recX && ((radius + cy == rect5y || Math.abs(rect5y - cy - radius) == 1 || Math.abs(rect5y - cy - radius) == 2))) {
                        if (rect5y == cy + radius) cy -= 2;
                        else if (Math.abs(rect5y - cy - radius) == 1) cy--;
                        ind = 1;
                    } else if (cx >= rect6x && cx <= rect6x + recX && ((radius + cy == rect6y || Math.abs(rect6y - cy - radius) == 1 || Math.abs(rect6y - cy - radius) == 2))) {
                        ind = 1;

                        if (rect6y == cy + radius) cy -= 2;
                        else if (Math.abs(rect6y - cy - radius) == 1) cy--;
                    } else if (cx >= rect7x && cx <= rect7x + recX && ((radius + cy == rect7y || Math.abs(rect7y - cy - radius) == 1 || Math.abs(rect7y - cy - radius) == 2))) {
                        ind = 1;
                        if (rect7y == cy + radius) cy -= 2;
                        else if (Math.abs(rect7y - cy - radius) == 1) cy--;
                    } else if (cx >= rect8x && cx <= rect8x + recX && ((radius + cy == rect8y || Math.abs(rect8y - cy - radius) == 1 || Math.abs(rect8y - cy - radius) == 2))) {
                        ind = 1;
                        if (rect8y == cy + radius) cy -= 2;
                        else if (Math.abs(rect8y - cy - radius) == 1) cy--;
                    }
                    if (ind != 1) {
                        cy += 2;

                    }
                    ind = 0;

                    if (cx >= des1x && cx <= des1x + recX && ((radius + cy == des1y || Math.abs(radius + cy - des1y) == 1 || Math.abs(radius + cy - des1y) == 2))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 2;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 2;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 2;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 2;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 2;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 2;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 2;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 2;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des2x && cx <= des2x + recX && ((radius + cy == des2y || radius + cy - des2y == 1 || radius + cy - des2y == 2))) {


                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 2;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 2;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 2;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 2;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 2;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 2;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 2;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 2;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des3x && cx <= des3x + recX && ((radius + cy == des3y || radius + cy - des3y == 1 || radius + cy - des3y == 2))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 2;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 2;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 2;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 2;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 2;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 2;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 2;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 2;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des4x && cx <= des4x + recX && ((radius + cy == des4y || radius + cy - des4y == 1 || radius + cy - des4y == 2))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 2;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 2;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 2;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 2;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 2;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 2;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 2;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 2;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    }


                    rect1y -= 2;
                    rect2y -= 2;
                    rect3y -= 2;
                    rect4y -= 2;
                    rect5y -= 2;
                    rect6y -= 2;
                    rect7y -= 2;
                    rect8y -= 2;
                    des1y -= 2;
                    des2y -= 2;
                    des3y -= 2;
                    des4y -= 2;

               /* if ((rect1x - cx == 1 || rect1x - cx == 2) && cy - rect1y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect2x - cx == 1 || rect2x - cx == 2) && cy - rect2y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect3x - cx == 1 || rect3x - cx == 2) && cy - rect3y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect4x - cx == 1 || rect4x - cx == 2) && cy - rect4y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect5x - cx == 1 || rect5x - cx == 2) && cy - rect5y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect6x - cx == 1 || rect6x - cx == 2) && cy - rect6y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect7x - cx == 1 || rect7x - cx == 2) && cy - rect7y <= recY - 2) {
                    cx -= 2;
                }
                if ((rect8x - cx == 1 || rect8x - cx == 2) && cy - rect8y <= recY - 2) {
                    cx -= 2;
                }*/
                }
                if (level == 2) {
                    if (cx >= rect1x && cx <= rect1x + recX && ((radius + cy == rect1y || Math.abs(rect1y - cy - radius) == 1 || Math.abs(rect1y - cy - radius) == 2 || Math.abs(rect1y - cy - radius) == 3))) {
                        if (cy + radius == rect1y) cy -= 3;
                        else if (Math.abs(rect1y - cy - radius) == 1) {
                            cy -= 2;
                        } else if (Math.abs(rect1y - cy - radius) == 2) cy--;
                        ind = 1;
                    } else if (cx >= rect2x && cx <= rect2x + recX && ((radius + cy == rect2y || Math.abs(rect2y - cy - radius) == 1 || Math.abs(rect2y - cy - radius) == 2 || Math.abs(rect2y - cy - radius) == 3))) {
                        if (cy + radius == rect2y) cy -= 3;
                        else if (Math.abs(rect2y - cy - radius) == 1) {
                            cy -= 2;
                        } else if (Math.abs(rect2y - cy - radius) == 2) cy--;
                        ind = 1;
                    } else if (cx >= rect3x && cx <= rect3x + recX && ((radius + cy == rect3y || Math.abs(rect3y - cy - radius) == 1 || Math.abs(rect3y - cy - radius) == 2 || Math.abs(rect3y - cy - radius) == 3))) {
                        if (cy + radius == rect3y) cy -= 3;
                        else if (Math.abs(rect3y - cy - radius) == 1) {
                            cy -= 2;
                        } else if (Math.abs(rect3y - cy - radius) == 2) cy--;
                        ind = 1;
                    } else if (cx >= rect4x && cx <= rect4x + recX && ((radius + cy == rect4y || Math.abs(rect4y - cy - radius) == 1 || Math.abs(rect4y - cy - radius) == 2 || Math.abs(rect4y - cy - radius) == 3))) {
                        if (cy + radius == rect4y) cy -= 3;
                        else if (Math.abs(rect4y - cy - radius) == 1) {
                            cy -= 2;
                        } else if (Math.abs(rect1y - cy - radius) == 2) cy--;
                        ind = 1;
                    } else if (cx >= rect5x && cx <= rect5x + recX && ((radius + cy == rect5y || Math.abs(rect5y - cy - radius) == 1 || Math.abs(rect5y - cy - radius) == 2 || Math.abs(rect5y - cy - radius) == 3))) {
                        if (rect5y == cy + radius) cy -= 3;
                        else if (Math.abs(rect5y - cy - radius) == 1) cy -= 2;
                        else if (Math.abs(rect5y - cy - radius) == 2) cy--;
                        ind = 1;
                    } else if (cx >= rect6x && cx <= rect6x + recX && ((radius + cy == rect6y || Math.abs(rect6y - cy - radius) == 1 || Math.abs(rect6y - cy - radius) == 2 || Math.abs(rect6y - cy - radius) == 3))) {
                        ind = 1;

                        if (rect6y == cy + radius) cy -= 3;
                        else if (Math.abs(rect6y - cy - radius) == 1) cy -= 2;
                        else if (Math.abs(rect6y - cy - radius) == 2) cy--;
                    } else if (cx >= rect7x && cx <= rect7x + recX && ((radius + cy == rect7y || Math.abs(rect7y - cy - radius) == 1 || Math.abs(rect7y - cy - radius) == 2 || Math.abs(rect7y - cy - radius) == 3))) {
                        ind = 1;
                        if (rect7y == cy + radius) cy -= 3;
                        else if (Math.abs(rect7y - cy - radius) == 1) cy -= 2;
                        else if (Math.abs(rect7y - cy - radius) == 2) cy--;
                    } else if (cx >= rect8x && cx <= rect8x + recX && ((radius + cy == rect8y || Math.abs(rect8y - cy - radius) == 1 || Math.abs(rect8y - cy - radius) == 2 || Math.abs(rect8y - cy - radius) == 3))) {
                        ind = 1;
                        if (rect8y == cy + radius) cy -= 3;
                        else if (Math.abs(rect8y - cy - radius) == 1) cy -= 2;
                        else if (Math.abs(rect8y - cy - radius) == 2) cy--;
                    }
                    if (ind != 1) {
                        cy += 3;

                    }
                    ind = 0;

                    if (cx >= des1x && cx <= des1x + recX && ((radius + cy == des1y || Math.abs(radius + cy - des1y) == 1 || Math.abs(radius + cy - des1y) == 2 || Math.abs(radius + cy - des1y) == 3))) {
                /*if(Math.abs(des1y - cy - radius) == 0){
                    cy -= 2;
                    des1y -=2;
                }
                else if(Math.abs(des1y - cy - radius) == 1) {
                    cy--;
                    des1y--;
                }
                //else if(des1y == radius + cy) cy -= 2;*/

                        // canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 3;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 3;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 3;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 3;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 3;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 3;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 3;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 3;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des2x && cx <= des2x + recX && ((radius + cy == des2y || radius + cy - des2y == 1 || radius + cy - des2y == 2 || Math.abs(radius + cy - des2y) == 3))) {
               /* if(Math.abs(des2y - cy - radius) == 0){
                    cy -= 2;
                    des2y -= 2;
                }
                else if(Math.abs(des2y - cy - radius) == 1) {
                    cy--;
                    des2y--;
                }
                //else if(des2y == radius + cy) cy -= 2;*/
                        //cy += 3;
                        //canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 3;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 3;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 3;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 3;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 3;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 3;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 3;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 3;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des3x && cx <= des3x + recX && ((radius + cy == des3y || radius + cy - des3y == 1 || radius + cy - des3y == 2 || Math.abs(radius + cy - des3y) == 3))) {
               /* if(Math.abs(des3y - cy - radius) == 0) {
                    cy -= 2;
                    des3y -= 2;
                }
                else if(Math.abs(des3y - cy - radius) == 1){
                    cy--;
                    des3y--;
                }
                //else if(des3y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 3;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 3;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 3;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 3;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 3;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 3;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 3;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 3;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des4x && cx <= des4x + recX && ((radius + cy == des4y || radius + cy - des4y == 1 || radius + cy - des4y == 2 || Math.abs(radius + cy - des4y) == 3))) {
                /*if(Math.abs(des4y - cy - radius) == 0) {
                    cy -= 2;
                    des4y -= 2;
                }
                else if(Math.abs(des4y - cy - radius) == 1){
                    cy--;
                    des4y--;
                }
                //else if(des4y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 3;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 3;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 3;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 3;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 3;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 3;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 3;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 3;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    }


                    rect1y -= 3;
                    rect2y -= 3;
                    rect3y -= 3;
                    rect4y -= 3;
                    rect5y -= 3;
                    rect6y -= 3;
                    rect7y -= 3;
                    rect8y -= 3;
                    des1y -= 3;
                    des2y -= 3;
                    des3y -= 3;
                    des4y -= 3;
                }

                if (level == 3) {
                    if (cx >= rect1x && cx <= rect1x + recX && ((radius + cy == rect1y || Math.abs(rect1y - cy - radius) == 1 || Math.abs(rect1y - cy - radius) == 2 || Math.abs(rect1y - cy - radius) == 3
                            || Math.abs(rect1y - cy - radius) == 4))) {
                        if (cy + radius == rect1y) cy -= 4;
                        else if (Math.abs(rect1y - cy - radius) == 1) {
                            cy -= 3;
                        } else if (Math.abs(rect1y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect1y - cy - radius) == 3) cy--;
                        ind = 1;
                    } else if (cx >= rect2x && cx <= rect2x + recX && ((radius + cy == rect2y || Math.abs(rect2y - cy - radius) == 1 || Math.abs(rect2y - cy - radius) == 2 || Math.abs(rect2y - cy - radius) == 3
                            || Math.abs(rect2y - cy - radius) == 4))) {
                        if (cy + radius == rect2y) cy -= 4;
                        else if (Math.abs(rect2y - cy - radius) == 1) {
                            cy -= 3;
                        } else if (Math.abs(rect2y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect2y - cy - radius) == 3) cy--;
                        ind = 1;
                    } else if (cx >= rect3x && cx <= rect3x + recX && ((radius + cy == rect3y || Math.abs(rect3y - cy - radius) == 1 || Math.abs(rect3y - cy - radius) == 2 || Math.abs(rect3y - cy - radius) == 3
                            || Math.abs(rect3y - cy - radius) == 4))) {
                        if (cy + radius == rect3y) cy -= 4;
                        else if (Math.abs(rect3y - cy - radius) == 1) {
                            cy -= 3;
                        } else if (Math.abs(rect3y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect3y - cy - radius) == 3) cy--;
                        ind = 1;
                    } else if (cx >= rect4x && cx <= rect4x + recX && ((radius + cy == rect4y || Math.abs(rect4y - cy - radius) == 1 || Math.abs(rect4y - cy - radius) == 2 || Math.abs(rect4y - cy - radius) == 3
                            || Math.abs(rect4y - cy - radius) == 4))) {
                        if (cy + radius == rect4y) cy -= 4;
                        else if (Math.abs(rect4y - cy - radius) == 1) {
                            cy -= 3;
                        } else if (Math.abs(rect4y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect4y - cy - radius) == 3) cy--;
                        ind = 1;
                    } else if (cx >= rect5x && cx <= rect5x + recX && ((radius + cy == rect5y || Math.abs(rect5y - cy - radius) == 1 || Math.abs(rect5y - cy - radius) == 2 || Math.abs(rect5y - cy - radius) == 3
                            || Math.abs(rect5y - cy - radius) == 4))) {
                        if (rect5y == cy + radius) cy -= 4;
                        else if (Math.abs(rect5y - cy - radius) == 1) cy -= 3;
                        else if (Math.abs(rect5y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect5y - cy - radius) == 3) cy--;
                        ind = 1;
                    } else if (cx >= rect6x && cx <= rect6x + recX && ((radius + cy == rect6y || Math.abs(rect6y - cy - radius) == 1 || Math.abs(rect6y - cy - radius) == 2 || Math.abs(rect6y - cy - radius) == 3
                            || Math.abs(rect6y - cy - radius) == 4))) {
                        ind = 1;

                        if (rect6y == cy + radius) cy -= 4;
                        else if (Math.abs(rect6y - cy - radius) == 1) cy -= 3;
                        else if (Math.abs(rect6y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect6y - cy - radius) == 3) cy--;
                    } else if (cx >= rect7x && cx <= rect7x + recX && ((radius + cy == rect7y || Math.abs(rect7y - cy - radius) == 1 || Math.abs(rect7y - cy - radius) == 2 || Math.abs(rect7y - cy - radius) == 3
                            || Math.abs(rect7y - cy - radius) == 4))) {
                        ind = 1;
                        if (rect7y == cy + radius) cy -= 4;
                        else if (Math.abs(rect7y - cy - radius) == 1) cy -= 3;
                        else if (Math.abs(rect7y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect7y - cy - radius) == 3) cy--;
                    } else if (cx >= rect8x && cx <= rect8x + recX && ((radius + cy == rect8y || Math.abs(rect8y - cy - radius) == 1 || Math.abs(rect8y - cy - radius) == 2 || Math.abs(rect8y - cy - radius) == 3
                            || Math.abs(rect8y - cy - radius) == 4))) {
                        ind = 1;
                        if (rect8y == cy + radius) cy -= 4;
                        else if (Math.abs(rect8y - cy - radius) == 1) cy -= 3;
                        else if (Math.abs(rect8y - cy - radius) == 2) cy -= 2;
                        else if (Math.abs(rect8y - cy - radius) == 3) cy--;
                    }
                    if (ind != 1) {
                        cy += 4;

                    }
                    ind = 0;

                    if (cx >= des1x && cx <= des1x + recX && ((radius + cy == des1y || Math.abs(radius + cy - des1y) == 1 || Math.abs(radius + cy - des1y) == 2 || Math.abs(radius + cy - des1y) == 3
                            || Math.abs(radius + cy - des1y) == 4))) {
                /*if(Math.abs(des1y - cy - radius) == 0){
                    cy -= 2;
                    des1y -=2;
                }
                else if(Math.abs(des1y - cy - radius) == 1) {
                    cy--;
                    des1y--;
                }
                //else if(des1y == radius + cy) cy -= 2;*/

                        // canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 4;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 4;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 4;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 4;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 4;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 4;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 4;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 4;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des2x && cx <= des2x + recX && ((radius + cy == des2y || radius + cy - des2y == 1 || radius + cy - des2y == 2 || Math.abs(radius + cy - des2y) == 3
                            || Math.abs(radius + cy - des2y) == 4))) {
               /* if(Math.abs(des2y - cy - radius) == 0){
                    cy -= 2;
                    des2y -= 2;
                }
                else if(Math.abs(des2y - cy - radius) == 1) {
                    cy--;
                    des2y--;
                }
                //else if(des2y == radius + cy) cy -= 2;*/
                        //cy += 3;
                        //canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 4;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 4;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 4;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 4;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 4;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 4;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 4;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 4;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des3x && cx <= des3x + recX && ((radius + cy == des3y || radius + cy - des3y == 1 || radius + cy - des3y == 2 || Math.abs(radius + cy - des3y) == 3
                            || Math.abs(radius + cy - des3y) == 4))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 4;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 4;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 4;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 4;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 4;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 4;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 4;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 4;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des4x && cx <= des4x + recX && ((radius + cy == des4y || radius + cy - des4y == 1 || radius + cy - des4y == 2 || Math.abs(radius + cy - des4y) == 3
                            || Math.abs(radius + cy - des4y) == 4))) {
                /*if(Math.abs(des4y - cy - radius) == 0) {
                    cy -= 2;
                    des4y -= 2;
                }
                else if(Math.abs(des4y - cy - radius) == 1){
                    cy--;
                    des4y--;
                }
                //else if(des4y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 4;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 4;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 4;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 4;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 4;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 4;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 4;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 4;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    }

                    rect1y -= 4;
                    rect2y -= 4;
                    rect3y -= 4;
                    rect4y -= 4;
                    rect5y -= 4;
                    rect6y -= 4;
                    rect7y -= 4;
                    rect8y -= 4;
                    des1y -= 4;
                    des2y -= 4;
                    des3y -= 4;
                    des4y -= 4;
                }

                if (level == 4) {
                    if (cx >= rect1x && cx <= rect1x + recX && ((radius + cy == rect1y || Math.abs(rect1y - cy - radius) == 1 || Math.abs(rect1y - cy - radius) == 2 || Math.abs(rect1y - cy - radius) == 3
                            || Math.abs(rect1y - cy - radius) == 4 || Math.abs(rect1y - cy - radius) == 5))) {
                        if (cy + radius == rect1y) cy -= 5;
                        else if (Math.abs(rect1y - cy - radius) == 1) {
                            cy -= 4;
                        } else if (Math.abs(rect1y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect1y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect1y) == 4) cy--;
                        ind = 1;
                    } else if (cx >= rect2x && cx <= rect2x + recX && ((radius + cy == rect2y || Math.abs(rect2y - cy - radius) == 1 || Math.abs(rect2y - cy - radius) == 2 || Math.abs(rect2y - cy - radius) == 3
                            || Math.abs(rect2y - cy - radius) == 4 || Math.abs(rect2y - cy - radius) == 5))) {
                        if (cy + radius == rect2y) cy -= 5;
                        else if (Math.abs(rect2y - cy - radius) == 1) {
                            cy -= 4;
                        } else if (Math.abs(rect2y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect2y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect2y) == 4) cy--;
                        ind = 1;
                    } else if (cx >= rect3x && cx <= rect3x + recX && ((radius + cy == rect3y || Math.abs(rect3y - cy - radius) == 1 || Math.abs(rect3y - cy - radius) == 2 || Math.abs(rect3y - cy - radius) == 3
                            || Math.abs(rect3y - cy - radius) == 4 || Math.abs(rect3y - cy - radius) == 5))) {
                        if (cy + radius == rect3y) cy -= 5;
                        else if (Math.abs(rect3y - cy - radius) == 1) {
                            cy -= 4;
                        } else if (Math.abs(rect3y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect3y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect3y) == 4) cy--;
                        ind = 1;
                    } else if (cx >= rect4x && cx <= rect4x + recX && ((radius + cy == rect4y || Math.abs(rect4y - cy - radius) == 1 || Math.abs(rect4y - cy - radius) == 2 || Math.abs(rect4y - cy - radius) == 3
                            || Math.abs(rect4y - cy - radius) == 4 || Math.abs(rect4y - cy - radius) == 5))) {
                        if (cy + radius == rect4y) cy -= 5;
                        else if (Math.abs(rect4y - cy - radius) == 1) {
                            cy -= 4;
                        } else if (Math.abs(rect4y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect4y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect4y) == 4) cy--;
                        ind = 1;
                    } else if (cx >= rect5x && cx <= rect5x + recX && ((radius + cy == rect5y || Math.abs(rect5y - cy - radius) == 1 || Math.abs(rect5y - cy - radius) == 2 || Math.abs(rect5y - cy - radius) == 3
                            || Math.abs(rect5y - cy - radius) == 4 || Math.abs(rect5y - cy - radius) == 5))) {
                        if (rect5y == cy + radius) cy -= 5;
                        else if (Math.abs(rect5y - cy - radius) == 1) cy -= 4;
                        else if (Math.abs(rect5y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect5y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect5y) == 4) cy--;
                        ind = 1;
                    } else if (cx >= rect6x && cx <= rect6x + recX && ((radius + cy == rect6y || Math.abs(rect6y - cy - radius) == 1 || Math.abs(rect6y - cy - radius) == 2 || Math.abs(rect6y - cy - radius) == 3
                            || Math.abs(rect6y - cy - radius) == 4 || Math.abs(rect6y - cy - radius) == 5))) {
                        ind = 1;

                        if (rect6y == cy + radius) cy -= 5;
                        else if (Math.abs(rect6y - cy - radius) == 1) cy -= 4;
                        else if (Math.abs(rect6y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect6y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect6y) == 4) cy--;
                    } else if (cx >= rect7x && cx <= rect7x + recX && ((radius + cy == rect7y || Math.abs(rect7y - cy - radius) == 1 || Math.abs(rect7y - cy - radius) == 2 || Math.abs(rect7y - cy - radius) == 3
                            || Math.abs(rect7y - cy - radius) == 4 || Math.abs(rect7y - cy - radius) == 5))) {
                        ind = 1;
                        if (rect7y == cy + radius) cy -= 5;
                        else if (Math.abs(rect7y - cy - radius) == 1) cy -= 4;
                        else if (Math.abs(rect7y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect7y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect7y) == 4) cy--;
                    } else if (cx >= rect8x && cx <= rect8x + recX && ((radius + cy == rect8y || Math.abs(rect8y - cy - radius) == 1 || Math.abs(rect8y - cy - radius) == 2 || Math.abs(rect8y - cy - radius) == 3
                            || Math.abs(rect8y - cy - radius) == 4 || Math.abs(rect8y - cy - radius) == 5))) {
                        ind = 1;
                        if (rect8y == cy + radius) cy -= 5;
                        else if (Math.abs(rect8y - cy - radius) == 1) cy -= 4;
                        else if (Math.abs(rect8y - cy - radius) == 2) cy -= 3;
                        else if (Math.abs(rect8y - cy - radius) == 3) cy -= 2;
                        else if (Math.abs(radius + cy - rect8y) == 4) cy--;
                    }
                    if (ind != 1) {
                        cy += 5;

                    }
                    ind = 0;

                    if (cx >= des1x && cx <= des1x + recX && ((radius + cy == des1y || Math.abs(radius + cy - des1y) == 1 || Math.abs(radius + cy - des1y) == 2 || Math.abs(radius + cy - des1y) == 3
                            || Math.abs(radius + cy - des1y) == 4 || Math.abs(radius + cy - des1y) == 5))) {
                /*if(Math.abs(des1y - cy - radius) == 0){
                    cy -= 2;
                    des1y -=2;
                }
                else if(Math.abs(des1y - cy - radius) == 1) {
                    cy--;
                    des1y--;
                }
                //else if(des1y == radius + cy) cy -= 2;*/

                        // canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 5;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 5;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 5;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 5;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 5;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 5;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 5;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 5;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des2x && cx <= des2x + recX && ((radius + cy == des2y || radius + cy - des2y == 1 || radius + cy - des2y == 2 || Math.abs(radius + cy - des2y) == 3
                            || Math.abs(radius + cy - des2y) == 4 || Math.abs(radius + cy - des2y) == 5))) {
               /* if(Math.abs(des2y - cy - radius) == 0){
                    cy -= 2;
                    des2y -= 2;
                }
                else if(Math.abs(des2y - cy - radius) == 1) {
                    cy--;
                    des2y--;
                }
                //else if(des2y == radius + cy) cy -= 2;*/
                        //cy += 3;
                        //canvas.drawCircle(cx, cy, radius, paint);
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 5;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 5;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 5;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 5;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 5;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 5;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 5;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 5;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des3x && cx <= des3x + recX && ((radius + cy == des3y || radius + cy - des3y == 1 || radius + cy - des3y == 2 || Math.abs(radius + cy - des3y) == 3
                            || Math.abs(radius + cy - des3y) == 4 || Math.abs(radius + cy - des3y) == 5))) {
               /* if(Math.abs(des3y - cy - radius) == 0) {
                    cy -= 2;
                    des3y -= 2;
                }
                else if(Math.abs(des3y - cy - radius) == 1){
                    cy--;
                    des3y--;
                }
                //else if(des3y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 5;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 5;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 5;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 5;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 5;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 5;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 5;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 5;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des4x && cx <= des4x + recX && ((radius + cy == des4y || radius + cy - des4y == 1 || radius + cy - des4y == 2 || Math.abs(radius + cy - des4y) == 3
                            || Math.abs(radius + cy - des4y) == 4 || Math.abs(radius + cy - des4y) == 5))) {
                /*if(Math.abs(des4y - cy - radius) == 0) {
                    cy -= 2;
                    des4y -= 2;
                }
                else if(Math.abs(des4y - cy - radius) == 1){
                    cy--;
                    des4y--;
                }
                //else if(des4y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 5;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 5;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 5;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 5;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 5;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 5;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 5;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 5;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    }


                    rect1y -= 5;
                    rect2y -= 5;
                    rect3y -= 5;
                    rect4y -= 5;
                    rect5y -= 5;
                    rect6y -= 5;
                    rect7y -= 5;
                    rect8y -= 5;
                    des1y -= 5;
                    des2y -= 5;
                    des3y -= 5;
                    des4y -= 5;
                }

                if (level == 5) {
                    if (cx >= rect1x && cx <= rect1x + recX && ((radius + cy == rect1y || Math.abs(rect1y - cy - radius) == 1 || Math.abs(rect1y - cy - radius) == 2 || Math.abs(rect1y - cy - radius) == 3
                            || Math.abs(rect1y - cy - radius) == 4 || Math.abs(rect1y - cy - radius) == 5 || Math.abs(rect1y - radius - cy) == 6))) {
                        if (cy + radius == rect1y) cy -= 6;
                        else if (Math.abs(rect1y - cy - radius) == 1) {
                            cy -= 5;
                        } else if (Math.abs(rect1y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect1y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect1y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect1y) == 5) cy -= 1;
                        ind = 1;
                    } else if (cx >= rect2x && cx <= rect2x + recX && ((radius + cy == rect2y || Math.abs(rect2y - cy - radius) == 1 || Math.abs(rect2y - cy - radius) == 2 || Math.abs(rect2y - cy - radius) == 3
                            || Math.abs(rect2y - cy - radius) == 4 || Math.abs(rect2y - cy - radius) == 5 || Math.abs(rect2y - radius - cy) == 6))) {
                        if (cy + radius == rect2y) cy -= 6;
                        else if (Math.abs(rect2y - cy - radius) == 1) {
                            cy -= 5;
                        } else if (Math.abs(rect2y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect2y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect2y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect2y) == 5) cy -= 1;
                        ind = 1;
                    } else if (cx >= rect3x && cx <= rect3x + recX && ((radius + cy == rect3y || Math.abs(rect3y - cy - radius) == 1 || Math.abs(rect3y - cy - radius) == 2 || Math.abs(rect3y - cy - radius) == 3
                            || Math.abs(rect3y - cy - radius) == 4 || Math.abs(rect3y - cy - radius) == 5 || Math.abs(rect3y - radius - cy) == 6))) {
                        if (cy + radius == rect3y) cy -= 6;
                        else if (Math.abs(rect3y - cy - radius) == 1) {
                            cy -= 5;
                        } else if (Math.abs(rect3y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect3y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect3y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect3y) == 5) cy -= 1;
                        ind = 1;
                    } else if (cx >= rect4x && cx <= rect4x + recX && ((radius + cy == rect4y || Math.abs(rect4y - cy - radius) == 1 || Math.abs(rect4y - cy - radius) == 2 || Math.abs(rect4y - cy - radius) == 3
                            || Math.abs(rect4y - cy - radius) == 4 || Math.abs(rect4y - cy - radius) == 5 || Math.abs(rect4y - radius - cy) == 6))) {
                        if (cy + radius == rect4y) cy -= 6;
                        else if (Math.abs(rect4y - cy - radius) == 1) {
                            cy -= 5;
                        } else if (Math.abs(rect4y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect4y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect4y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect4y) == 5) cy -= 1;
                        ind = 1;
                    } else if (cx >= rect5x && cx <= rect5x + recX && ((radius + cy == rect5y || Math.abs(rect5y - cy - radius) == 1 || Math.abs(rect5y - cy - radius) == 2 || Math.abs(rect5y - cy - radius) == 3
                            || Math.abs(rect5y - cy - radius) == 4 || Math.abs(rect5y - cy - radius) == 5 || Math.abs(rect5y - radius - cy) == 6))) {
                        if (rect5y == cy + radius) cy -= 6;
                        else if (Math.abs(rect5y - cy - radius) == 1) cy -= 5;
                        else if (Math.abs(rect5y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect5y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect5y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect5y) == 5) cy -= 1;
                        ind = 1;
                    } else if (cx >= rect6x && cx <= rect6x + recX && ((radius + cy == rect6y || Math.abs(rect6y - cy - radius) == 1 || Math.abs(rect6y - cy - radius) == 2 || Math.abs(rect6y - cy - radius) == 3
                            || Math.abs(rect6y - cy - radius) == 4 || Math.abs(rect6y - cy - radius) == 5 || Math.abs(rect6y - radius - cy) == 6))) {
                        ind = 1;
                        if (rect6y == cy + radius) cy -= 5;
                        else if (Math.abs(rect6y - cy - radius) == 1) cy -= 5;
                        else if (Math.abs(rect6y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect6y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect6y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect6y) == 5) cy -= 1;
                    } else if (cx >= rect7x && cx <= rect7x + recX && ((radius + cy == rect7y || Math.abs(rect7y - cy - radius) == 1 || Math.abs(rect7y - cy - radius) == 2 || Math.abs(rect7y - cy - radius) == 3
                            || Math.abs(rect7y - cy - radius) == 4 || Math.abs(rect7y - cy - radius) == 5 || Math.abs(rect7y - radius - cy) == 6))) {
                        ind = 1;
                        if (rect7y == cy + radius) cy -= 6;
                        else if (Math.abs(rect7y - cy - radius) == 1) cy -= 5;
                        else if (Math.abs(rect7y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect7y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect7y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect7y) == 5) cy -= 1;
                    } else if (cx >= rect8x && cx <= rect8x + recX && ((radius + cy == rect8y || Math.abs(rect8y - cy - radius) == 1 || Math.abs(rect8y - cy - radius) == 2 || Math.abs(rect8y - cy - radius) == 3
                            || Math.abs(rect8y - cy - radius) == 4 || Math.abs(rect8y - cy - radius) == 5 || Math.abs(rect8y - radius - cy) == 6))) {
                        ind = 1;
                        if (rect8y == cy + radius) cy -= 6;
                        else if (Math.abs(rect8y - cy - radius) == 1) cy -= 5;
                        else if (Math.abs(rect8y - cy - radius) == 2) cy -= 4;
                        else if (Math.abs(rect8y - cy - radius) == 3) cy -= 3;
                        else if (Math.abs(radius + cy - rect8y) == 4) cy -= 2;
                        else if (Math.abs(radius + cy - rect8y) == 5) cy -= 1;
                    }
                    if (ind != 1) {
                        cy += 6;

                    }
                    ind = 0;

                    if (cx >= des1x && cx <= des1x + recX && ((radius + cy == des1y || Math.abs(radius + cy - des1y) == 1 || Math.abs(radius + cy - des1y) == 2 || Math.abs(radius + cy - des1y) == 3
                            || Math.abs(radius + cy - des1y) == 4 || Math.abs(radius + cy - des1y) == 5 || Math.abs(radius + cy - des1y) == 6))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 6;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 6;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 6;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 6;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 6;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 6;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 6;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 6;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des2x && cx <= des2x + recX && ((radius + cy == des2y || radius + cy - des2y == 1 || radius + cy - des2y == 2 || Math.abs(radius + cy - des2y) == 3
                            || Math.abs(radius + cy - des2y) == 4 || Math.abs(radius + cy - des2y) == 5 || Math.abs(radius + cy - des2y) == 6))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 6;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 6;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 6;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 6;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 6;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 6;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 6;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 6;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des3x && cx <= des3x + recX && ((radius + cy == des3y || radius + cy - des3y == 1 || radius + cy - des3y == 2 || Math.abs(radius + cy - des3y) == 3
                            || Math.abs(radius + cy - des3y) == 4 || Math.abs(radius + cy - des3y) == 5 || Math.abs(radius + cy - des3y) == 6))) {

                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 6;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 6;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 6;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 6;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 6;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 6;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 6;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 6;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    } else if (cx >= des4x && cx <= des4x + recX && ((radius + cy == des4y || radius + cy - des4y == 1 || radius + cy - des4y == 2 || Math.abs(radius + cy - des4y) == 3
                            || Math.abs(radius + cy - des4y) == 4 || Math.abs(radius + cy - des4y) == 5 || Math.abs(radius + cy - des4y) == 6))) {
                /*if(Math.abs(des4y - cy - radius) == 0) {
                    cy -= 2;
                    des4y -= 2;
                }
                else if(Math.abs(des4y - cy - radius) == 1){
                    cy--;
                    des4y--;
                }
                //else if(des4y == radius + cy) cy -= 2;*/
                        die = 1;
                        if (rect1y >= y / 2) {
                            cx = rect1x + recX / 2;
                            cy = rect1y - radius - 6;
                        } else if (rect5y >= y / 2) {
                            cx = rect5x + recX / 2;
                            cy = rect5y - radius - 6;
                        } else if (rect2y >= y / 2) {
                            cx = rect2x + recX / 2;
                            cy = rect2y - radius - 6;
                        } else if (rect6y >= y / 2) {
                            cx = rect6x + recX / 2;
                            cy = rect6y - radius - 6;
                        } else if (rect3y >= y / 2) {
                            cx = rect3x + recX / 2;
                            cy = rect3y - radius - 6;
                        } else if (rect7y >= y / 2) {
                            cx = rect7x + recX / 2;
                            cy = rect7y - radius - 6;
                        } else if (rect4y >= y / 2) {
                            cx = rect4x + recX / 2;
                            cy = rect4y - radius - 6;
                        } else if (rect8y >= y / 2) {
                            cx = rect8x + recX / 2;
                            cy = rect8y - radius - 6;
                        }
                        if (scLife > 0) scLife--;
                        life.setText("Lives : " + scLife);
                    }


                    rect1y -= 6;
                    rect2y -= 6;
                    rect3y -= 6;
                    rect4y -= 6;
                    rect5y -= 6;
                    rect6y -= 6;
                    rect7y -= 6;
                    rect8y -= 6;
                    des1y -= 6;
                    des2y -= 6;
                    des3y -= 6;
                    des4y -= 6;
                }
                if (scLife == 0) {
                    //life.setText(scLife);
                    editor1 = pref1.edit();
                    editor = pref.edit();
                    editor1.putInt("flag",0);
                    editor1.commit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(GameActivity.this, GameOver.class);
                   // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }


                //  canvas.drawCircle(cx1, cy1, radius1, paint);
                // canvas.drawCircle(cx1, cy1, radius1, paint);

             /*   scCount++;
                if (scCount == 10000000) scCount = 0;
                if (scCount % 10 == 0) sc++;
                score.setText("Score : " + sc);
                if(sc != 0 && scCount % 10000 == 0 && scLife <= 5){
                    scLife++;
                    life.setText("Lives : " + scLife);
                    score.setText("Score : " + sc);
                }
                if (sc == 40) {
                    level = 2;
                    //cy++;
                } else if (sc == 80) level = 3;
                else if (sc == 120) level = 4;
                else if (sc == 160) level = 5;*/

                if (die == 2) {
                    try {
                        //  Toast.makeText(this.getContext(), "Your life has decreased!!",
                        //        Toast.LENGTH_SHORT).show();
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            canvas.drawCircle(cx, cy, radius, paint);

            canvas.drawRect(rect1x, rect1y, rect1x + recX, rect1y + recY, paint);
            canvas.drawRect(rect2x, rect2y, rect2x + recX, rect2y + recY, paint);
            canvas.drawRect(rect3x, rect3y, rect3x + recX, rect3y + recY, paint);
            canvas.drawRect(rect4x, rect4y, rect4x + recX, rect4y + recY, paint);
            canvas.drawRect(rect5x, rect5y, rect5x + recX, rect5y + recY, paint);
            canvas.drawRect(rect6x, rect6y, rect6x + recX, rect6y + recY, paint);
            canvas.drawRect(rect7x, rect7y, rect7x + recX, rect7y + recY, paint);
            canvas.drawRect(rect8x, rect8y, rect8x + recX, rect8y + recY, paint);
            paint.setColor(Color.parseColor("#FFF5112C"));
            canvas.drawRect(des1x, des1y, des1x + recX, des1y + recY, paint);
            canvas.drawRect(des2x, des2y, des2x + recX, des2y + recY, paint);
            canvas.drawRect(des3x, des3y, des3x + recX, des3y + recY, paint);
            canvas.drawRect(des4x, des4y, des4x + recX, des4y + recY, paint);
                invalidate();

        }
    }
    public void func()
    {
        //sc = 0;
        rect1y = y / 4;
        rect2y = y - y / 7;
        rect3y = y / 2;
        rect4y = y - y / 12;
        rect5y = y / 8;
        rect6y = y - y / 16;
        rect7y = y / 5 - 25;
        rect8y = y - y / 4;
        des1y = y - y / 5;
        des2y = y / 6;
        des3y = y - y / 6;
        des4y = y / 32;
        rect1x = rand.nextInt(x / 2);
        rect2x = rand.nextInt(x / 2);
        rect3x = rand.nextInt(x / 2);
        rect4x = rand.nextInt(x / 2);
        des1x = rand.nextInt(x / 2);
        des2x = rand.nextInt(x / 2);
        rect5x = rand.nextInt(x / 2) + x / 2;
        if (rect5x + recX > x) rect5x -= recX;
        rect6x = rand.nextInt(x / 2) + x / 2;
        if (rect6x + recX > x) rect6x -= recX;
        rect7x = rand.nextInt(x / 2) + x / 2;
        if (rect7x + recX > x) rect7x -= recX;
        rect8x = rand.nextInt(x / 2) + x / 2;
        if (rect8x + recX > x) rect8x -= recX;
        des3x = rand.nextInt(x / 2) + x / 2;
        if (des3x + recX > x) des3x -= recX;
        des4x = rand.nextInt(x / 2) + x / 2;
        if (des4x + recX > x) des4x -= recX;
        cx = rect8x + recX / 2;
        cy = rect8y - radius;
        life.setText("Lives : " + scLife);
        score.setText("Score : " + sc);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (button % 2 == 0) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                if (cx + radius > x) {
                    cx -= 4;
                } else if (cx - radius <= 0) {
                    cx += 4;
                } else if (cx + radius <= x && die != 2) {
                    cx -= (int) event.values[0];

                }
            }
        }
    }
    public void onBackPressed() {
       // GameActivity.sc = 0;
        editor1 = pref1.edit();
        editor = pref.edit();
        editor1.putInt("flag",1);
        editor1.commit();
        editor.putInt("rect1x",rect1x);
        editor.putInt("rect2x",rect2x);
        editor.putInt("rect3x",rect3x);
        editor.putInt("rect4x",rect4x);
        editor.putInt("rect5x",rect5x);
        editor.putInt("rect6x",rect6x);
        editor.putInt("rect7x",rect7x);
        editor.putInt("rect8x",rect8x);
        editor.putInt("rect1y",rect1y);
        editor.putInt("rect2y",rect2y);
        editor.putInt("rect3y",rect3y);
        editor.putInt("rect4y",rect4y);
        editor.putInt("rect5y",rect5y);
        editor.putInt("rect6y",rect6y);
        editor.putInt("rect7y",rect7y);
        editor.putInt("rect8y",rect8y);
        editor.putInt("des1x",des1x);
        editor.putInt("des2x",des2x);
        editor.putInt("des3x",des3x);
        editor.putInt("des4x",des4x);
        editor.putInt("des1y",des1y);
        editor.putInt("des2y",des2y);
        editor.putInt("des3y",des3y);
        editor.putInt("des4y",des4y);
        editor.putInt("cx",cx);
        editor.putInt("cy",cy);
        editor.putInt("sc",sc);
        editor.putInt("scCount",scCount);
        editor.putInt("scLife",scLife);
        editor.commit();
        Intent intent = new Intent(GameActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        return;
    }
}