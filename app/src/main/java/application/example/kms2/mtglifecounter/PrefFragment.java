package application.example.kms2.mtglifecounter;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by KMS2 on 2016/04/07.
 */
public class PrefFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
