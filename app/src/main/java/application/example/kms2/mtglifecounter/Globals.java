package application.example.kms2.mtglifecounter;

import android.app.Application;

/**
 * Created by KMS2 on 2016/03/02.
 */
public class Globals extends Application {
    short countAsHistory = 0;
    short countBsHistory = 0;

    String strMemo = "";

    short[] historyAsLife = new short[500];
    short[] historyBsLife = new short[500];
    short[] historyAsChange = new short[500];
    short[] historyBsChange = new short[500];

    int selected_change_color = 0;
    int setting_change_background = 0;
    int setting_change_lifecounter = 1;
    int setting_change_poisoncounter = 2;
    int setting_change_generaldamage = 3;

    final float INITIAL_BACKGROUNDHUE = 0;
    final float INITIAL_BACKGROUNDSATURATION = 0;
    final float INITIAL_BACKGROUNDVALUE = 1;
    final float INITIAL_LIFEHUE = 0;
    final float INITIAL_LIFESATURATION = 0;
    final float INITIAL_LIFEVALUE = 0.39f;
    final float INITIAL_POISONHUE = 264;
    final float INITIAL_POISONSATURATION = 0.5f;
    final float INITIAL_POISONVALUE = 0.8f;
    final float INITIAL_GENERALHUE = 47;
    final float INITIAL_GENERALSATURATION = 1;
    final float INITIAL_GENERALVALUE = 1;
    float backgroundHue = INITIAL_BACKGROUNDHUE;
    float backgroundSaturation = INITIAL_BACKGROUNDSATURATION;
    float backgroundValue = INITIAL_BACKGROUNDVALUE;
    float lifeHue = INITIAL_LIFEHUE;
    float lifeSaturation = INITIAL_LIFESATURATION;
    float lifeValue = INITIAL_LIFEVALUE;
    float poisonHue = INITIAL_POISONHUE;
    float poisonSaturation = INITIAL_POISONSATURATION;
    float poisonValue = INITIAL_POISONVALUE;
    float generalHue = INITIAL_GENERALHUE;
    float generalSaturation = INITIAL_GENERALSATURATION;
    float generalValue = INITIAL_GENERALVALUE;

    float[] fltBackgroundColor = { backgroundHue, backgroundSaturation, backgroundValue};
    float[] fltLifeColor = { lifeHue, lifeSaturation, lifeValue};
    float[] fltPoisonColor = { poisonHue, poisonSaturation,poisonValue};
    float[] fltGeneralColor = { generalHue, generalSaturation, generalValue};

    final short just_left = 0;
    final short just_right = 1;
    short justification = just_left;
}