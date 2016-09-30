package application.example.kms2.mtglifecounter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by KMS2 on 2016/03/02.
 */
public class History extends AppCompatActivity {
    String history_iv;

   String strAsHistory;
   String strBsHistory;

    TextView tvAsHistory;
    TextView tvBsHistory;
    LinearLayout llBackground;

    Globals globals;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globals = (Globals) this.getApplication();

        Intent intent = getIntent();

        if(globals.justification == globals.just_left) {
            history_iv = getString(R.string.history_iv);
            strAsHistory = "  20:" + String.format("%4s", history_iv) + "\r\n";
            strBsHistory = "  20:" + String.format("%4s", history_iv) + "\r\n";
        }else{
            history_iv = getString(R.string.history_iv);
            strAsHistory = "20 : " + history_iv + "\r\n";
            strBsHistory = "20 : " + history_iv + "\r\n";
        }

        tvAsHistory = (TextView) findViewById(R.id.asTextHistory);
        tvBsHistory = (TextView) findViewById(R.id.bsTextHistory);

        llBackground = (LinearLayout) findViewById(R.id.llHistoryBackground);
        llBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        tvAsHistory.setTypeface(Typeface.MONOSPACE);
        tvBsHistory.setTypeface(Typeface.MONOSPACE);

        if(globals.justification == globals.just_left) {
            for (int i = 0; i < globals.countAsHistory; i++) {
                if (globals.historyAsChange[i] > 0) {
                    strAsHistory += String.format("%4s", globals.historyAsLife[i]) + ":" + String.format("%4s", "+" + globals.historyAsChange[i]) + "\r\n";
                } else {
                    strAsHistory += String.format("%4s", globals.historyAsLife[i]) + ":" + String.format("%4s", globals.historyAsChange[i]) + "\r\n";
                }
            }

            for (int i = 0; i < globals.countBsHistory; i++) {
                if (globals.historyBsChange[i] > 0) {
                    strBsHistory += String.format("%4s", globals.historyBsLife[i]) + ":" + String.format("%4s", "+" + globals.historyBsChange[i]) + "\r\n";
                } else {
                    strBsHistory += String.format("%4s", globals.historyBsLife[i]) + ":" + String.format("%4s", globals.historyBsChange[i]) + "\r\n";
                }
            }
        }else{
            for (int i = 0; i < globals.countAsHistory; i++) {
                if (globals.historyAsChange[i] > 0) {
                    strAsHistory += globals.historyAsLife[i] + " : +" + globals.historyAsChange[i] + "\r\n";
                } else {
                    strAsHistory += globals.historyAsLife[i] + " : " + globals.historyAsChange[i] + "\r\n";
                }
            }

            for (int i = 0; i < globals.countBsHistory; i++) {
                if (globals.historyBsChange[i] > 0) {
                    strBsHistory += globals.historyBsLife[i] + " : +" + globals.historyBsChange[i] + "\r\n";
                } else {
                    strBsHistory += globals.historyBsLife[i] + " : " + globals.historyBsChange[i] + "\r\n";
                }
            }
        }

        tvAsHistory.setText(strAsHistory);
        tvBsHistory.setText(strBsHistory);
    }
}
