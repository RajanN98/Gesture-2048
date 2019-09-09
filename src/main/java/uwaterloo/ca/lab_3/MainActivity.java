package uwaterloo.ca.lab_3;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;

import uwaterloo.ca.lab_3.Game.GameLoop;

/**
 * Handles UI Creation and Updates UI based on AppCompatActivity.
 *
 * @author  Rajan Nijjar
 * @version 1.0
 * @since Feb 16, 2017
 */
public class MainActivity extends AppCompatActivity {
    RelativeLayout l;

    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initializations */
        l = (RelativeLayout) findViewById(R.id.activity_main);
        l.getLayoutParams().width = 1350;
        l.getLayoutParams().height = 1350;

        l.setBackgroundResource(R.drawable.gameboard);

        tvOutput = (TextView) findViewById(R.id.tvDirection);

        /* Textview */
        tvOutput.setText("NULL");

        Timer myGameLoop = new Timer();
        GameLoop gl = new GameLoop(this, getApplicationContext(), l);

        myGameLoop.schedule(gl, 35, 35);

        // Sensor Service
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Access different type of sensors.
        Sensor sLA = sm.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Instance of handlers, passed by reference.
       final LinearAccelerationSensorHandler shLA = new LinearAccelerationSensorHandler(tvOutput, gl);

        // Register handle to the corresponding sensor event.
        sm.registerListener(shLA, sLA, SensorManager.SENSOR_DELAY_GAME);
    }
}
