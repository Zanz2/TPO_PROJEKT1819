package com.tpo.sparovcek.sparovcek;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class UserSettingsActivity extends AppCompatActivity {


    float x1,x2,y1,y2;
    public boolean onTouchEvent(MotionEvent touchevent){
        super.onTouchEvent(touchevent);
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                float yDiff = Math.abs(y1-y2);
                float xDiff = Math.abs(x1-x2);
                if(xDiff>yDiff) { // če je razlika v x osi večje, gre za horizontaln premik
                    if (x1 < x2) { //levo

                    }else  { //desno
                        Intent i = new Intent(UserSettingsActivity.this, Main2Activity.class);
                        startActivity(i);
                    }
                }else{ // drugače je v y osi večja in gre za vertikaln
                    if (y1 > y2) { //dol
                        Intent i = new Intent(UserSettingsActivity.this, UserHistoryActivity.class);
                        startActivity(i);
                    }
                }
                break;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
