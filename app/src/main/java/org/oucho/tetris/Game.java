package org.oucho.tetris;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


public class Game extends AppCompatActivity
		implements OnClickListener {

	private Box[][] box;

	private Pieces nextPiece = new Pieces();
	private Pieces currentPiece = new Pieces();


	private CountDownTimer timer;

	private GameBoard gameBoard;

	private MediaPlayer soundMove;
	private MediaPlayer soundLine;
	private MediaPlayer soundDown;
	private MediaPlayer soundRotate;

	private TextView niveau;
	private TextView btnPause;
	private TextView nbLignes;
	private TextView textScore;

	private ImageView pièce0;
	private ImageView pièce1;
	private ImageView pièce2;
	private ImageView pièce3;
	private ImageView pièce4;
	private ImageView pièce5;
	private ImageView pièce6;
	private ImageView button0;

	private int score = 0;
	private int combo = 1;
	private int ligne = 0;

	private final int[] bouton_ID = {
			R.id.buttonPause,
			R.id.ButtonMoveR,
			R.id.ButtonMoveL,
			R.id.ButtonMoveD,
			R.id.buttonRotateR,
			R.id.ButtonRotateL
	};

	private boolean game;

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game);


		btnPause = (TextView) findViewById(R.id.buttonPause);

		gameBoard = (GameBoard) findViewById(R.id.GameView);

		button0 = (ImageView) findViewById(R.id.ButtonMoveD);


		pièce0 = (ImageView) findViewById(R.id.pièce0);
		pièce1 = (ImageView) findViewById(R.id.pièce1);
		pièce2 = (ImageView) findViewById(R.id.pièce2);
		pièce3 = (ImageView) findViewById(R.id.pièce3);
		pièce4 = (ImageView) findViewById(R.id.pièce4);
		pièce5 = (ImageView) findViewById(R.id.pièce5);
		pièce6 = (ImageView) findViewById(R.id.pièce6);

		niveau = (TextView) findViewById(R.id.niveau);
		nbLignes = (TextView) findViewById(R.id.lignes);
		textScore = (TextView) findViewById(R.id.TextViewScore);


		for(int ID : bouton_ID)
			this.findViewById(ID).setOnClickListener(this);


		game = true;

		soundDown = MediaPlayer.create(this, R.raw.down);
        soundLine = MediaPlayer.create(this, R.raw.line);
        soundMove = MediaPlayer.create(this, R.raw.move);
        soundRotate = MediaPlayer.create(this, R.raw.rotate);

		textScore.setText("0");
		niveau.setText("0");


		board();
		setImgs();
		timer(1000);

		currentPiece.start();
	}



	/* **********************************************************************************************
    * Pause, resume etc.
    * *********************************************************************************************/
	@Override
	protected void onPause() {
		super.onPause();
		game = false;
		btnPause.setText(R.string.resume);
	}

	@Override
	protected void onStop() {
		super.onStop();
		game = false;
	}


	private void board() {


		//Get measures for the board
		Point size = new Point();

		this.getWindowManager().getDefaultDisplay().getSize(size);

		int width = (int) (size.x * 0.73);
		int height = (int) (size.y * 0.83);

		int d = (int) (width * 0.85 / 10);

		//Initialize boxes and draw the wall
		box = new Box[20][10];

		int x = (int) (width * 0.05);
		int y = (int) (height * 0.05);

		gameBoard.createWall(x, y, d);


		for (int i = 0; i < 20; i++){

			x = (int) (width * 0.05);

			for (int j = 0; j < 10; j++){

				box[i][j] = new Box();
				gameBoard.initialize(i, j, x, y, d);
				x = x + d;
			}

			y = y + d;
		}
	}



   /* **********************************************************************************************
    * Gestion des clicks sur l'interface
    * *********************************************************************************************/

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.ButtonMoveR:

				if (game) {
					soundMove.start();

					unDraw();
					currentPiece.moveRight();
					reDraw();
				}

				break;

			case R.id.ButtonMoveL:

				if (game) {

					soundMove.start();

					unDraw();
					currentPiece.moveLeft();
					reDraw();
				}

				break;

			case R.id.ButtonMoveD:

				if (game) {

					soundMove.start();

					unDraw();
					currentPiece.moveDown();
					reDraw();

					// long press
					down();
				}

				break;

			case R.id.buttonRotateR:

				if (game) {

					soundRotate.start();
					unDraw();
					currentPiece.rotateRight();
					reDraw();
				}

				break;

			case R.id.ButtonRotateL:

				if (game) {

					soundRotate.start();
					unDraw();
					currentPiece.rotateLeft();
					reDraw();
				}

				break;

			case R.id.buttonPause:

				if (game) {

					game = false;
					btnPause.setText(R.string.resume);

				} else {

					game = true;
					btnPause.setText(R.string.pause);
				}

				break;

			default:
				break;
		}
	}


	private void down() {

		button0.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;

			@Override public boolean onTouch(View v, MotionEvent event) {

				switch(event.getAction()) {

					case MotionEvent.ACTION_DOWN:
						if (mHandler != null) return true;
						mHandler = new Handler();
						mHandler.postDelayed(mAction, 500);
						break;

					case MotionEvent.ACTION_UP:
						if (mHandler == null) return true;
						mHandler.removeCallbacks(mAction);
						mHandler = null;
						break;

				}
				return false;
			}

			final Runnable mAction = new Runnable() {
				@Override public void run() {

					unDraw();
					currentPiece.moveDown();
					reDraw();

					score = score + 1;

					textScore.setText(Integer.toString(score));

					mHandler.postDelayed(this, 20);
				}
			};
		});
	}



	/* ************************************************
	 * Main time bucle
	 * ************************************************/

	private void gameAction(){
		if (game){

			unDraw();

			//Try to move it down.
			if (!currentPiece.moveDown()){

				soundDown.start();
				//If couldnt move the piece down, the boxes occupied by it become ocuupied boxes
				for (int i = 0; i < 20; i++)
					for (int j = 0; j < 10; j++){
						if (currentPiece.box[i][j]){
							box[i][j].setColor(currentPiece.getColor());
							gameBoard.setColor(i, j, currentPiece.getColor());
						}
					}
				/// ...check if there is any full row...
				if (!lookForRows()){
					combo = 1; //If nothing has been removed, set combo to 1

				}
				//... check if the game is loose... 
				checkGameLoose();
				// ... and start a new piece
				currentPiece = nextPiece;
				currentPiece.start();

				nextPiece = new Pieces();

				//Set the next piece image
				setImgs();

			}

			//Copy the board info to the piece
			currentPiece.readBoard(box);
			reDraw();
		}


	}


	private void setImgs() {
		//Set image for next piece
		//Has to be done here, or there is no next piece image at the beggining
		switch(nextPiece.type){
			case Values.PIECE_0:

				pièce0.setVisibility(View.VISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.INVISIBLE);


				//nextPieceImg.setImageResource(R.drawable.piece0);

				break;
			case Values.PIECE_1:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.VISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.INVISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece1);

				break;
			case Values.PIECE_2:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.VISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.INVISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece2);

				break;
			case Values.PIECE_3:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.VISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.INVISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece3);

				break;
			case Values.PIECE_4:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.VISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.INVISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece4);

				break;
			case Values.PIECE_5:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.VISIBLE);
				pièce6.setVisibility(View.INVISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece5);

				break;
			case Values.PIECE_6:
				pièce0.setVisibility(View.INVISIBLE);
				pièce1.setVisibility(View.INVISIBLE);
				pièce2.setVisibility(View.INVISIBLE);
				pièce3.setVisibility(View.INVISIBLE);
				pièce4.setVisibility(View.INVISIBLE);
				pièce5.setVisibility(View.INVISIBLE);
				pièce6.setVisibility(View.VISIBLE);
				//nextPieceImg.setImageResource(R.drawable.piece6);


/*				BitmapFactory.Options options = new BitmapFactory.Options();
				options.outHeight = XXXX;
				options.outWidth = XXXX;
				Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.myimage, options);

				imageView.setImageBitmap(bm);*/
				break;
		}
	}


	/* ************************************************
	 * Checks for filled rows
	 * ************************************************/
	private boolean lookForRows(){

		boolean somethingRemoved = false; //To determine if some row has been removed to keep the combo

		boolean full;

		for (int i = 1; i < 20; i++){
			full = true;
			for (int j = 0; j < 10; j++){
				if (box[i][j].getColor() == Values.COLOR_NONE)
					full = false;
			}

			if (full){
				somethingRemoved = true;
				//Remove the row. The score is increase here
				removeRow(i);

			}
		}

		return somethingRemoved;
	}




	/* ************************************************
	 * Removes the row passed as argument
	 * ************************************************/
	private void removeRow(int row){

		soundLine.start();

		score = score + Values.SCORE_PER_ROW * combo;

		textScore.setText(Integer.toString(score));


		ligne = ligne + 1;

		nbLignes.setText(String.valueOf(ligne));

		//Setcombo multiplier
		combo = combo * 2;

		if (combo == 32)
			combo = 16;


		// moves all the rows above the removed one one position down
		for (int i = row; i > 1; i--)
			for (int j = 0; j < 10; j++){
				box[i][j].setColor(box[i - 1][j].getColor());
				gameBoard.setColor(i, j, (byte) box[i - 1][j].getColor());
			}



		speed();

	}


	/* *********************************************************************************************
	 * Vitesse descente blocs
	 * ********************************************************************************************/
	private void timer(int temps) {

		timer = new CountDownTimer(150000, temps) {
			public void onTick(long millisUntilFinished) {
				gameAction();
			}

			public void onFinish() {
				gameAction();
				start();
			}
		}.start();
	}


	private void speed() {
		if (ligne >= 10 && ligne < 20) {
			timer.cancel();
			timer(900);
			niveau.setText("1");
		} else if (ligne >= 20 && ligne < 30) {
			timer.cancel();
			timer(800);
			niveau.setText("2");

		} else if (ligne >= 30 && ligne < 40) {
			timer.cancel();
			timer(700);
			niveau.setText("3");

		} else if (ligne >= 40 && ligne < 50) {
			timer.cancel();
			timer(600);
			niveau.setText("4");

		} else if (ligne >= 50 && ligne < 60) {
			timer.cancel();
			timer(500);
			niveau.setText("5");

		} else if (ligne >= 60 && ligne < 70) {
			timer.cancel();
			timer(400);
			niveau.setText("6");

		} else if (ligne >= 70 && ligne < 80) {
			timer.cancel();
			timer(300);
			niveau.setText("7");

		} else if (ligne >= 80 && ligne < 90) {
			timer.cancel();
			timer(200);
			niveau.setText("8");

		} else if (ligne >= 90) {
			timer.cancel();
			timer(100);
			niveau.setText("9");

		}

	}

	/*************************************************/
	/* Draws the piece being played ******************/
	/*************************************************/
	/* Draws cubes in the positions occupied by the **/
	/* current piece. Should be called after undraw() /
	/*************************************************/
	private void reDraw(){
		//Read where the piece is and colorize
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 10; j++){
				if (currentPiece.box[i][j]){
					box[i][j].setColor(currentPiece.getColor());
					gameBoard.setColor(i, j, currentPiece.getColor());
				}
			}
	}



	/* ************************************************
	 * Clears the piece being played
	 * ************************************************
	 * Clears cubes in the positions occupied by the
	 * current piece. Should be called befors draw()
	 * ************************************************/
	private void unDraw(){
		for (int i = 0; i < 20; i++)
			for (int j = 0; j < 10; j++){
				if (currentPiece.box[i][j]){
					box[i][j].setColor(0);
					gameBoard.setColor(i, j, (byte) 0);
				}
			}
	}



	/* ************************************************
	 * Checks if the current game is loose
	 * ************************************************/
	private void checkGameLoose() {
		int hScore1, hScore2, hScore3, aux;

		String hScore1Date, hScore2Date, hScore3Date;

		boolean loose = false;

		for (int j = 0; j < 10; j++)
			if (box[1][j].getColor() != Values.COLOR_NONE)
				loose = true;
		if (!loose)
			return;
		//If I get here, the game is loose. Game state variable is set to false
		game = false;



		//Add high scores if needed
		SharedPreferences highScores = getSharedPreferences("highScores", 0);
		hScore1 = highScores.getInt("hScore1", 0);
		hScore2 = highScores.getInt("hScore2", 0);
		hScore3 = highScores.getInt("hScore3", 0);
		hScore1Date = highScores.getString("hScore1Date", "0");
		hScore2Date = highScores.getString("hScore2Date", "0");
		hScore3Date = highScores.getString("hScore3Date", "0");

		Calendar currentDate = Calendar.getInstance();

		Date dateNow = currentDate.getTime();

		if(score > hScore3){
			hScore3 = score;
			hScore3Date = dateNow.toString();
		}

		if(hScore3 > hScore2){
			aux = hScore2;
			hScore2 = hScore3;
			hScore2Date = hScore3Date;
			hScore3 = aux;
		}

		if(hScore2 > hScore1){
			aux = hScore1;
			hScore1 = hScore2;
			hScore1Date = hScore2Date;
			hScore2 = aux;
		}

		SharedPreferences.Editor editor = highScores.edit();
		editor.putInt("hScore1", hScore1);
		editor.putInt("hScore2", hScore2);
		editor.putInt("hScore3", hScore3);
		editor.putString("hScore1Date", hScore1Date);
		editor.putString("hScore2Date", hScore1Date);
		editor.putString("hScore3Date", hScore1Date);
		editor.commit();

		//Show dialog showing score
		//TODO:Show a trophy icon if high score
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.gameover);
		String msg = getString(R.string.score1)+ " " + Integer.toString(score);

        builder.setMessage(msg)
                .setCancelable(false)

                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
						mpRelease();
                        finish();
                    }
                })

                .setPositiveButton(R.string.newgame, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        startActivity(getIntent());
                    }
                });

		AlertDialog endGameAlert = builder.create();
		try {
			endGameAlert.show();
		} catch (Exception ignored) {}
	}

	/***********************************************************************************************
	 * Touche retour
	 **********************************************************************************************/

	@Override
	public boolean onKeyDown(final int keyCode, final KeyEvent event) {

		mpRelease();

		return super.onKeyDown(keyCode, event);
	}

	private void mpRelease() {
		soundMove.release();
		soundLine.release();
		soundDown.release();
		soundRotate.release();
	}

}
