package uwaterloo.ca.lab_3.Game;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.RelativeLayout;

import java.util.LinkedList;
import java.util.Random;
import java.util.TimerTask;

/**
 * Created by Rajan Nijjar on 3/7/2017.
 */

public class GameLoop extends TimerTask {

    private Activity activity;
    private Context c;
    private RelativeLayout rl;
    private GameBlock block;
    public static final int RIGHT_BOUNDARY = 950;
    public static final int LEFT_BOUNDARY = -55;
    public static final int UP_BOUNDARY = -50;
    public static final int DOWN_BOUNDARY = 955;

    public LinkedList<GameBlock> myGBList;
    private Random myRandomGen;

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        NO_MOVEMENT
    }

    public Direction direction = Direction.NO_MOVEMENT;

    public GameLoop(Activity activity, Context c, RelativeLayout rl) {
        this.activity = activity;
        this.c = c;
        this.rl = rl;

        createBlock();
    }

    public void setDirection(Direction target) {
        if (direction != target) {
            direction = target;
            block.setDirection(target);
        }
        createBlock();
    }

    private void createBlock() {
        myRandomGen= new Random();

        int[] coords = { myRandomGen.nextInt(4) + LEFT_BOUNDARY,
                myRandomGen.nextInt(4)+ UP_BOUNDARY };

        block = new GameBlock(c, coords[0], coords[1]);
        rl.addView(block);
        block.bringToFront();
    }

    public GameBlock isOccupied(int coordX, int coordY){

        int[] checkCoord = new int[2];

        for(GameBlock gb : myGBList){
            checkCoord = block.getCoordinate();
            if(checkCoord[0] == coordX && checkCoord[1] == coordY){
                Log.d("Game Loop Report: ", "Occupant Found!");
                return gb;
            }
        }

        return null;

    }



    @Override
    public void run() {
        activity.runOnUiThread (

                new Runnable() {
                    public void run() {
                        for(GameBlock gb : myGBList){
                            gb.move();}
                    }
                }
        );

    }

    private void completeTask() {

    }



}
