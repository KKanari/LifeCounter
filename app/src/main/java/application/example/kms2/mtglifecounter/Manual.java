package application.example.kms2.mtglifecounter;

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

/**
 * Created by KMS2 on 2016/03/02.
 */
public class Manual extends AppCompatActivity {
    //    final String MANUAL_1 = "黒：ライフカウンター\n紫：毒カウンター\n黄：統率者ダメージ\n" +
//             "＋：カウンターを増加\n－：カウンターを減少\nリセットはメニューボタンから行えます。";
//    final String MANUAL_2 = "ライフの変動の履歴が確認できます。\n履歴が増えてくるとスクロールできるようになります。";
//    final String MANUAL_3 = "メモを記録しておくことができます。\n全消去ボタンでメモを削除することができ、" +
//            "またメイン画面のリセットボタンでも消去することができます。";
//    final String MANUAL_4 = "ROLLボタンを押すとダイスが止まります。\nその後REROLLボタンを押すと再度ダイスが回り始めます。";
//    final String MANUAL_5 = "表か裏どちらかのコインを選択した後にFLIPボタンを押すとコインが投げられ、" +
//            "結果に応じて「Win」または「Lose」が表示されます。";
    Globals globals;

    short viewPage;
    ImageView ivManual;
    TextView tvManual;
    Button btnBack;
    Button btnNext;
    RelativeLayout rlBackground;

    Bitmap manual1;
    Bitmap manual2;
    Bitmap manual3;
    Bitmap manual4;
    Bitmap manual5;

    String manual_1;
    String manual_2;
    String manual_3;
    String manual_4;
    String manual_5;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        Resources r = getResources();
        globals = (Globals) getApplication();

        viewPage = 0;

        manual1 = BitmapFactory.decodeResource(r, R.drawable.manual_1);
        manual2 = BitmapFactory.decodeResource(r, R.drawable.manual_2);
        manual3 = BitmapFactory.decodeResource(r, R.drawable.manual_3);
        manual4 = BitmapFactory.decodeResource(r, R.drawable.manual_4);
        manual5 = BitmapFactory.decodeResource(r, R.drawable.manual_5);

        ivManual = (ImageView) findViewById(R.id.imgManual);
        tvManual = (TextView) findViewById(R.id.txtManual);
        btnBack = (Button) findViewById(R.id.buttonBack);
        btnNext = (Button) findViewById(R.id.buttonNext);

        rlBackground = (RelativeLayout) findViewById(R.id.rlManualBackground);
        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        manual_1 = getString(R.string.manual_1);
        manual_2 = getString(R.string.manual_2);
        manual_3 = getString(R.string.manual_3);
        manual_4 = getString(R.string.manual_4);
        manual_5 = getString(R.string.manual_5);

        ivManual.setImageBitmap(manual1);
        tvManual.setText(manual_1);
        btnBack.setEnabled(false);
    }

    public void clickBack(View view) {
        viewPage--;
        ivManual.destroyDrawingCache();
        switch (viewPage) {
            case 0:
                ivManual.setImageBitmap(manual1);
                btnBack.setEnabled(false);
                tvManual.setText(manual_1);
                break;
            case 1:
                ivManual.setImageBitmap(manual2);
                tvManual.setText(manual_2);
                break;
            case 2:
                ivManual.setImageBitmap(manual3);
                tvManual.setText(manual_3);
                break;
            case 3:
                ivManual.setImageBitmap(manual4);
                tvManual.setText(manual_4);
                btnNext.setEnabled(true);
                break;
        }
    }

    public void clickNext(View view) {
        viewPage++;
        ivManual.destroyDrawingCache();
        switch (viewPage) {
            case 1:
                ivManual.setImageBitmap(manual2);
                btnBack.setEnabled(true);
                tvManual.setText(manual_2);
                break;
            case 2:
                ivManual.setImageBitmap(manual3);
                tvManual.setText(manual_3);
                break;
            case 3:
                ivManual.setImageBitmap(manual4);
                tvManual.setText(manual_4);
                break;
            case 4:
                ivManual.setImageBitmap(manual5);
                btnNext.setEnabled(false);
                tvManual.setText(manual_5);
                break;
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
    }
}
