package com.tpo.sparovcek.sparovcek;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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

    private String test_string = "hue hue hue";
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
                if (xDiff > 300 || yDiff > 250) {
                    if (xDiff > yDiff) { // če je razlika v x osi večje, gre za horizontaln premik
                        if (x1 < x2) { //levo

                        } else { //desno
                            Intent i = new Intent(UserSettingsActivity.this, OverviewGraphActivity.class);
                            startActivity(i);
                        }
                    } else { // drugače je v y osi večja in gre za vertikaln
                        if (y1 > y2) { //dol
                            Intent i = new Intent(UserSettingsActivity.this, UserHistoryActivity.class);
                            startActivity(i);
                        }
                    }
                }
                break;
        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        final TextView spr = (TextView) findViewById(R.id.spremenigeslot);
        // izpis
        //test.setText(izpisi());

        //Odjava
        Button odjava = (Button) findViewById(R.id.odjava);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
                startActivity(new Intent(UserSettingsActivity.this, MainActivity.class));
            }
        });

    }
    //Spremeni geslo + alert
    public void spremenigeslo(View v){
        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setTitle("Potrditev");
        a.setMessage("Res zelite spremeniti geslo?");
        a.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ime = loadData().split("#")[0].replaceAll("\\s+","");
                String geslo = loadData().split("#")[1].replaceAll("\\s+","");
                final TextView spr = (TextView) findViewById(R.id.spremenigeslot);
                String novogeslo = spr.getText().toString();
                saveData(ime + "#" + novogeslo);
            }
        });

        a.setNegativeButton("NE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        a.create().show();
    }
    public void ponastavi(View v){
        AlertDialog.Builder a = new AlertDialog.Builder(this);
        a.setTitle("Potrditev");
        a.setMessage("Res zelite ponastaviti pb?");
        a.setPositiveButton("DA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bazahelper bhelper = new bazahelper(getBaseContext());
                SQLiteDatabase bd = bhelper.getReadableDatabase();
                bhelper.deleteAll(bd);
                kategorije("");
                finish();
                startActivity(getIntent());
            }
        });

        a.setNegativeButton("NE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        a.create().show();
    }
    void saveData(String s) {
        try {
            //spuci
            //s = "";
            //s = loadData() + " " + s;
            File file = new File(getFilesDir(), "neki.txt");
            file.createNewFile(); // if file already exists will do nothing
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(s.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void kategorije(String s) {
        try {
            //spuci
            //s = "";
            //s = loadData() + " " + s;
            File file = new File(getFilesDir(), "kategorije.txt");
            file.createNewFile(); // if file already exists will do nothing
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(s.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String loadData() {
        try {
            File file = new File(getFilesDir(), "neki.txt");
            file.createNewFile(); // if file already exists will do nothing
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String input;
            test_string = "";
            while ((input = br.readLine()) != null)
                test_string += input + "\n";
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test_string;
    }
    String izpisi(){
        bazahelper bhelper = new bazahelper(this);
        SQLiteDatabase bd = bhelper.getReadableDatabase();
        String[] p = {baza.pb._ID, baza.pb.COLUMN_NAME_TITLE};
        String selectQuery = "SELECT  * FROM " + baza.pb.TABLE_NAME;
        Cursor c = bd.rawQuery(selectQuery, null);
        List i = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                i.add(c.getString(1));
            }while (c.moveToNext());
        }
        c.close();
        String aaa = "";
        for(int j = 0; j < i.size(); j++){
            aaa += i.get(j).toString() + "\n";
        }
        return aaa;
    }
}
