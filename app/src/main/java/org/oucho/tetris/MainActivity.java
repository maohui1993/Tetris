package org.oucho.tetris;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final static Intent intent = new Intent();


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(R.id.buttonNewGame).setOnClickListener(this);
        this.findViewById(R.id.buttonResumeGame).setOnClickListener(this);
        this.findViewById(R.id.buttonHighScores).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.buttonNewGame:

                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Game"));
                startActivity(intent);
                break;

            case R.id.buttonResumeGame:
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Game"));
                //TODO: Something to load a game. First I need to develop something to save the game
                startActivity(intent);
                break;

            case R.id.buttonHighScores:
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.HighScores"));
                startActivity(intent);
                break;

            default:
                break;
        }
    }


    /*************************************************/
	/* Create the options menu ***********************/
    /*************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.initmenu, menu);
        return true;
    }

    /*************************************************/
	/* Set actions for each menu element *************/
    /*************************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        intent = new Intent();
        switch(item.getItemId()){

            case R.id.menuItemPreferences:
                intent.setComponent(new ComponentName("org.oucho.tetris", "org.oucho.tetris.Preferences"));
                break;

            default:
                break;
        }
        startActivity(intent);
        return true;
    }

}
