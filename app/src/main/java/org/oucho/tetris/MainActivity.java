package org.oucho.tetris;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final static Intent intent = new Intent();
    private final static Handler handler = new Handler();

    private MediaPlayer soundIntro;
    private int soundClick;

    private SoundPool soundPool;

    private ImageView pièce0, pièce1, pièce2, pièce3, pièce4, pièce5, pièce6;
    private TranslateAnimation animation0, animation1, animation2, animation3, animation4, animation5, animation6;


    private int[] rotAngle;
    private int[] délaiAnim;

    private static int hauteurEcran;
    private static int divisionEcran;

    private String[] tetrinominos;
    private static final Random rgenerator = new Random();


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();


        rotAngle = res.getIntArray(R.array.Angles);
        délaiAnim = res.getIntArray(R.array.Délai);
        tetrinominos = res.getStringArray(R.array.Tetrinominos);

        this.findViewById(R.id.buttonAbout).setOnClickListener(this);
        this.findViewById(R.id.buttonNewGame).setOnClickListener(this);
        this.findViewById(R.id.buttonHighScores).setOnClickListener(this);

        pièce0 = (ImageView) findViewById(R.id.anim0);
        pièce1 = (ImageView) findViewById(R.id.anim1);
        pièce2 = (ImageView) findViewById(R.id.anim2);
        pièce3 = (ImageView) findViewById(R.id.anim3);
        pièce4 = (ImageView) findViewById(R.id.anim4);
        pièce5 = (ImageView) findViewById(R.id.anim5);
        pièce6 = (ImageView) findViewById(R.id.anim6);


        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int tailleDPI = metrics.densityDpi;
        // arrondi inférieur Math.floor
        divisionEcran = (int) Math.floor(tailleDPI / 11);

        Point size = new Point();
        this.getWindowManager().getDefaultDisplay().getSize(size);
        hauteurEcran = size.y;


        AudioAttributes audioAttrib = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder()
                .setAudioAttributes(audioAttrib)
                .setMaxStreams(1)
                .build();



        soundClick = soundPool.load(this, R.raw.move, 1);

        soundIntro = MediaPlayer.create(this, R.raw.intro);
        soundIntro.start();

        animTetriminos();
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            soundIntro.stop();
        } catch (Exception ignored) {}

        pièce0.clearAnimation();
        pièce1.clearAnimation();
        pièce2.clearAnimation();
        pièce3.clearAnimation();
        pièce4.clearAnimation();
        pièce5.clearAnimation();
        pièce6.clearAnimation();

    }

    @Override
    public void onResume() {
        super.onResume();

        animTetriminos();

    }


    private void animTetriminos() {

        pièce0.setImageResource(genTetrinominos());
        pièce1.setImageResource(genTetrinominos());
        pièce2.setImageResource(genTetrinominos());
        pièce3.setImageResource(genTetrinominos());
        pièce4.setImageResource(genTetrinominos());
        pièce5.setImageResource(genTetrinominos());
        pièce6.setImageResource(genTetrinominos());

        // TranslateAnimation(float fromXDelta, float toXDelta, float fromYDelta, float toYDelta)


        handler.postDelayed(new Runnable() {
            public void run() {

                animation0 = new TranslateAnimation(0, 0, 0, hauteurEcran+90);
                animation0.setAnimationListener(new AnimationListener0());
                animation0.setDuration(genDelai());
                animation0.setRepeatCount(-1);
                animation0.setRepeatMode(Animation.RESTART);
                animation0.setFillAfter(true);
                pièce0.startAnimation(animation0);
            }
        }, 0);



        handler.postDelayed(new Runnable() {
            public void run() {
                animation1 = new TranslateAnimation(divisionEcran, divisionEcran, 0, hauteurEcran+90);
                animation1.setAnimationListener(new AnimationListener1());
                animation1.setDuration(genDelai());
                animation1.setRepeatCount(-1);
                animation1.setRepeatMode(Animation.RESTART);
                animation1.setFillAfter(true);
                pièce1.startAnimation(animation1);
            }
        }, 3000);


        handler.postDelayed(new Runnable() {
            public void run() {
                animation2 = new TranslateAnimation(divisionEcran*2, divisionEcran*2, 0, hauteurEcran+90);
                animation2.setAnimationListener(new AnimationListener2());
                animation2.setDuration(genDelai());
                animation2.setRepeatCount(-1);
                animation2.setRepeatMode(Animation.RESTART);
                animation2.setFillAfter(true);
                pièce2.startAnimation(animation2);
            }
        }, 6000);

        handler.postDelayed(new Runnable() {
            public void run() {
                animation3 = new TranslateAnimation(divisionEcran*3, divisionEcran*3, 0, hauteurEcran+90);
                animation3.setAnimationListener(new AnimationListener3());
                animation3.setDuration(genDelai());
                animation3.setRepeatCount(-1);
                animation3.setRepeatMode(Animation.RESTART);
                animation3.setFillAfter(true);
                pièce3.startAnimation(animation3);
            }
        }, 9000);

        handler.postDelayed(new Runnable() {
            public void run() {
                animation4 = new TranslateAnimation(divisionEcran*4, divisionEcran*4, 0, hauteurEcran+90);
                animation4.setAnimationListener(new AnimationListener4());
                animation4.setDuration(genDelai());
                animation4.setRepeatCount(-1);
                animation4.setRepeatMode(Animation.RESTART);
                animation4.setFillAfter(true);
                pièce4.startAnimation(animation4);
            }
        }, 12000);


        handler.postDelayed(new Runnable() {
            public void run() {
                animation5 = new TranslateAnimation(divisionEcran*5, divisionEcran*5, 0, hauteurEcran+90);
                animation5.setAnimationListener(new AnimationListener5());
                animation5.setDuration(genDelai());
                animation5.setRepeatCount(-1);
                animation5.setRepeatMode(Animation.RESTART);
                animation5.setFillAfter(true);
                pièce5.startAnimation(animation5);
            }
        }, 15000);

        handler.postDelayed(new Runnable() {
            public void run() {
                animation6 = new TranslateAnimation(divisionEcran*6, divisionEcran*6, 0, hauteurEcran+90);
                animation6.setAnimationListener(new AnimationListener6());
                animation6.setDuration(genDelai());
                animation6.setRepeatCount(-1);
                animation6.setRepeatMode(Animation.RESTART);
                animation6.setFillAfter(true);
                pièce6.startAnimation(animation6);
            }
        }, 18000);
    }


    private int genDelai() {
        return délaiAnim[rgenerator.nextInt(délaiAnim.length)];
    }

    private int genAngle() {
        return rotAngle[rgenerator.nextInt(rotAngle.length)];
    }

    private int genTetrinominos() {

        String img = "drawable/" + tetrinominos[rgenerator.nextInt(tetrinominos.length)];

        return this.getResources().getIdentifier(img, null, this.getPackageName());
    }


    private class AnimationListener0 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation0.setDuration(genDelai());
            pièce0.setImageResource(genTetrinominos());
            pièce0.setRotation(genAngle());

        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener1 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation1.setDuration(genDelai());
            pièce1.setImageResource(genTetrinominos());
            pièce1.setRotation(genAngle());

        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener2 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation2.setDuration(genDelai());
            pièce2.setImageResource(genTetrinominos());
            pièce2.setRotation(genAngle());
        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener3 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation3.setDuration(genDelai());
            pièce3.setImageResource(genTetrinominos());
            pièce3.setRotation(genAngle());
        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener4 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation4.setDuration(genDelai());
            pièce4.setImageResource(genTetrinominos());
            pièce4.setRotation(genAngle());

        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener5 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {
            animation5.setDuration(genDelai());
            pièce5.setImageResource(genTetrinominos());
            pièce5.setRotation(genAngle());

        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    private class AnimationListener6 implements AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {}

        @Override
        public void onAnimationRepeat(Animation animation) {

            animation6.setDuration(genDelai());
            pièce6.setImageResource(genTetrinominos());
            pièce6.setRotation(genAngle());

        }

        @Override
        public void onAnimationStart(Animation animation) {}

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.buttonNewGame:
                soundPool.play(soundClick, 1, 1, 1, 0, 1);
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Game"));
                startActivity(intent);
                soundIntro.stop();
                break;

            case R.id.buttonHighScores:
                soundPool.play(soundClick, 1, 1, 1, 0, 1);
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.HighScores"));
                startActivity(intent);
                break;

            case R.id.buttonAbout:
                soundPool.play(soundClick, 1, 1, 1, 0, 1);
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

    /***********************************************************************************************
     * Touche retour
     **********************************************************************************************/

    @Override
    public void onBackPressed() {
        mpRelease();
        finish();
    }

    private void mpRelease() {
        //soundIntro.stop();
        soundIntro.release();

    }

}
