package application.example.kms2.mtglifecounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by KMS2 on 2016/05/13.
 */
public class Setting_Picker extends AppCompatActivity {
    Globals globals;

    int bmHeight;
    int bmWidth;
    int bmColor;
    int intColor;
    int statusBarHeight;
    int contentViewTop;
    int titleBarHeight;

    String setting_confirmcheck;
    String setting_confirmmessage;
    String setting_confirmyes;
    String setting_confirmno;

    final int PICKER_MARGIN = 100;

    float[] pvHSV = new float[3];

    ImageView ivHue;
    ImageView ivValue;
    ImageView ivSaturation;
    ImageView ivPreview;
    TextView tvCheck;

    RelativeLayout rlBackground;

    Bitmap bmHue;
    Bitmap bmValue;
    Bitmap bmSaturation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_picker);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globals = (Globals) getApplication();
        ivPreview = (ImageView) findViewById(R.id.ivPreview);

        tvCheck = (TextView) findViewById(R.id.textView);

        rlBackground = (RelativeLayout) findViewById(R.id.allLayout);

        setting_confirmcheck =getString(R.string.setting_confirmcheck);
        setting_confirmmessage =getString(R.string.setting_confirmmessage);
        setting_confirmyes =getString(R.string.setting_confirmyes);
        setting_confirmno =getString(R.string.setting_confirmno);

        if (globals.selected_change_color == globals.setting_change_background) {
            ivPreview.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
        } else if (globals.selected_change_color == globals.setting_change_lifecounter) {
            ivPreview.setBackgroundColor(Color.HSVToColor(globals.fltLifeColor));
        } else if (globals.selected_change_color == globals.setting_change_poisoncounter) {
            ivPreview.setBackgroundColor(Color.HSVToColor(globals.fltPoisonColor));
        } else if (globals.selected_change_color == globals.setting_change_generaldamage) {
            ivPreview.setBackgroundColor(Color.HSVToColor(globals.fltGeneralColor));
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        RelativeLayout relativelayout = (RelativeLayout) findViewById(R.id.allLayout);

        bmHeight = 60;
        bmWidth = 360;

        bmHue = Bitmap.createBitmap(bmWidth, bmHeight, Bitmap.Config.ARGB_8888);
        bmValue = Bitmap.createBitmap(bmWidth, bmHeight, Bitmap.Config.ARGB_8888);
        bmSaturation = Bitmap.createBitmap(bmWidth, bmHeight, Bitmap.Config.ARGB_8888);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        dispColorPicker();
        dispImageView();
    }

    public void dispColorPicker() {
        //色相
        for (int i = 0; i < 360; i++) {
            bmColor = Color.HSVToColor(new float[]{i, 1, 1});
            for (int j = 0; j < bmHeight; j++) {
                bmHue.setPixel(i, j, bmColor);
            }
        }

        //彩度
        for (int i = 0; i < 360; i++) {
            bmColor = Color.HSVToColor(new float[]{pvHSV[0], 0.0028f * i, pvHSV[2]});
            for (int j = 0; j < bmHeight; j++) {
                bmSaturation.setPixel(i, j, bmColor);
            }
        }

        //明度
        for (int i = 0; i < 360; i++) {
            bmColor = Color.HSVToColor(new float[]{pvHSV[0], pvHSV[1], 0.0028f * i});
            for (int j = 0; j < bmHeight; j++) {
                bmValue.setPixel(i, j, bmColor);
            }
        }
    }

    public void dispImageView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float widthRatio = 900f / (dm.widthPixels - PICKER_MARGIN);
        ivHue = (ImageView) findViewById(R.id.ivHue);
        ivHue.setImageBitmap(bmHue);
        ivSaturation = (ImageView) findViewById(R.id.ivSaturation);
        ivSaturation.setImageBitmap(bmSaturation);
        ivValue = (ImageView) findViewById(R.id.ivValue);
        ivValue.setImageBitmap(bmValue);
        ivHue.setScaleX(1 / widthRatio);
        ivSaturation.setScaleX(1 / widthRatio);
        ivValue.setScaleX(1 / widthRatio);
    }

    public int getTitleHeight() {
        Rect rect = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        statusBarHeight = rect.top;
        contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
        titleBarHeight = contentViewTop - statusBarHeight;
        return titleBarHeight;
    }

    //    タッチ座標取得
    public boolean onTouchEvent(MotionEvent event) {
        RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.allLayout);
        rLayout.setDrawingCacheEnabled(false);
        rLayout.setDrawingCacheEnabled(true);
        Bitmap bmColor = Bitmap.createBitmap(rLayout.getDrawingCache());
//        tvCheck.setText("X:" + (int) event.getX() + "  Y:" + (int) (event.getY() - getTitleHeight()));
        intColor = bmColor.getPixel((int) event.getX(), (int) event.getY() - getTitleHeight());
        if (intColor != Color.HSVToColor(globals.fltBackgroundColor)) {
            ivPreview.setBackgroundColor(intColor);
            Color.colorToHSV(intColor, pvHSV);
            dispColorPicker();
        }
        return false;
    }

    public void onConfirm(View view) {
        AlertDialog.Builder alertAC = new AlertDialog.Builder(this);
        alertAC.setTitle(setting_confirmcheck);
        alertAC.setMessage(setting_confirmmessage);
        alertAC.setPositiveButton(setting_confirmyes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (globals.selected_change_color == globals.setting_change_background) {
                    Color.colorToHSV(intColor, globals.fltBackgroundColor);
                } else if (globals.selected_change_color == globals.setting_change_lifecounter) {
                    Color.colorToHSV(intColor, globals.fltLifeColor);
                } else if (globals.selected_change_color == globals.setting_change_poisoncounter) {
                    Color.colorToHSV(intColor, globals.fltPoisonColor);
                } else if (globals.selected_change_color == globals.setting_change_generaldamage) {
                    Color.colorToHSV(intColor, globals.fltGeneralColor);
                }
                finish();
            }
        });
        alertAC.setNegativeButton(setting_confirmno, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertAC.show();
    }
}