package edu.northsouth.marsnavigator;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import java.text.DecimalFormat;


public class SensorReadingActivity extends WearableActivity {

    DecimalFormat twoDForm = new DecimalFormat("#.##");
    private TextView TextView1;private TextView TextView2;private TextView TextView3;
    private SensorManager sensorManager;
    private Sensor gyro ;
    private SensorEventListener gyroscopeSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            float[] g = new float[3];
            g = sensorEvent.values.clone();

            float norm_Of_g = (float) Math.sqrt(g[0] * g[0] + g[1] * g[1] + g[2] * g[2]);

            // Normalize the accelerometer vector
            g[0] = g[0] / norm_Of_g;
            g[1] = g[1] / norm_Of_g;
            g[2] = g[2] / norm_Of_g;

            int angle1 = (int) Math.round(Math.toDegrees(Math.asin(g[0])));
            int angle2 = (int) Math.round(Math.toDegrees(Math.asin(g[1])));
            int angle3 = (int) Math.round(Math.toDegrees(Math.acos(g[2])));

            TextView1.setText(String.valueOf(angle1));
            TextView2.setText(String.valueOf(angle2));
            TextView3.setText(String.valueOf(angle3));

            //TextView3.setText(String.valueOf(twoDForm.format(sensorEvent.values[2])));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_reading);

        TextView1 = (TextView) findViewById(R.id.sensorValue1);
        TextView2 = (TextView) findViewById(R.id.sensorValue2);
        TextView3 = (TextView) findViewById(R.id.sensorValue3);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gyro= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Enables Always-on
        setAmbientEnabled();
        // Register the listener
        sensorManager.registerListener(gyroscopeSensorListener,
                gyro, SensorManager.SENSOR_DELAY_NORMAL);

    }


}
