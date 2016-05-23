package org.oucho.tetris;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;



public class HighScores extends AppCompatActivity {


	@SuppressWarnings("ConstantConditions")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.highscores);

		TextView hs1, hs2, hs3, hs4, hs5;

		TextView hs1d, hs2d, hs3d, hs4d, hs5d;

		ImageView trophy1, trophy2, trophy3, trophy4, trophy5;


		SharedPreferences highScores;

		setTitle("Scores");

        hs1 = (TextView) findViewById(R.id.textViewHighScore1);
        hs2 = (TextView) findViewById(R.id.textViewHighScore2);
        hs3 = (TextView) findViewById(R.id.textViewHighScore3);
		hs4 = (TextView) findViewById(R.id.textViewHighScore4);
		hs5 = (TextView) findViewById(R.id.textViewHighScore5);

        hs1d = (TextView) findViewById(R.id.textViewHighScore1Date);
        hs2d = (TextView) findViewById(R.id.textViewHighScore2Date);
        hs3d = (TextView) findViewById(R.id.textViewHighScore3Date);
		hs4d = (TextView) findViewById(R.id.textViewHighScore4Date);
		hs5d = (TextView) findViewById(R.id.textViewHighScore5Date);

        trophy1 = (ImageView) findViewById(R.id.imageViewTrophy1);
        trophy2 = (ImageView) findViewById(R.id.imageViewTrophy2);
        trophy3 = (ImageView) findViewById(R.id.imageViewTrophy3);
		trophy4 = (ImageView) findViewById(R.id.imageViewTrophy4);
		trophy5 = (ImageView) findViewById(R.id.imageViewTrophy5);


        highScores = getSharedPreferences("highScores", 0);

		if (highScores.getInt("hScore1", 0) > 0){
			hs1.setText(getResources().getString(R.string.premier) + ": " + Integer.toString(highScores.getInt("hScore1", 0)));
			hs1d.setText(highScores.getString("hScore1Date", " "));
		} else {
			//Set icon to none
			trophy1.setImageResource(R.color.alpha);
			//Show "No high scores message"
			hs1d.setText(getResources().getString(R.string.nohscores));
		}

		if (highScores.getInt("hScore2", 0) > 0){
			hs2.setText(getResources().getString(R.string.second) + ": " + Integer.toString(highScores.getInt("hScore2", 0)));
			hs2d.setText(highScores.getString("hScore2Date", " "));
		} else {
			//Set icon to none
			trophy2.setImageResource(R.color.alpha);
		}

		if (highScores.getInt("hScore3", 0) > 0){
			hs3.setText(getResources().getString(R.string.troisième) + ": " + Integer.toString(highScores.getInt("hScore3", 0)));
			hs3d.setText(highScores.getString("hScore3Date", " "));
		} else {
			//Set icon to none
			trophy3.setImageResource(R.color.alpha);
		}

		if (highScores.getInt("hScore4", 0) > 0){
			hs4.setText(getResources().getString(R.string.quatrième) + ": " + Integer.toString(highScores.getInt("hScore4", 0)));
			hs4d.setText(highScores.getString("hScore4Date", " "));
		} else {
			//Set icon to none
			trophy4.setImageResource(R.color.alpha);
		}

		if (highScores.getInt("hScore5", 0) > 0){
			hs5.setText(getResources().getString(R.string.cinquième) + ": " + Integer.toString(highScores.getInt("hScore5", 0)));
			hs5d.setText(highScores.getString("hScore5Date", " "));
		} else {
			//Set icon to none
			trophy5.setImageResource(R.color.alpha);
		}
	}
}
