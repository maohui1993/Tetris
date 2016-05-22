package org.oucho.tetris;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final static Intent intent = new Intent();
    private MediaPlayer intro;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.buttonAbout).setOnClickListener(this);
        this.findViewById(R.id.buttonNewGame).setOnClickListener(this);
        this.findViewById(R.id.buttonHighScores).setOnClickListener(this);

        intro = MediaPlayer.create(this, R.raw.intro);
        intro.start();

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.buttonNewGame:

                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Game"));
                startActivity(intent);
                intro.stop();
                intro.release();
                break;

            case R.id.buttonHighScores:
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.HighScores"));
                startActivity(intent);
                break;

            case R.id.buttonAbout:
                showAboutDialog();
                break;

            default:
                break;
        }
    }

    /**************
     * About dialog
     **************/

    private void showAboutDialog(){
        AboutDialog dialog = new AboutDialog();
        dialog.show(getSupportFragmentManager(), "about");
    }

}
