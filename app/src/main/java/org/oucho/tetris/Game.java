package org.oucho.tetris;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

	private Pieces nextPiece = new Pieces(false, 0);
	private Pieces currentPiece;

    private byte répétition0 = 0;
    private byte répétition1 = -1;
    private byte répétition2 = -1;


	private CountDownTimer timer;

	private GameBoard gameBoard;

	private SoundPool soundPool;

	private int soundMove;
	private int soundLine;
	private int soundDown;
	private int soundRotate;

	private TextView niveau;
	private TextView btnPause;
	private TextView nbLignes;
	private TextView textScore;

    private ImageView nextPieceImg;
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


    // première pièce J, I, L ou T
    private static final Random rgenerator = new Random();
    private int[] tirage;

    private int compteurPièce = -1;

    private int piece0 = 0;
    private int piece1 = 0;
    private int piece2 = 0;
    private int piece3 = 0;
    private int piece4 = 0;
    private int piece5 = 0;
    private int piece6 = 0;


	@SuppressWarnings("ConstantConditions")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.game);

        tirage = getResources().getIntArray(R.array.PremierTirage);

		btnPause = (TextView) findViewById(R.id.buttonPause);

		gameBoard = (GameBoard) findViewById(R.id.GameView);

		button0 = (ImageView) findViewById(R.id.ButtonMoveD);

		nextPieceImg = (ImageView) findViewById(R.id.imageViewNext);


        niveau = (TextView) findViewById(R.id.niveau);
		nbLignes = (TextView) findViewById(R.id.lignes);
		textScore = (TextView) findViewById(R.id.TextViewScore);


        for(int ID : bouton_ID)
			this.findViewById(ID).setOnClickListener(this);


		game = true;


		AudioAttributes audioAttrib = new AudioAttributes.Builder()
				.setUsage(AudioAttributes.USAGE_GAME)
				.setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
				.build();
		soundPool = new SoundPool.Builder()
				.setAudioAttributes(audioAttrib)
				.setMaxStreams(3)
				.build();

		soundDown = soundPool.load(this, R.raw.down, 1);
		soundLine = soundPool.load(this, R.raw.line, 1);
		soundMove = soundPool.load(this, R.raw.move, 1);
		soundRotate = soundPool.load(this, R.raw.rotate, 1);


		textScore.setText("0");
		niveau.setText("0");

        setPremierTirage();

		board();
		setImgs();
		timer(1000);

		currentPiece.start();
	}

    private void setPremierTirage() {

        int plop =  tirage[rgenerator.nextInt(tirage.length)];

        do {
            currentPiece = new Pieces(true, plop);
        } while (currentPiece.type == nextPiece.type);

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

                    soundPool.play(soundMove, 1, 1, 1, 0, 1);

					unDraw();
					currentPiece.moveRight();
					reDraw();
				}

				break;

			case R.id.ButtonMoveL:

				if (game) {

                    soundPool.play(soundMove, 1, 1, 1, 0, 1);

					unDraw();
					currentPiece.moveLeft();
					reDraw();
				}

				break;

			case R.id.ButtonMoveD:

				if (game) {

                    soundPool.play(soundMove, 1, 1, 1, 0, 1);

					unDraw();
					currentPiece.moveDown();
					reDraw();

					// long press
					down();
				}

				break;

			case R.id.buttonRotateR:

				if (game) {

                    soundPool.play(soundRotate, 1, 1, 1, 0, 1);

					unDraw();
					currentPiece.rotateRight();
					reDraw();
				}

				break;

			case R.id.ButtonRotateL:

				if (game) {

                    soundPool.play(soundRotate, 1, 1, 1, 0, 1);

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

                soundPool.play(soundDown, 1, 1, 1, 0, 1);

                compteurPièce++;


                if (currentPiece.type == 0) {
                    piece0 = piece0 + 1;
                }

                if (currentPiece.type == 1) {
                    piece1 = piece1 + 1;
                }

                if (currentPiece.type == 2) {
                    piece2 = piece2 + 1;
                }

                if (currentPiece.type == 3) {
                    piece3 = piece3 + 1;
                }

                if (currentPiece.type == 4) {
                    piece4 = piece4 + 1;
                }

                if (currentPiece.type == 5) {
                    piece5 = piece5 + 1;
                }

                if (currentPiece.type == 6) {
                    piece6 = piece6 + 1;
                }



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



                do {

                    nextPiece = new Pieces(false, 0);

                } while (nextPiece.type == répétition0 && nextPiece.type == répétition1
                        || nextPiece.type == répétition1 && nextPiece.type == répétition2
                        || nextPiece.type == répétition1
                        || nextPiece.type == 4 && répétition0 == 4
                        || nextPiece.type == 5 && répétition0 == 5);


                if  (compteurPièce == 5) {

                    if (piece0 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece0");
                    }
                    if (piece1 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece1");
                    }
                    if (piece2 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece2");
                    }
                    if (piece3 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece3");
                    }
                    if (piece4 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece4");
                    }
                    if (piece5 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece5");
                    }
                    if (piece6 >= 3) {
                        nextPiece = new Pieces(false, 0);
                        Log.d("Compteur pièce 5", "piece6");
                    }
                }


                if (compteurPièce == 10) {

                    if (piece0 == 0) {
                        Log.d("Compteur pièce 10", "piece0");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 0);

                    } else if (piece1 == 0) {
                        Log.d("Compteur pièce 10", "piece1");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 1);

                    } else if (piece2 == 0) {
                        Log.d("Compteur pièce 10", "piece2");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 2);

                    } else if (piece3 == 0) {
                        Log.d("Compteur pièce 10", "piece3");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 3);

                    } else if (piece4 == 0) {
                        Log.d("Compteur pièce 10", "piece4");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 4);

                    } else if (piece5 == 0) {
                        Log.d("Compteur pièce 10", "piece5");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 5);

                    } else if (piece6 == 0) {
                        Log.d("Compteur pièce 10", "piece6");
                        do {
                            nextPiece = new Pieces(false, 0);

                        } while (nextPiece.type != 6);
                    }


                    compteurPièce = -1;

                    piece0 = 0;
                    piece1 = 0;
                    piece2 = 0;
                    piece3 = 0;
                    piece4 = 0;
                    piece5 = 0;
                    piece6 = 0;

                }

                répétition2 = répétition1;
                répétition1 = répétition0;
                répétition0 = nextPiece.type;


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

				nextPieceImg.setImageResource(R.drawable.piece0);

				break;
			case Values.PIECE_1:

				nextPieceImg.setImageResource(R.drawable.piece1);

				break;
			case Values.PIECE_2:

				nextPieceImg.setImageResource(R.drawable.piece2);

				break;
			case Values.PIECE_3:

				nextPieceImg.setImageResource(R.drawable.piece3);

				break;
			case Values.PIECE_4:

				nextPieceImg.setImageResource(R.drawable.piece4);

				break;
			case Values.PIECE_5:

				nextPieceImg.setImageResource(R.drawable.piece5);

				break;
			case Values.PIECE_6:

				nextPieceImg.setImageResource(R.drawable.piece6);

				break;

			default:
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

        soundPool.play(soundLine, 1, 1, 1, 0, 1);



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
		int hScore1, hScore2, hScore3, hScore4, hScore5;
		int tmpScore;

		String hScore1Date, hScore2Date, hScore3Date, hScore4Date, hScore5Date;
		String tmpDate;

		boolean loose = false;

		for (int j = 0; j < 10; j++)
			if (box[1][j].getColor() != Values.COLOR_NONE)
				loose = true;
		if (!loose)
			return;

		// si la partie est perdu
		game = false;



		SharedPreferences highScores = getSharedPreferences("highScores", 0);
		hScore1 = highScores.getInt("hScore1", 0);
		hScore2 = highScores.getInt("hScore2", 0);
		hScore3 = highScores.getInt("hScore3", 0);
		hScore4 = highScores.getInt("hScore4", 0);
		hScore5 = highScores.getInt("hScore5", 0);
		hScore1Date = highScores.getString("hScore1Date", "0");
		hScore2Date = highScores.getString("hScore2Date", "0");
		hScore3Date = highScores.getString("hScore3Date", "0");
		hScore4Date = highScores.getString("hScore4Date", "0");
		hScore5Date = highScores.getString("hScore5Date", "0");

		@SuppressLint("SimpleDateFormat")
		DateFormat df = new SimpleDateFormat("cccc d MMMM yyyy, HH:mm");
		String date = df.format(Calendar.getInstance().getTime());

		if(score > hScore5){
			hScore5 = score;
			hScore5Date = date;
		}


		if(hScore5 > hScore4){
			tmpScore = hScore4;
			tmpDate = hScore4Date;

			hScore4 = hScore5;
			hScore4Date = hScore5Date;

			hScore5 = tmpScore;
			hScore5Date = tmpDate;
		}

		if(hScore4 > hScore3){
			tmpScore = hScore3;
			tmpDate = hScore3Date;

			hScore3 = hScore4;
			hScore3Date = hScore4Date;

			hScore4 = tmpScore;
			hScore4Date = tmpDate;
		}

		if(hScore3 > hScore2){
			tmpScore = hScore2;
			tmpDate = hScore2Date;

			hScore2 = hScore3;
			hScore2Date = hScore3Date;

			hScore3 = tmpScore;
			hScore3Date = tmpDate;
		}

		if(hScore2 > hScore1){
			tmpScore = hScore1;
			tmpDate = hScore1Date;

			hScore1 = hScore2;
			hScore1Date = hScore2Date;

			hScore2 = tmpScore;
			hScore2Date = tmpDate;
		}

		SharedPreferences.Editor editor = highScores.edit();
		editor.putInt("hScore1", hScore1);
		editor.putInt("hScore2", hScore2);
		editor.putInt("hScore3", hScore3);
		editor.putInt("hScore4", hScore4);
		editor.putInt("hScore5", hScore5);
		editor.putString("hScore1Date", hScore1Date);
		editor.putString("hScore2Date", hScore2Date);
		editor.putString("hScore3Date", hScore3Date);
		editor.putString("hScore4Date", hScore4Date);
		editor.putString("hScore5Date", hScore5Date);
		editor.apply();

		//Show dialog showing score
		//TODO:Show a trophy icon if high score
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.gameover);
		String msg = getString(R.string.score1)+ " " + Integer.toString(score);

        builder.setMessage(msg)
                .setCancelable(false)

                .setNegativeButton(R.string.end, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
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

		return super.onKeyDown(keyCode, event);
	}
}
