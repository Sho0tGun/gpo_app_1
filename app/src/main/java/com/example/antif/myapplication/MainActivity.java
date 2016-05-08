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


public class MainActivity extends Activity implements SensorEventListener
{
    private SensorManager mSensorManager;
    private float[] angle;
    public int x,y,r,speed,start_r;
    int max_x,max_y,max_r;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        max_x = metrics.widthPixels;
        max_y = metrics.heightPixels;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Получаем менеджер сенсоров
        setContentView(R.layout.activity_main);
        setContentView(new DrawView(this));
        x=max_x/2;
        y=max_y/2;
        speed=2;
        start_r=50;
        max_r=150;
        r=start_r;

    }
    public class DrawView extends View
    {

        Paint p;
        Rect rect;

        public  DrawView(Context context)
        {
            super(context);
            p = new Paint();
            rect = new Rect();
        }



        @Override
        public void onDraw(Canvas canvas)
        {
            // заливка канвы цветом
            canvas.drawARGB(60, 0, 255, 255);
            // настройка кисти
            // красный цвет
            p.setColor(Color.BLACK);
            canvas.drawLine(max_r-start_r,max_r-start_r,max_r-start_r,max_y-max_r+start_r,p);//left_vert
            canvas.drawLine(max_x-max_r+start_r,max_r-start_r,max_x-max_r+start_r,max_y-max_r+start_r,p);//right_vert
            canvas.drawLine(max_r-start_r,max_r-start_r,max_x-max_r+start_r,max_r-start_r,p);//verh_gor
            canvas.drawLine(max_r-start_r,max_y-max_r+start_r,max_x-max_r+start_r,max_y-max_r+start_r,p);//niz_gor
            canvas.drawLine(0,0,max_r-start_r,max_r-start_r,p);//left_verx
            canvas.drawLine(max_x,0,max_x-max_r+start_r,max_r-start_r,p);//
            canvas.drawLine(0,max_y,max_r-start_r,max_y-max_r+start_r,p);
            canvas.drawLine(max_x,max_y,max_x-max_r+start_r,max_y-max_r+start_r,p);
            p.setColor(Color.RED);
            // толщина линии = 10
            // рисуем круг с центром в (100,200), радиус = 50
            canvas.drawCircle(x, y, r, p);
            invalidate();
        }


    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            angle = event.values.clone();
            draw();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void draw()
    {

        if(x>=r && x<=(max_x-r)&& y>=r && y<=(max_y-r)) {
            if (angle[2] <= (-1) && angle[2] >= (-2) && r < max_r) r += 1;
            if (angle[2] < (-2) && angle[2] >= (-3) && r < max_r) r += 2;
            if (angle[2] < (-3) && angle[2] >= (-4) && r < max_r) r += 3;
            if (angle[2] < (-4) && angle[2] >= (-5) && r < max_r) r += 5;
            if (angle[2] < (-5) && r < max_r) r += 6;
            if (angle[2] >= 0 && angle[2] <= 1 && r > start_r) r -= 1;
            if (angle[2] > 1 && angle[2] <= 2 && r < start_r) r -= 2;
            if (angle[2] > 2 && angle[2] <= 3 && r < start_r) r -= 3;
            if (angle[2] > 3 && angle[2] <= 4 && r < start_r) r -= 5;
            if (angle[2] > 4 && r > start_r) r -= 6;
            if (r < start_r) r = start_r;
            if (r > max_r) r = max_r;
            x = x + (int) ((-1) * angle[0] * speed);
            y = y + (int) (angle[1] * speed);
            if (x < max_r) x = max_r;
            if (y < max_r) y = max_r;
            if (x > (max_x - max_r)) x = max_x - max_r;
            if (y > (max_y - max_r)) y = max_y - max_r;
        }
    }
}
