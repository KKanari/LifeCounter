package application.example.kms2.mtglifecounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by KMS2 on 2016/03/02.
 */
public class Memo extends AppCompatActivity {
    EditText etMemo;
    Globals globals;
    RelativeLayout rlBackground;

    String memo_accheck;
    String memo_acmessage;
    String memo_acyes;
    String memo_acno;
    String memo_acdelete;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        globals = (Globals) getApplication();
        Intent intent = getIntent();

        etMemo = (EditText) findViewById(R.id.areaMemo);
        etMemo.setText(globals.strMemo);

        rlBackground = (RelativeLayout) findViewById(R.id.rlMemoBackground);
        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));

        memo_accheck = getString(R.string.memo_accheck);
        memo_acmessage = getString(R.string.memo_acmessage);
        memo_acyes = getString(R.string.memo_acyes);
        memo_acno = getString(R.string.memo_acno);
        memo_acdelete = getString(R.string.memo_acdelete);
    }

    public void clickAllClear(View view) {
        AlertDialog.Builder alertAC = new AlertDialog.Builder(this);
        alertAC.setTitle(memo_accheck);
        alertAC.setMessage(memo_acmessage);
        alertAC.setPositiveButton(memo_acyes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                globals.strMemo = "";
                etMemo.setText(globals.strMemo);
                Toast.makeText(Memo.this, memo_acdelete, Toast.LENGTH_SHORT).show();
            }
        });
        alertAC.setNegativeButton(memo_acno, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertAC.show();
    }

    protected void onDestroy() {
        super.onDestroy();

        SpannableStringBuilder ssbMemo = (SpannableStringBuilder) etMemo.getText();
        globals.strMemo = ssbMemo.toString();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        rlBackground.setBackgroundColor(Color.HSVToColor(globals.fltBackgroundColor));
    }
}
