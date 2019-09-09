package uwaterloo.ca.lab_3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

import uwaterloo.ca.lab_3.Game.GameLoop;

/**
 * Handles Implementation of a Linear Acceleration Sensor.
 *
 * @author  Rajan Nijjar
 * @version 1.0
 * @since Feb 16, 2017
 */
class LinearAccelerationSensorHandler implements SensorEventListener {
    TextView tvRead;

    private int iCount = 0;
    private float[][] fCSV = new float[100][3];

    FiniteStateMachine FSM = new FiniteStateMachine();

    GameLoop gl;

    /* Constructor */
    public LinearAccelerationSensorHandler(TextView read, GameLoop gl) {
        this.tvRead = read;
        this.gl = gl;
    }

    // Required by the SensorEventListener interface...
    public void onAccuracyChanged(Sensor s, int i) {
        // TODO...
    }

    // Returns float 2-D array containing values for CSV creation.
    public float[][] RecieveCSV() {
        return fCSV;
    }

    /**
     * FIFO 100-element rotation method
     * @param arr This is the raw sensor data.
     */
    public void InsertCSV(float[] arr) {
        for (int i = 1; i < 100; i++) {
            fCSV[i - 1][0] = fCSV[i][0];
            fCSV[i - 1][1] = fCSV[i][1];
            fCSV[i - 1][2] = fCSV[i][2];
        }

        // Apply Low-Pass Filter (LPF).
        fCSV[99][0] += (arr[0] - fCSV[99][0]) / FSM.FILTER_CONSTANT;
        fCSV[99][1] += (arr[1] - fCSV[99][1]) / FSM.FILTER_CONSTANT;
        fCSV[99][2] += (arr[2] - fCSV[99][2]) / FSM.FILTER_CONSTANT;

        // Signature Analysis.
        FSM.Direction(fCSV);

        // Sets text based direction result.
        tvRead.setText(FSM.sResult(gl));
    }

    // Required by the SensorEventListener interface...
    public void onSensorChanged(SensorEvent se) {
        // Double-check if the sensor event is intended for this handler.
        if (se.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            InsertCSV(se.values);
        }
    }
}
