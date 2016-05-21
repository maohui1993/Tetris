package org.oucho.tetris;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;



public class GameBoard extends View {

    //Drawables for the board boxes, the playable zone
    private final Drawable[][] block = new Drawable[20][10];

    //Drawables for the wall (yes, it's done with tiles)
    private final Drawable[] wall = new Drawable[102];

    //Drawable for the background, and boolean for drawing it or not
    //private Drawable mbg;
    //private boolean bg = false;
    //Context and canvas to be used along the class
    private final Context context;


    public GameBoard(Context cont, AttributeSet attrs) {
        super(cont, attrs);
        context = cont;
    }

    /*************************************************
	* Initializes drawables for playable boxes ******
    *************************************************
	* Must be initialized one by one from the Game
	* activity, passing all the parameters

    *************************************************/
    public void initialize(int i, int j, int left, int top, int side) {
        block[i][j] = ContextCompat.getDrawable(context, R.color.alpha);
        block[i][j].setBounds(left, top, left + side, top + side);
    }

    /*************************************************
	* Draws the board wall
    *************************************************
	* Needs the top-left point of the board frame
	* and the width of the wall

    *************************************************/
    public void createWall(int left, int top, int side) {
        int i = 0, x, y;

        x = left - side / 2;
        y = top;

        // gauche
        while (i < 40) {
            wall[i] = ContextCompat.getDrawable(context, R.drawable.brick);
            wall[i].setBounds(x, y, x + side / 2, y + side / 2);
            y = y + side / 2;
            i = i + 1;
        }

        x = left + side * 10;
        y = top;


        // droite
        while (i < 80) {
            wall[i] = ContextCompat.getDrawable(context, R.drawable.brick);
            wall[i].setBounds(x, y, x + side / 2, y + side / 2);
            y = y + side / 2;
            i = i + 1;
        }

        x = left - side / 2;

        // bas
        while (i < 102) {
            wall[i] = ContextCompat.getDrawable(context, R.drawable.brick);
            wall[i].setBounds(x, y, x + side / 2, y + side / 2);
            x = x + side / 2;
            i = i + 1;
        }
    }


   /* ************************************************
	* Draws the board
    * ************************************************/

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        try {

            for (int i = 0; i < 102; i++)
                wall[i].draw(canvas);



            for (int i = 0; i < 20; i++)
                for (int j = 0; j < 10; j++) {
                    block[i][j].draw(canvas);
                }

            //Actually draw
            invalidate();

        } catch (Exception ignored) {}
    }



    public void setColor(int i, int j, byte c) {
        Rect rect;
        rect = block[i][j].getBounds();
        switch (c) {

            case Values.COLOR_NONE:
                block[i][j] = ContextCompat.getDrawable(context, R.color.alpha);
                break;
            case Values.COLOR_CYAN:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_cyan1);
                break;
            case Values.COLOR_BLUE:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_blue1);
                break;
            case Values.COLOR_ORANGE:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_orange1);
                break;
            case Values.COLOR_YELLOW:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_yellow1);
                break;
            case Values.COLOR_GREEN:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_green1);
                break;
            case Values.COLOR_RED:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_red1);
                break;
            case Values.COLOR_PURPLE:
                block[i][j] = ContextCompat.getDrawable(context, R.drawable.block_purple1);
                break;

        }
        block[i][j].setBounds(rect);
    }
}
