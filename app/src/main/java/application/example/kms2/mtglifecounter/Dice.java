package application.example.kms2.mtglifecounter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by KMS2 on 2016/03/02.
 */
public class Dice extends AppCompatActivity {
    ImageView ivDice;
    AnimationDrawable faDice;
    Button btnRoll;

    final short ROLL_ON = 0;
    final short ROLL_OFF = 1;

    short flagRoll = ROLL_ON;
    int rslDice;

    Globals globals;
    RelativeLayout rlBackground;

    Bitmap bmDice_1;
    Bitmap bmDice_2;
    Bitmap bmDice_3;
    Bitmap bmDice_4;
    Bitmap bmDice_5;
    Bitmap bmDice_6;
    Bitmap bmSkele;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dice);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        globals = (Globals) getApplication();

        ivDice = (ImageView) findViewById(R.id.imgDice);
        btnRoll = (Button) findViewById(R.id.buttonRoll);

        rlBackground = (RelativeLayout) findViewById(R.id.rlDiceBackground);
        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        Resources r = getResources();
        bmDice_1 = BitmapFactory.decodeResource(r, R.drawable.dice_1);
        bmDice_2 = BitmapFactory.decodeResource(r, R.drawable.dice_2);
        bmDice_3 = BitmapFactory.decodeResource(r, R.drawable.dice_3);
        bmDice_4 = BitmapFactory.decodeResource(r, R.drawable.dice_4);
        bmDice_5 = BitmapFactory.decodeResource(r, R.drawable.dice_5);
        bmDice_6 = BitmapFactory.decodeResource(r, R.drawable.dice_6);
        bmSkele = BitmapFactory.decodeResource(r, R.drawable.skeleton);
    }

    public void clickRoll(View view) {
        if (flagRoll == ROLL_ON) {
            rslDice = new Random().nextInt(6) + 1;
            switch (rslDice) {
                case 1:
                    ivDice.setImageBitmap(bmDice_1);
                    break;
                case 2:
                    ivDice.setImageBitmap(bmDice_2);
                    break;
                case 3:
                    ivDice.setImageBitmap(bmDice_3);
                    break;
                case 4:
                    ivDice.setImageBitmap(bmDice_4);
                    break;
                case 5:
                    ivDice.setImageBitmap(bmDice_5);
                    break;
                case 6:
                    ivDice.setImageBitmap(bmDice_6);
                    break;
            }
            faDice.stop();
            flagRoll = ROLL_OFF;
            btnRoll.setText("ReRoll");
        } else if (flagRoll == ROLL_OFF) {
            ivDice.setImageBitmap(bmSkele);
            faDice.start();
            flagRoll = ROLL_ON;
            btnRoll.setText("Stop");
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ivDice.setBackgroundResource(R.drawable.dice_animation);
        faDice = (AnimationDrawable) ivDice.getBackground();
        faDice.start();

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
    }
}