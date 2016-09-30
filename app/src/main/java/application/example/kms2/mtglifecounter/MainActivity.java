package application.example.kms2.mtglifecounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {
    //定数宣言
    final short INITIAL_LIFE = 20;      //ライフ変数の初期値
    final short INITIAL_POISON = 0;     //毒カウンターの初期値
    final short INITIAL_GENERAL = 0;    //統率者ダメージの初期値
    final short INITIAL_CHANGE = 0;     //ライフ変動値の初期値
    final short HISTORY_SELECTED = 0;   //メニュー用の定数(履歴)
    final short MEMO_SELECTED = 1;      //メニュー用の定数(メモ)
    final short DICE_SELECTED = 2;      //メニュー用の定数(ダイス)
    final short COIN_SELECTED = 3;      //メニュー用の定数(コイン)
    final short MANUAL_SELECTED = 4;    //メニュー用の定数(マニュアル)
    final short RESET_SELECTED = 5;    //メニュー用の定数(リセット)
    final short OPTION_SELECTED = 6;    //メニュー用の定数(オプション)
    final short LIFE_SELECTED = 0;      //カウンターのモード管理用(ライフ)
    final short POISON_SELECTED = 1;    //カウンターのモード管理用(毒カウンター)
    final short GENERAL_SELECTED = 2;   //カウンターのモード管理用(統率者ダメージ)
    final short TIMER_NOT_RUN = 0;      //処理が実行中か判定用
    final short TIMER_RUN = 1;          //処理が実行中か判定用
    final short TEXTSIZE_BIG = 100;     //テキストサイズ100
    final short TEXTSIZE_SMALL = 48;    //テキストサイズ48
    final short TIME_DELAYED = 10;      //繰り返し処理用の定数
    final short TIME_CHECK = 1000;      //繰り返し処理の判定用の定数

    final short MAX_LIFE = 999;
    final short MIN_LIFE = -99;
    final short MAX_POISON = 99;
    final short MIN_POISON = 0;
    final short MAX_GENERAL = 99;
    final short MIN_GENERAL = 0;

    //変数宣言
    short countAsLife = INITIAL_LIFE;           //Aのライフ
    short countBsLife = INITIAL_LIFE;           //Bのライフ
    short changedAsLife = INITIAL_CHANGE;       //Aのライフ変動値
    short changedBsLife = INITIAL_CHANGE;       //Bのライフ変動値
    short countAsPoison = INITIAL_POISON;       //Aの毒カウンター
    short countBsPoison = INITIAL_POISON;       //Bの毒カウンター
    short changedAsPoison = INITIAL_CHANGE;     //Aの毒カウンター変動値
    short changedBsPoison = INITIAL_CHANGE;     //Bの毒カウンター変動値
    short countAsGeneral = INITIAL_GENERAL;     //Aの統率者ダメージ
    short countBsGeneral = INITIAL_GENERAL;     //Bの統率者ダメージ
    short changedAsGeneral = INITIAL_CHANGE;    //Aの統率者ダメージ変動値
    short changedBsGeneral = INITIAL_CHANGE;    //Bの統率者ダメージ変動値
    short flagAsMode = LIFE_SELECTED;           //Aの選択されているカウンター
    short flagBsMode = LIFE_SELECTED;           //Bの選択されているカウンター
    short flagAsRun = TIMER_NOT_RUN;            //Aの処理が実行されているか判定
    short flagBsRun = TIMER_NOT_RUN;            //Bの処理が実行されているか判定
    //    long timeNow = 0;                               //現在時間
//    long timePast = 0;                              //過去時間
    long timeNowA = 0;
    long timeNowB = 0;
    long timePastA = 0;
    long timePastB = 0;


    TextView tvAsLife;              //Aのライフ表示領域
    TextView tvAsChangedLife;       //Aのライフ変動値表示領域
    TextView tvAsPoison;            //Aの毒カウンター表示領域
    TextView tvAsChangedPoison;     //Aの毒カウンター変動値表示領域
    TextView tvAsGeneral;           //Aの統率者ダメージ表示領域
    TextView tvAsChangedGeneral;    //Aの統率者ダメージ変動値表示領域
    TextView tvBsLife;              //Bのライフ表示領域
    TextView tvBsChangedLife;       //Bのライフ変動値表示領域
    TextView tvBsPoison;            //Bの毒カウンター表示領域
    TextView tvBsChangedPoison;     //Bの毒カウンター変動値表示領域
    TextView tvBsGeneral;           //Bの統率者ダメージ表示領域
    TextView tvBsChangedGeneral;    //Bの統率者ダメージ変動値表示領域
    RelativeLayout rlBackground;    //背景

    private Handler handler = new Handler();
    private Runnable updateAsTimer;
    private Runnable updateBsTimer;

    Globals globals;

    String menu_history;
    String menu_memo;
    String menu_dice;
    String menu_coin;
    String menu_manual;
    String menu_reset;
    String menu_option;
    String main_resetcheck;
    String main_resetmessage;
    String main_reset;
    String main_resetyes;
    String main_resetno;
    String main_backTitle;
    String main_backMessage;
    String main_backYes;
    String main_backNo;

    //起動時処理
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globals = (Globals) getApplication();

        tvAsLife = (TextView) findViewById(R.id.dispAsLife);
        tvAsChangedLife = (TextView) findViewById(R.id.dispAsChangedLife);
        tvAsPoison = (TextView) findViewById(R.id.dispAsPoison);
        tvAsChangedPoison = (TextView) findViewById(R.id.dispAsChangedPoison);
        tvAsGeneral = (TextView) findViewById(R.id.dispAsGeneral);
        tvAsChangedGeneral = (TextView) findViewById(R.id.dispAsChangedGeneral);

        tvBsLife = (TextView) findViewById(R.id.dispBsLife);
        tvBsChangedLife = (TextView) findViewById(R.id.dispBsChangedLife);
        tvBsPoison = (TextView) findViewById(R.id.dispBsPoison);
        tvBsChangedPoison = (TextView) findViewById(R.id.dispBsChangedPoison);
        tvBsGeneral = (TextView) findViewById(R.id.dispBsGeneral);
        tvBsChangedGeneral = (TextView) findViewById(R.id.dispBsChangedGeneral);

        rlBackground = (RelativeLayout) findViewById(R.id.rlMainBackground);

        tvAsLife.setText(Integer.toString(countAsLife));
        tvAsPoison.setText(Integer.toString(countAsPoison));
        tvAsGeneral.setText(Integer.toString(countAsGeneral));
        tvBsLife.setText(Integer.toString(countBsLife));
        tvBsPoison.setText(Integer.toString(countBsPoison));
        tvBsGeneral.setText(Integer.toString(countBsGeneral));

        //書体の変更
        tvAsLife.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvBsLife.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvAsPoison.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvBsPoison.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvAsGeneral.setTypeface(Typeface.SERIF, Typeface.BOLD);
        tvBsGeneral.setTypeface(Typeface.SERIF, Typeface.BOLD);

        //文字色の変更
        tvAsLife.setTextColor(Color.HSVToColor(globals.fltLifeColor));
        tvBsLife.setTextColor(Color.HSVToColor(globals.fltLifeColor));
        tvAsPoison.setTextColor(Color.HSVToColor(globals.fltPoisonColor));
        tvBsPoison.setTextColor(Color.HSVToColor(globals.fltPoisonColor));
        tvAsGeneral.setTextColor(Color.HSVToColor(globals.fltGeneralColor));
        tvBsGeneral.setTextColor(Color.HSVToColor(globals.fltGeneralColor));

        //時間の取得
        timePastA = System.currentTimeMillis();
        timePastB = System.currentTimeMillis();

        //string.xmlに対応させる
        menu_history = getString(R.string.menu_history);
        menu_memo = getString(R.string.menu_memo);
        menu_dice = getString(R.string.menu_dice);
        menu_coin = getString(R.string.menu_coin);
        menu_manual = getString(R.string.menu_manual);
        menu_reset = getString(R.string.menu_reset);
        menu_option = getString(R.string.menu_option);
        main_resetcheck = getString(R.string.main_resetcheck);
        main_resetmessage = getString(R.string.main_resetmessage);
        main_reset = getString(R.string.main_reset);
        main_resetyes = getString(R.string.memo_acyes);
        main_resetno = getString(R.string.memo_acno);
        main_backTitle = getString(R.string.main_backTitle);
        main_backMessage = getString(R.string.main_backMessage);
        main_backYes = getString(R.string.main_backYes);
        main_backNo = getString(R.string.main_backNo);

        AdView mAdView = (AdView) findViewById(R.id.adView);
//        mAdView.setAdSize(AdSize.SMART_BANNER);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        Point abc = getViewSize();
//        Toast.makeText(this, abc.toString(),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        RelativeLayout r = (RelativeLayout) findViewById(R.id.linear);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        setDisp();
    }

    //メニューボタン追加処理
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, HISTORY_SELECTED, 0, menu_history);
        menu.add(0, MEMO_SELECTED, 0, menu_memo);
        menu.add(0, DICE_SELECTED, 0, menu_dice);
        menu.add(0, COIN_SELECTED, 0, menu_coin);
        menu.add(0, MANUAL_SELECTED, 0, menu_manual);
        menu.add(0, OPTION_SELECTED, 0, menu_option);
        menu.add(0, RESET_SELECTED, 0, menu_reset);
        return true;
    }

    //各メニューボタンのクリック処理
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case HISTORY_SELECTED:
                Intent intentHistory = new Intent(this, History.class);
                startActivity(intentHistory);
                break;
            case MEMO_SELECTED:
                Intent intentMemo = new Intent(this, Memo.class);
                startActivity(intentMemo);
                break;
            case DICE_SELECTED:
                Intent intentDice = new Intent(this, Dice.class);
                startActivity(intentDice);
                break;
            case COIN_SELECTED:
                Intent intentCoin = new Intent(this, Coin.class);
                startActivity(intentCoin);
                break;
            case MANUAL_SELECTED:
                Intent intentManual = new Intent(this, Manual.class);
                startActivity(intentManual);
                break;
            case RESET_SELECTED:
                AlertDialog.Builder alertAC = new AlertDialog.Builder(this);
                alertAC.setTitle(main_resetcheck);
                alertAC.setMessage(main_resetmessage);
                alertAC.setPositiveButton(main_resetyes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        countAsLife = INITIAL_LIFE;
                        countBsLife = INITIAL_LIFE;
                        changedAsLife = INITIAL_CHANGE;
                        changedBsLife = INITIAL_CHANGE;
                        countAsPoison = INITIAL_POISON;
                        countBsPoison = INITIAL_POISON;
                        changedAsPoison = INITIAL_CHANGE;
                        changedBsPoison = INITIAL_CHANGE;
                        countAsGeneral = INITIAL_GENERAL;
                        countBsGeneral = INITIAL_GENERAL;
                        changedAsGeneral = INITIAL_CHANGE;
                        changedBsGeneral = INITIAL_CHANGE;
                        flagAsMode = LIFE_SELECTED;
                        flagBsMode = LIFE_SELECTED;
                        flagAsRun = TIMER_NOT_RUN;
                        flagBsRun = TIMER_NOT_RUN;
                        globals.countAsHistory = 0;
                        globals.countBsHistory = 0;
                        globals.historyAsLife = new short[500];
                        globals.historyAsChange = new short[500];
                        globals.historyBsLife = new short[500];
                        globals.historyBsChange = new short[500];
                        globals.strMemo = "";
                        setDisp();
                        tvAsLife.setTextSize(TEXTSIZE_BIG);
                        tvAsPoison.setTextSize(TEXTSIZE_SMALL);
                        tvAsGeneral.setTextSize(TEXTSIZE_SMALL);
                        tvBsLife.setTextSize(TEXTSIZE_BIG);
                        tvBsPoison.setTextSize(TEXTSIZE_SMALL);
                        tvBsGeneral.setTextSize(TEXTSIZE_SMALL);
                        Toast.makeText(MainActivity.this, main_reset, Toast.LENGTH_SHORT).show();
                    }
                });
                alertAC.setNegativeButton(main_resetno, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertAC.show();
                break;
            case OPTION_SELECTED:
                Intent intentSetAct = new Intent(this, Setting_Main.class);
                startActivity(intentSetAct);
                break;
        }
        return false;
    }

    //プレイヤーA側のライフクリック処理
    public void clickAsLife(View view) {
        tvAsChangedPoison.setText("");
        tvAsChangedGeneral.setText("");
        flagAsMode = LIFE_SELECTED;
        tvAsLife.setTextSize(TEXTSIZE_BIG);
        tvAsPoison.setTextSize(TEXTSIZE_SMALL);
        tvAsGeneral.setTextSize(TEXTSIZE_SMALL);
    }

    //プレイヤーA側の毒ダメージクリック処理
    public void clickAsPoison(View view) {
        tvAsChangedLife.setText("");
        tvAsChangedGeneral.setText("");
        flagAsMode = POISON_SELECTED;
        tvAsLife.setTextSize(TEXTSIZE_SMALL);
        tvAsPoison.setTextSize(TEXTSIZE_BIG);
        tvAsGeneral.setTextSize(TEXTSIZE_SMALL);
    }

    //プレイヤーA側の統率者ダメージクリック処理
    public void clickAsGeneral(View view) {
        tvAsChangedLife.setText("");
        tvAsChangedPoison.setText("");
        flagAsMode = GENERAL_SELECTED;
        tvAsLife.setTextSize(TEXTSIZE_SMALL);
        tvAsPoison.setTextSize(TEXTSIZE_SMALL);
        tvAsGeneral.setTextSize(TEXTSIZE_BIG);
    }

    //プレイヤーB側のライフクリック処理
    public void clickBsLife(View view) {
        tvBsChangedPoison.setText("");
        tvBsChangedGeneral.setText("");
        flagBsMode = LIFE_SELECTED;
        tvBsLife.setTextSize(TEXTSIZE_BIG);
        tvBsPoison.setTextSize(TEXTSIZE_SMALL);
        tvBsGeneral.setTextSize(TEXTSIZE_SMALL);
    }

    //プレイヤーB側の毒ダメージクリック処理
    public void clickBsPoison(View view) {
        tvBsChangedLife.setText("");
        tvBsChangedGeneral.setText("");
        flagBsMode = POISON_SELECTED;
        tvBsLife.setTextSize(TEXTSIZE_SMALL);
        tvBsPoison.setTextSize(TEXTSIZE_BIG);
        tvBsGeneral.setTextSize(TEXTSIZE_SMALL);
    }

    //プレイヤーB側の統率者ダメージクリック処理
    public void clickBsGeneral(View view) {
        tvBsChangedLife.setText("");
        tvBsChangedPoison.setText("");
        flagBsMode = GENERAL_SELECTED;
        tvBsLife.setTextSize(TEXTSIZE_SMALL);
        tvBsPoison.setTextSize(TEXTSIZE_SMALL);
        tvBsGeneral.setTextSize(TEXTSIZE_BIG);
    }

    //プレイヤーA側の＋ボタン処理
    public void clickAsPlus(View view) {
        timeNowA = System.currentTimeMillis();
        if (timeNowA - timePastA <= TIME_CHECK) {
            switch (flagAsMode) {
                case LIFE_SELECTED:
                    if (countAsLife >= MAX_LIFE) {
                        countAsLife = MAX_LIFE;
                    } else {
                        countAsLife++;
                        changedAsLife++;
                    }
                    break;
                case POISON_SELECTED:
                    if (countAsPoison >= MAX_POISON) {
                        countAsPoison = MAX_POISON;
                    } else {
                        countAsPoison++;
                        changedAsPoison++;
                    }
                    break;
                case GENERAL_SELECTED:
                    if (countAsGeneral >= MAX_GENERAL) {
                        countAsGeneral = MAX_GENERAL;
                    } else {
                        countAsGeneral++;
                        changedAsGeneral++;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (flagAsMode) {
                case LIFE_SELECTED:
                    changedAsLife = 0;
                    if (countAsLife >= MAX_LIFE) {
                        countAsLife = MAX_LIFE;
                    } else {
                        countAsLife++;
                        changedAsLife++;
                    }
                    break;
                case POISON_SELECTED:
                    changedAsPoison = 0;
                    if (countAsPoison >= MAX_POISON) {
                        countAsPoison = MAX_POISON;
                    } else {
                        countAsPoison++;
                        changedAsPoison++;
                    }
                    break;
                case GENERAL_SELECTED:
                    changedAsGeneral = 0;
                    if (countAsGeneral >= MAX_GENERAL) {
                        countAsGeneral = MAX_GENERAL;
                    } else {
                        countAsGeneral++;
                        changedAsGeneral++;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        dispAsTextView();

        timePastA = timeNowA;

        if (flagAsRun == TIMER_NOT_RUN) {
            flagAsRun = TIMER_RUN;
            updateAsTimer();
            handler.postDelayed(updateAsTimer, TIME_DELAYED);
        }

    }

    //プレイヤーA側の－ボタン処理
    public void clickAsMinus(View view) {
        timeNowA = System.currentTimeMillis();
        if (timeNowA - timePastA <= TIME_CHECK) {
            switch (flagAsMode) {
                case LIFE_SELECTED:
                    if (countAsLife <= MIN_LIFE) {
                        countAsLife = MIN_LIFE;
                    } else {
                        countAsLife--;
                        changedAsLife--;
                    }
                    break;
                case POISON_SELECTED:
                    if (countAsPoison <= MIN_POISON) {
                        countAsPoison = MIN_POISON;
                    } else {
                        countAsPoison--;
                        changedAsPoison--;
                    }
                    break;
                case GENERAL_SELECTED:
                    if (countAsGeneral <= MIN_GENERAL) {
                        countAsGeneral = MIN_GENERAL;
                    } else {
                        countAsGeneral--;
                        changedAsGeneral--;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (flagAsMode) {
                case LIFE_SELECTED:
                    changedAsLife = 0;
                    if (countAsLife <= MIN_LIFE) {
                        countAsLife = MIN_LIFE;
                    } else {
                        countAsLife--;
                        changedAsLife--;
                    }
                    break;
                case POISON_SELECTED:
                    changedAsPoison = 0;
                    if (countAsPoison <= MIN_POISON) {
                        countAsPoison = MIN_POISON;
                    } else {
                        countAsPoison--;
                        changedAsPoison--;
                    }
                    break;
                case GENERAL_SELECTED:
                    changedAsGeneral = 0;
                    if (countAsGeneral <= MIN_GENERAL) {
                        countAsGeneral = MIN_GENERAL;
                    } else {
                        countAsGeneral--;
                        changedAsGeneral--;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        dispAsTextView();

        timePastA = timeNowA;

        if (flagAsRun == TIMER_NOT_RUN) {
            flagAsRun = TIMER_RUN;
            updateAsTimer();
            handler.postDelayed(updateAsTimer, TIME_DELAYED);
        }
    }

    //プレイヤーB側の＋ボタン処理
    public void clickBsPlus(View view) {
        timeNowB = System.currentTimeMillis();
        if (timeNowB - timePastB <= TIME_CHECK) {
            switch (flagBsMode) {
                case LIFE_SELECTED:
                    if (countBsLife >= MAX_LIFE) {
                        countBsLife = MAX_LIFE;
                    } else {
                        countBsLife++;
                        changedBsLife++;
                    }
                    break;
                case POISON_SELECTED:
                    if (countBsPoison >= MAX_POISON) {
                        countBsPoison = MAX_POISON;
                    } else {
                        countBsPoison++;
                        changedBsPoison++;
                    }
                    break;
                case GENERAL_SELECTED:
                    if (countBsGeneral >= MAX_GENERAL) {
                        countBsGeneral = MAX_GENERAL;
                    } else {
                        countBsGeneral++;
                        changedBsGeneral++;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (flagBsMode) {
                case LIFE_SELECTED:
                    changedBsLife = 0;
                    if (countBsLife >= MAX_LIFE) {
                        countBsLife = MAX_LIFE;
                    } else {
                        countBsLife++;
                        changedBsLife++;
                    }
                    break;
                case POISON_SELECTED:
                    changedBsPoison = 0;
                    if (countBsPoison >= MAX_POISON) {
                        countBsLife = MAX_POISON;
                    } else {
                        countBsPoison++;
                        changedBsPoison++;
                    }
                    break;
                case GENERAL_SELECTED:
                    changedBsGeneral = 0;
                    if (countBsGeneral >= MAX_GENERAL) {
                        countBsGeneral = MAX_GENERAL;
                    } else {
                        countBsGeneral++;
                        changedBsGeneral++;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        dispBsTextView();

        timePastB = timeNowB;

        if (flagBsRun == TIMER_NOT_RUN) {
            flagBsRun = TIMER_RUN;
            updateBsTimer();
            handler.postDelayed(updateBsTimer, TIME_DELAYED);
        }

    }

    //プレイヤーB側の－ボタン処理
    public void clickBsMinus(View view) {
        timeNowB = System.currentTimeMillis();
        if (timeNowB - timePastB <= TIME_CHECK) {
            switch (flagBsMode) {
                case LIFE_SELECTED:
                    if (countBsLife <= MIN_LIFE) {
                        countBsLife = MIN_LIFE;
                    } else {
                        countBsLife--;
                        changedBsLife--;
                    }
                    break;
                case POISON_SELECTED:
                    if (countBsPoison <= MIN_POISON) {
                        countBsPoison = MIN_POISON;
                    } else {
                        countBsPoison--;
                        changedBsPoison--;
                    }
                    break;
                case GENERAL_SELECTED:
                    if (countBsGeneral <= MIN_GENERAL) {
                        countBsGeneral = MIN_GENERAL;
                    } else {
                        countBsGeneral--;
                        changedBsGeneral--;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (flagBsMode) {
                case LIFE_SELECTED:
                    changedBsLife = 0;
                    if (countBsLife <= MIN_LIFE) {
                        countBsLife = MIN_LIFE;
                    } else {
                        countBsLife--;
                        changedBsLife--;
                    }
                    break;
                case POISON_SELECTED:
                    changedBsPoison = 0;
                    if (countBsPoison <= MIN_POISON) {
                        countBsPoison = MIN_POISON;
                    } else {
                        countBsPoison--;
                        changedBsPoison--;
                    }
                    break;
                case GENERAL_SELECTED:
                    changedBsGeneral = 0;
                    if (countBsGeneral <= MIN_GENERAL) {
                        countBsGeneral = MIN_GENERAL;
                    } else {
                        countBsGeneral--;
                        changedBsGeneral--;
                    }
                    break;
                default:
                    Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        dispBsTextView();

        timePastB = timeNowB;

        if (flagBsRun == TIMER_NOT_RUN) {
            flagBsRun = TIMER_RUN;
            updateBsTimer();
            handler.postDelayed(updateBsTimer, TIME_DELAYED);
        }
    }

    //プレイヤーA側の繰り返し処理
    public void updateAsTimer() {
        updateAsTimer = new Runnable() {
            @Override
            public void run() {

                timeNowA = System.currentTimeMillis();
                if (timeNowA - timePastA >= TIME_CHECK) {
                    switch (flagAsMode) {
                        case LIFE_SELECTED:
                            tvAsChangedLife.setText("");
                            globals.historyAsLife[globals.countAsHistory] = countAsLife;
                            globals.historyAsChange[globals.countAsHistory] = changedAsLife;
                            globals.countAsHistory++;
                            break;
                        case POISON_SELECTED:
                            tvAsChangedPoison.setText("");
                            break;
                        case GENERAL_SELECTED:
                            tvAsChangedGeneral.setText("");
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    handler.removeCallbacks(updateAsTimer);
                    flagAsRun = TIMER_NOT_RUN;
                } else {
//                    handler.removeCallbacks(updateTimer);
                    handler.postDelayed(updateAsTimer, TIME_DELAYED);
                }
            }
        };
    }

    //プレイヤーB側の繰り返し処理
    public void updateBsTimer() {
        updateBsTimer = new Runnable() {
            @Override
            public void run() {

                timeNowB = System.currentTimeMillis();
                if (timeNowB - timePastB >= TIME_CHECK) {
                    switch (flagBsMode) {
                        case LIFE_SELECTED:
                            tvBsChangedLife.setText("");
                            globals.historyBsLife[globals.countBsHistory] = countBsLife;
                            globals.historyBsChange[globals.countBsHistory] = changedBsLife;
                            globals.countBsHistory++;
                            break;
                        case POISON_SELECTED:
                            tvBsChangedPoison.setText("");
                            break;
                        case GENERAL_SELECTED:
                            tvBsChangedGeneral.setText("");
                            break;
                        default:
                            Toast.makeText(MainActivity.this, "ERROR!", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    handler.removeCallbacks(updateBsTimer);
                    flagBsRun = TIMER_NOT_RUN;
                } else {
//                    handler.removeCallbacks(updateTimer);
                    handler.postDelayed(updateBsTimer, TIME_DELAYED);
                }
            }
        };
    }

    //プレイヤーA側のTextViewへの表示処理
    public void dispAsTextView() {
        switch (flagAsMode) {
            case LIFE_SELECTED:
                tvAsLife.setText(Integer.toString(countAsLife));
                if (changedAsLife > 0) {
                    tvAsChangedLife.setText("+" + Integer.toString(changedAsLife));
                } else {
                    tvAsChangedLife.setText(Integer.toString(changedAsLife));
                }
                break;
            case POISON_SELECTED:
                tvAsPoison.setText(Integer.toString(countAsPoison));
                if (changedAsPoison > 0) {
                    tvAsChangedPoison.setText("+" + Integer.toString(changedAsPoison));
                } else {
                    tvAsChangedPoison.setText(Integer.toString(changedAsPoison));
                }
                break;
            case GENERAL_SELECTED:
                tvAsGeneral.setText(Integer.toString(countAsGeneral));
                if (changedAsGeneral > 0) {
                    tvAsChangedGeneral.setText("+" + Integer.toString(changedAsGeneral));
                } else {
                    tvAsChangedGeneral.setText(Integer.toString(changedAsGeneral));
                }
                break;
            default:
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //プレイヤーB側のTextViewへの表示処理
    public void dispBsTextView() {
        switch (flagBsMode) {
            case LIFE_SELECTED:
                tvBsLife.setText(Integer.toString(countBsLife));
                if (changedBsLife > 0) {
                    tvBsChangedLife.setText("+" + Integer.toString(changedBsLife));
                } else {
                    tvBsChangedLife.setText(Integer.toString(changedBsLife));
                }
                break;
            case POISON_SELECTED:
                tvBsPoison.setText(Integer.toString(countBsPoison));
                if (changedBsPoison > 0) {
                    tvBsChangedPoison.setText("+" + Integer.toString(changedBsPoison));
                } else {
                    tvBsChangedPoison.setText(Integer.toString(changedBsPoison));
                }
                break;
            case GENERAL_SELECTED:
                tvBsGeneral.setText(Integer.toString(countBsGeneral));
                if (changedBsGeneral > 0) {
                    tvBsChangedGeneral.setText("+" + Integer.toString(changedBsGeneral));
                } else {
                    tvBsChangedGeneral.setText(Integer.toString(changedBsGeneral));
                }
                break;
            default:
                Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AlertDialog.Builder alertAC = new AlertDialog.Builder(this);
            alertAC.setTitle(main_backTitle);
            alertAC.setMessage(main_backMessage);
            alertAC.setPositiveButton(main_backYes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertAC.setNegativeButton(main_backNo, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertAC.show();
            return true;
        }
        return false;
    }

    public static Point getViewSize(View View) {
        Point point = new Point(0, 0);
        point.set(View.getWidth(), View.getHeight());
        return point;
    }

    public void setDisp() {
        tvAsLife.setText(Short.toString(countAsLife));
        tvAsPoison.setText(Short.toString(countAsPoison));
        tvAsGeneral.setText(Short.toString(countAsGeneral));
        tvBsLife.setText(Short.toString(countBsLife));
        tvBsPoison.setText(Short.toString(countBsPoison));
        tvBsGeneral.setText(Short.toString(countBsGeneral));

        tvAsLife.setTextColor(Color.HSVToColor(globals.fltLifeColor));
        tvBsLife.setTextColor(Color.HSVToColor(globals.fltLifeColor));
        tvAsPoison.setTextColor(Color.HSVToColor(globals.fltPoisonColor));
        tvBsPoison.setTextColor(Color.HSVToColor(globals.fltPoisonColor));
        tvAsGeneral.setTextColor(Color.HSVToColor(globals.fltGeneralColor));
        tvBsGeneral.setTextColor(Color.HSVToColor(globals.fltGeneralColor));
    }
}