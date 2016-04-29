package com.example.antif.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;


public class MainActivity extends Activity implements SensorEventListener {
    private SensorManager mSensorManager;
    private float[] angle;
    public int x,y,r,s;
    int max_x,max_y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        max_x = metrics.widthPixels;
        max_y = metrics.heightPixels;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Получаем менеджер сенсоров
        setContentView(R.layout.activity_main);
        setContentView(new DrawView(this));
        x=max_x/4;
        y=max_y/4;
        s=2;r=50;

    }
    public class DrawView extends View {

        Paint p;
        Rect rect;

        public  DrawView(Context context) {
            super(context);
            p = new Paint();
            rect = new Rect();
        }



        @Override
        public void onDraw(Canvas canvas) {
            // заливка канвы цветом
            canvas.drawARGB(60, 0, 255, 255);
            // настройка кисти
            // красный цвет
            p.setColor(Color.RED);
            // толщина линии = 10
            // рисуем круг с центром в (100,200), радиус = 50

            canvas.drawCircle(x, y, r, p);
            invalidate();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            angle = event.values.clone();
            draw();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void draw() {

        if(x>=r && x<=(max_x-r)&& y>=r && y<=(max_y-r))
        {
            x = x+(int) ((-1)*angle[0]*s);
            y = y+(int) (angle[1]*s);
            if(x<r) x=r;
            if(y<r) y=r;
            if(x>(max_x-r)) x=max_x-r;
            if(y>(max_y-r)) y=max_y-r;
        }
    }
}
