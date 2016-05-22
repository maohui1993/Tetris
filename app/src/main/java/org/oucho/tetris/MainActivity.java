package org.oucho.tetris;

import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import org.oucho.tetris.update.AppUpdate;
import org.oucho.tetris.update.Display;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final static Intent intent = new Intent();
    private MediaPlayer soundIntro;
    private MediaPlayer soundClick;

    private static final String updateURL = "http://oucho.free.fr/app_android/Tetris/update_tetris.xml";

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.buttonAbout).setOnClickListener(this);
        this.findViewById(R.id.buttonNewGame).setOnClickListener(this);
        this.findViewById(R.id.buttonHighScores).setOnClickListener(this);

        soundClick = MediaPlayer.create(this, R.raw.move);

        soundIntro = MediaPlayer.create(this, R.raw.intro);
        soundIntro.start();

        updateOnStart();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.buttonNewGame:
                soundClick.start();
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Game"));
                startActivity(intent);
                soundIntro.stop();
                break;

            case R.id.buttonHighScores:
                soundClick.start();
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.HighScores"));
                startActivity(intent);
                break;

            case R.id.buttonAbout:
                soundClick.start();
                showAboutDialog();
                break;

            default:
                break;
        }
    }

    /* **********************************************************************************************
    * Mise à jour
    * *********************************************************************************************/

    private void updateOnStart() {

        new AppUpdate(this)
                .setUpdateXML(updateURL)
                .setDisplay(Display.SNACKBAR)
                .start();
    }

    /**************
     * About dialog
     **************/

    private void showAboutDialog(){
        AboutDialog dialog = new AboutDialog();
        dialog.show(getSupportFragmentManager(), "about");
    }

    /***********************************************************************************************
     * Touche retour
     **********************************************************************************************/

    @Override
    public boolean onKeyDown(final int keyCode, final KeyEvent event) {

        mpRelease();
        finish();
        return super.onKeyDown(keyCode, event);
    }

    private void mpRelease() {
        //soundIntro.stop();
        soundClick.release();
        soundIntro.release();

    }

}
