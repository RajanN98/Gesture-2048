package uwaterloo.ca.lab_3.Game;


import android.content.Context;
import android.widget.ImageView;

/**
 * Created by keith on 2017-03-07.
 */

public abstract class GameBlockTemplate extends ImageView{

    public GameBlockTemplate(Context gbCTX){
        super(gbCTX);
    }

    public abstract void setDestination(GameLoop.Direction myDir);

    public abstract void move();

}
