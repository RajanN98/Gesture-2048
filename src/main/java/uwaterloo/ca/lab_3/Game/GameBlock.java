package uwaterloo.ca.lab_3.Game;


import android.content.Context;
import android.widget.ImageView;

import uwaterloo.ca.lab_3.R;

/**
 * Created by Rajan Nijjar on 3/7/2017.
 */

public class GameBlock extends ImageView {
    private final float GB_ACC = 6.0f;
    private final float IMAGE_SCALE = 0.55f;
    private int myCoordX;
    private int myCoordY;
    private int targetCoordX;
    private int targetCoordY;

    private int myVelocity;
    private GameLoop.Direction targetDirection;


    public GameBlock(Context c, int coordX, int coordY){
        super(c);
        this.setImageResource(R.drawable.gameblock);
        this.setX(coordX);
        this.setY(coordY);
        this.setScaleX(IMAGE_SCALE);
        this.setScaleY(IMAGE_SCALE);
        myVelocity=0;

        myCoordX=coordX;
        myCoordY=coordY;
        targetCoordX=myCoordX;
        targetCoordX=myCoordX;
        myVelocity=0;

        targetDirection = GameLoop.Direction.NO_MOVEMENT;

    }

    public int[] getCoordinate(){
        int[] thisCoord = new int[2];
        thisCoord[0] = myCoordX;
        thisCoord[1] = myCoordY;
        return thisCoord;
    }


    public void setDirection(GameLoop.Direction thisDir) {
        targetDirection = thisDir;
    }

    public void move(){

        switch(targetDirection){

            case LEFT:

                targetCoordX = GameLoop.LEFT_BOUNDARY;

                if(myCoordX > targetCoordX){
                    if((myCoordX - myVelocity) <= targetCoordX){
                        myCoordX = targetCoordX;
                        myVelocity = 0;
                    }
                    else {
                        myCoordX -= myVelocity;
                        myVelocity += GB_ACC;
                    }
                }

                break;

            case RIGHT:

                targetCoordX = GameLoop.RIGHT_BOUNDARY;

                if(myCoordX < targetCoordX){
                    if((myCoordX + myVelocity) >= targetCoordX){
                        myCoordX = targetCoordX;
                        myVelocity = 0;
                    }
                    else {
                        myCoordX += myVelocity;
                        myVelocity += GB_ACC;
                    }
                }

                break;

            case UP:

                targetCoordY = GameLoop.UP_BOUNDARY;

                if(myCoordY > targetCoordY){
                    if((myCoordY - myVelocity) <= targetCoordY){
                        myCoordY = targetCoordY;
                        myVelocity = 0;
                    }
                    else {
                        myCoordY -= myVelocity;
                        myVelocity += GB_ACC;
                    }
                }

                break;

            case DOWN:

                targetCoordY = GameLoop.DOWN_BOUNDARY;

                if(myCoordY < targetCoordY){
                    if((myCoordY + myVelocity) >= targetCoordY){
                        myCoordY = targetCoordY;
                        myVelocity = 0;
                    }
                    else {
                        myCoordY += myVelocity;
                        myVelocity += GB_ACC;
                    }
                }

                break;

            default:
                break;

        }

        this.setX(myCoordX);
        this.setY(myCoordY);

        if(myVelocity == 0) {
            targetDirection = GameLoop.Direction.NO_MOVEMENT;
        }

    }
}
