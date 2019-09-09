package uwaterloo.ca.lab_3;

import android.util.Log;

import uwaterloo.ca.lab_3.Game.GameLoop;

/**
 * Handles Implementation of a Finite State Machine to create 4 types of actions.
 *
 * @author  Rajan Nijjar
 * @version 1.2
 * @since Feb 16, 2017
 */

public class FiniteStateMachine {
    // Filter Constant
    public final float FILTER_CONSTANT = 12.0f;

    // Threshold Constants
    private final float[] THRES_A = { 1.0f, 1.3f, -0.5f };
    private final float[] THRES_B = { -1.0f, -1.3f, 0.5f };

    // FSM Counter
    private final int iDEFAULT = 30;
    int iCounter = iDEFAULT;

    // String to represent Direction from Finite State Machine Analysis.
    String sDirection = "Undetermined";

    // FSM States
    public enum fsm_states {
        WAIT,
        RISE_A,
        FALL_A,
        RISE_B,
        FALL_B,
        RISE_C,
        FALL_C,
        RISE_D,
        FALL_D,
        DETERMINED
    };

    // Signature States
    public enum sig_states {
        SIG_A,
        SIG_B,
        SIG_C,
        SIG_D,
        SIG_X
    };

    /* Initialize States */
    public fsm_states fsmState = fsm_states.WAIT;
    public sig_states sigState = sig_states.SIG_X;

    /**
     * This method analyzes the 2D array received from the Linear Acceleration Sensor.
     * After analyzing, it returns a new sigState and fsmState.
     * @param arr This is an array containing Linear Acceleration Sensor data.
     */
    public void Direction(float[][] arr) {
        // Get difference between sensor data change.
        float fUpDown = arr[99][0] - arr[98][0];
        float fLeftRight = arr[99][2] - arr[98][2];

        switch (fsmState) {
            case WAIT:
                iCounter = iDEFAULT;

                /* SIG_X -> Unknown Action */
                sigState = sig_states.SIG_X;

                if(fUpDown > THRES_A[0]) {
                    fsmState = fsm_states.RISE_A;
                }
                else if(fUpDown < THRES_B[0]){
                    fsmState = fsm_states.FALL_B;
                }
                else if (fLeftRight > THRES_A[0]) {
                    fsmState = fsm_states.RISE_C;
                }
                else if (fLeftRight < THRES_B[0]) {
                    fsmState = fsm_states.FALL_D;
                }
                break;

            case RISE_A:
                if(fUpDown <= 0){
                    if(arr[99][0] >= THRES_A[1]){
                        fsmState = fsm_states.FALL_A;
                    }
                    else{
                        fsmState = fsm_states.DETERMINED;
                    }
                }
                break;

            case FALL_A:
                if(fUpDown >= 0){
                    if (arr[99][0] <= THRES_A[2]) {
                        /* SIG_A -> RIGHT Action */
                        sigState = sig_states.SIG_A;
                    }
                    fsmState = fsm_states.DETERMINED;
                }
                break;

            case RISE_B:
                if(fUpDown <= 0){
                    if (arr[99][0] >= THRES_B[2]) {
                        /* SIG_B -> LEFT Action */
                        sigState = sig_states.SIG_B;
                    }
                    fsmState = fsm_states.DETERMINED;
                }
                break;

            case FALL_B:
                if(fUpDown >= 0){
                    if(arr[99][0] <= THRES_B[1]){
                        fsmState = fsm_states.RISE_B;
                    }
                    else{
                        fsmState = fsm_states.DETERMINED;
                    }
                }
                break;

            case RISE_C:
                if (fLeftRight <= 0) {
                    if (arr[99][2] >= THRES_A[1]) {
                        fsmState = fsm_states.FALL_C;
                    }
                    else {
                        fsmState = fsm_states.DETERMINED;
                    }
                }
                break;

            case FALL_C:
                if (fLeftRight >= 0) {
                    if (arr[99][2] <= THRES_A[2]) {
                        /* SIG_C -> UP Action */
                        sigState = sig_states.SIG_C;
                    }
                    fsmState = fsm_states.DETERMINED;
                }
                break;

            case RISE_D:
                if (fLeftRight <= 0) {
                    if (arr[99][2] >= THRES_B[2]) {
                        /* SIG_D -> DOWN Action */
                        sigState = sig_states.SIG_D;
                    }
                    fsmState = fsm_states.DETERMINED;
                }
                break;

            case FALL_D:
                if (fLeftRight >= 0) {
                    if (arr[99][2] <= THRES_B[1]) {
                        fsmState = fsm_states.RISE_D;
                    }
                    else {
                        fsmState = fsm_states.DETERMINED;
                    }
                }
                break;

            case DETERMINED:
                Log.d("FSM: ", "fsmState DETERMINED " + sigState.toString());
                break;

            default:
                fsmState = fsm_states.WAIT;
                break;

        }
        iCounter--;
    }

    /**
     * @return a string showcasing direction based on signature states.
     */
    public String sResult(GameLoop gl) {
        if (iCounter <= 0) {
            Log.d("kms", "counter is less than 0");
            iCounter = iDEFAULT;
            if(fsmState == fsm_states.DETERMINED) {
                fsmState = fsm_states.WAIT;

                if(sigState == sig_states.SIG_B) {
                    gl.setDirection(GameLoop.Direction.LEFT);
                    sDirection = "LEFT";
                }
                else if(sigState == sig_states.SIG_A) {
                    gl.setDirection(GameLoop.Direction.RIGHT);
                    sDirection = "RIGHT";
                }
                else if (sigState == sig_states.SIG_C) {
                    gl.setDirection(GameLoop.Direction.UP);
                    sDirection = "UP";
                }
                else if (sigState == sig_states.SIG_D) {
                    gl.setDirection(GameLoop.Direction.DOWN);
                    sDirection = "DOWN";
                }
            }
            else {
                fsmState = fsm_states.WAIT;
                sDirection = "Undetermined";
            }
        }
        return sDirection;
    }
}
