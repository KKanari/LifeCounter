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
import android.widget.Toast;

import java.util.Random;

/**
 * Created by KMS2 on 2016/03/02.
 */
public class Coin extends AppCompatActivity {
    final short SELECT_NEUTRAL = 0;
    final short SELECT_HEADS = 1;
    final short SELECT_TAILS = 2;
    final short RESULT_HEADS = 1;
    final short RESULT_TAILS = 2;
    final short FLIP_NOT_RUN = 0;
    final short FLIP_RUN = 1;

    short flagSelectCoin = SELECT_NEUTRAL;
    int resultCoin = 0;
    short flagFlip = 0;

    Globals globals;
    ImageView ivCoinHeads;
    ImageView ivCoinTails;
    ImageView ivResult;
    Button btnFlip;

    RelativeLayout rlBackground;

    AnimationDrawable faCoin;

    Bitmap bmCoinHeads;
    Bitmap bmCoinTails;
    Bitmap bmSelectCoinHeads;
    Bitmap bmSelectCoinTails;

    Intent intent;
    Toast toast;

    String coin_select;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coin);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        intent = getIntent();
        globals = (Globals) getApplication();

        Resources r = getResources();

        bmCoinHeads = BitmapFactory.decodeResource(r, R.drawable.coin_heads);
        bmCoinTails = BitmapFactory.decodeResource(r, R.drawable.coin_tails);
        bmSelectCoinHeads = BitmapFactory.decodeResource(r, R.drawable.coin_select_heads);
        bmSelectCoinTails = BitmapFactory.decodeResource(r, R.drawable.coin_select_tails);

        ivCoinHeads = (ImageView) findViewById(R.id.imgHeads);
        ivCoinTails = (ImageView) findViewById(R.id.imgTails);
        ivResult = (ImageView) findViewById(R.id.imgResult);
        btnFlip = (Button) findViewById(R.id.buttonFlip);

        rlBackground = (RelativeLayout) findViewById(R.id.rlCoinBackground);
        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        ivCoinHeads.setImageBitmap(bmCoinHeads);
        ivCoinTails.setImageBitmap(bmCoinTails);

        ivResult.setVisibility(View.INVISIBLE);

        coin_select = getString(R.string.coin_select);
    }

    public void clickFlip(View view) {
        if (flagFlip == FLIP_RUN) {
            ivCoinHeads.setVisibility(View.VISIBLE);
            ivCoinTails.setVisibility(View.VISIBLE);
            ivResult.setVisibility(View.INVISIBLE);
            flagSelectCoin = SELECT_NEUTRAL;
            resultCoin = 0;
            flagFlip = FLIP_NOT_RUN;
            btnFlip.setText("FLIP");
        } else {
            if (flagSelectCoin == SELECT_NEUTRAL) {
                Toast.makeText(this, coin_select, Toast.LENGTH_SHORT).show();
            } else {
                ivCoinHeads.setVisibility(View.INVISIBLE);
                ivCoinTails.setVisibility(View.INVISIBLE);
                ivResult.setVisibility(View.VISIBLE);
                resultCoin = new Random().nextInt(2);
                resultCoin++;
                if (resultCoin == RESULT_HEADS) {
                    ivResult.setBackgroundResource(R.drawable.coin_animation_heads);
                } else if (resultCoin == RESULT_TAILS) {
                    ivResult.setBackgroundResource(R.drawable.coin_animation_tails);
                }

                faCoin = (AnimationDrawable) ivResult.getBackground();
                faCoin.start();

                if (flagSelectCoin == SELECT_HEADS) {
                    if (resultCoin == SELECT_HEADS) {
                        Toast.makeText(this, "Win!", Toast.LENGTH_SHORT).show();
                    } else if(resultCoin == SELECT_TAILS){
                        Toast.makeText(this, "Lose...", Toast.LENGTH_SHORT).show();
                    }
                    flagSelectCoin = SELECT_NEUTRAL;
                } else if (flagSelectCoin == SELECT_TAILS) {
                    if (resultCoin == SELECT_TAILS) {
                        Toast.makeText(this, "Win!", Toast.LENGTH_SHORT).show();
                    } else if(resultCoin == SELECT_HEADS){
                        Toast.makeText(this, "Lose...", Toast.LENGTH_SHORT).show();
                    }
                    flagSelectCoin = SELECT_NEUTRAL;
                } else {
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                }
                ivCoinHeads.setImageBitmap(bmCoinHeads);
                ivCoinTails.setImageBitmap(bmCoinTails);
                btnFlip.setText("RESET");
                flagFlip = FLIP_RUN;
            }
        }
    }

    public void clickHeads(View view) {
        ivCoinHeads.setImageBitmap(bmSelectCoinHeads);
        ivCoinTails.setImageBitmap(bmCoinTails);
        flagSelectCoin = SELECT_HEADS;
    }

    public void clickTails(View view) {
        ivCoinHeads.setImageBitmap(bmCoinHeads);
        ivCoinTails.setImageBitmap(bmSelectCoinTails);
        flagSelectCoin = SELECT_TAILS;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
    }
}
