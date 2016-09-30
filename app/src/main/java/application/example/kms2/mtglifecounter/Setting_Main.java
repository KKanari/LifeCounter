package application.example.kms2.mtglifecounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by KMS2 on 2016/05/24.
 */
public class Setting_Main extends AppCompatActivity {
    Globals globals;

    TextView tvBackgound;
    TextView tvLetterColor;
    TextView tvLifeCounterColor;
    TextView tvPoisonCounterColor;
    TextView tvGeneralDamageColor;
    TextView tvLetterPosition;
    Button btnInitialize;
    ImageView ivBackgroundColor;
    ImageView ivLifeColor;
    ImageView ivPoisonColor;
    ImageView ivGeneralColor;
    RelativeLayout rlBackground;

    String setting_initializecheck;
    String setting_initializemessage;
    String setting_initializeyes;
    String setting_initializeno;

    Bitmap bmJustLeft;
    Bitmap bmSelectedJustLeft;
    Bitmap bmJustRight;
    Bitmap bmSelectedJustRight;

    ImageView ivPositionLeft;
    ImageView ivPositionRight;

    float[] fltIniBackgroundColor = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globals = (Globals) getApplication();
        Intent intent = getIntent();

        tvBackgound = (TextView) findViewById(R.id.txtBackGroundColor);
        tvLetterColor = (TextView) findViewById(R.id.txtLetterColor);
        tvLifeCounterColor = (TextView) findViewById(R.id.txtLifeColor);
        tvPoisonCounterColor = (TextView) findViewById(R.id.txtPoisonColor);
        tvGeneralDamageColor = (TextView) findViewById(R.id.txtGeneralColor);
        tvLetterPosition = (TextView) findViewById(R.id.txtLetterPosition);
        btnInitialize = (Button) findViewById(R.id.btnInitialize);
        ivBackgroundColor = (ImageView) findViewById(R.id.ivBackColor);
        ivLifeColor = (ImageView) findViewById(R.id.ivLifeColor);
        ivPoisonColor = (ImageView) findViewById(R.id.ivPoisonColor);
        ivGeneralColor = (ImageView) findViewById(R.id.ivGeneralColor);

        rlBackground = (RelativeLayout) findViewById(R.id.rlSettingMainBackground);

        tvBackgound.setText(R.string.setting_backgroundcolor);
        tvLetterColor.setText(R.string.setting_lettercolor);
        tvLifeCounterColor.setText(R.string.setting_lifecountercolor);
        tvPoisonCounterColor.setText(R.string.setting_poisoncountercolor);
        tvGeneralDamageColor.setText(R.string.setting_generalcolor);
        tvLetterPosition.setText(R.string.setting_letterposition);
        btnInitialize.setText(R.string.setting_initialize);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
        ivBackgroundColor.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
        ivLifeColor.setBackgroundColor(Color.HSVToColor(globals.fltLifeColor));
        ivPoisonColor.setBackgroundColor(Color.HSVToColor(globals.fltPoisonColor));
        ivGeneralColor.setBackgroundColor(Color.HSVToColor(globals.fltGeneralColor));

        setting_initializecheck = getString(R.string.setting_initializecheck);
        setting_initializemessage = getString(R.string.setting_initializemessage);
        setting_initializeyes = getString(R.string.setting_initializeyes);
        setting_initializeno = getString(R.string.setting_initializeno);

        ivPositionLeft = (ImageView) findViewById(R.id.ivPositionLeft);
        ivPositionRight = (ImageView) findViewById(R.id.ivPositionRight);

        Resources r = getResources();

        bmJustLeft = BitmapFactory.decodeResource(r, R.drawable.left);
        bmSelectedJustLeft = BitmapFactory.decodeResource(r, R.drawable.left_selected);
        bmJustRight = BitmapFactory.decodeResource(r, R.drawable.right);
        bmSelectedJustRight = BitmapFactory.decodeResource(r, R.drawable.right_selected);

        if (globals.justification == globals.just_left) {
            ivPositionLeft.setImageBitmap(bmSelectedJustLeft);
        } else {
            ivPositionRight.setImageBitmap(bmSelectedJustRight);
        }
    }

    public void clickChangeColor(View view) {
//        Toast.makeText(this, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
        switch (view.getId()) {
            case 2131689638:
                globals.selected_change_color = globals.setting_change_background;
                Intent intentPicker = new Intent(this, Setting_Picker.class);
                startActivity(intentPicker);
                break;
            case 2131689640:
                globals.selected_change_color = globals.setting_change_lifecounter;
                intentPicker = new Intent(this, Setting_Picker.class);
                startActivity(intentPicker);
                break;
            case 2131689642:
                globals.selected_change_color = globals.setting_change_poisoncounter;
                intentPicker = new Intent(this, Setting_Picker.class);
                startActivity(intentPicker);
                break;
            case 2131689644:
                globals.selected_change_color = globals.setting_change_generaldamage;
                intentPicker = new Intent(this, Setting_Picker.class);
                startActivity(intentPicker);
                break;
            default:
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickInitialize(View view) {
        AlertDialog.Builder alertAC = new AlertDialog.Builder(this);
        alertAC.setTitle(setting_initializecheck);
        alertAC.setMessage(setting_initializemessage);
        alertAC.setPositiveButton(setting_initializeyes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                globals.backgroundHue = globals.INITIAL_BACKGROUNDHUE;
                globals.backgroundSaturation = globals.INITIAL_BACKGROUNDSATURATION;
                globals.backgroundValue = globals.INITIAL_BACKGROUNDVALUE;
                globals.lifeHue = globals.INITIAL_LIFEHUE;
                globals.lifeSaturation = globals.INITIAL_LIFESATURATION;
                globals.lifeValue = globals.INITIAL_LIFEVALUE;
                globals.poisonHue = globals.INITIAL_POISONHUE;
                globals.poisonSaturation = globals.INITIAL_POISONSATURATION;
                globals.poisonValue = globals.INITIAL_POISONVALUE;
                globals.generalHue = globals.INITIAL_GENERALHUE;
                globals.generalSaturation = globals.INITIAL_GENERALSATURATION;
                globals.generalValue = globals.INITIAL_GENERALVALUE;

                globals.justification = globals.just_left;
                ivPositionLeft.setImageBitmap(bmSelectedJustLeft);
                ivPositionRight.setImageBitmap(bmJustRight);


//                Toast.makeText(getApplication(), String.valueOf(Color.HSVToColor(globals.fltBackgroundColor)), Toast.LENGTH_SHORT).show();
                globals.fltBackgroundColor = new float[]{globals.backgroundHue, globals.backgroundSaturation, globals.backgroundValue};
                globals.fltLifeColor = new float[]{globals.lifeHue, globals.lifeSaturation, globals.lifeValue};
                globals.fltPoisonColor = new float[]{globals.poisonHue, globals.poisonSaturation, globals.poisonValue};
                globals.fltGeneralColor = new float[]{globals.generalHue, globals.generalSaturation, globals.generalValue};
                onWindowFocusChanged(true);
            }
        });
        alertAC.setNegativeButton(setting_initializeno, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertAC.show();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
        ivBackgroundColor.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
        ivLifeColor.setBackgroundColor(Color.HSVToColor(globals.fltLifeColor));
        ivPoisonColor.setBackgroundColor(Color.HSVToColor(globals.fltPoisonColor));
        ivGeneralColor.setBackgroundColor(Color.HSVToColor(globals.fltGeneralColor));
    }

    public void onClickLeft(View view) {
        globals.justification = globals.just_left;
        ivPositionLeft.setImageBitmap(bmSelectedJustLeft);
        ivPositionRight.setImageBitmap(bmJustRight);
    }

    public void onClickRight(View view) {
        globals.justification = globals.just_right;
        ivPositionLeft.setImageBitmap(bmJustLeft);
        ivPositionRight.setImageBitmap(bmSelectedJustRight);
    }
}
