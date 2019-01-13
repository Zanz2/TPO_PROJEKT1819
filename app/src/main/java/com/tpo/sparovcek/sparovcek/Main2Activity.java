package com.tpo.sparovcek.sparovcek;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ToggleButton;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class Main2Activity extends AppCompatActivity {
    private String test_string = "hue hue hue";
    public ArrayList<String> k = new ArrayList<String>();
    private static final String[] kategorije = new String[] {
            "Avto", "Trgovina", "Ostalo"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        String kat = loadData();
        for (int i = 0; i < kat.split("#").length; i++){
            try{
                if(!k.contains(kat.split("#")[i]) & !kat.split("#")[i].equals("")){
                    k.add(kat.split("#")[i]);
                }
            }catch (Exception e){}
        }


        k.add("Avto");
        k.add("položnice");
        k.add("hrana");
        k.add("zabava");

        final TextView naziv = findViewById(R.id.naziv);
        final TextView znesek = findViewById(R.id.znesek);
        final TextView napaka = findViewById(R.id.napaka);

        //autocomplete text za kategorije
        final AutoCompleteTextView m = findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, k);
        m.setAdapter(adapter);

        final ToggleButton tb = findViewById(R.id.toggleButton);
        //dodajv.setText(tb.getText());

        //datum
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy#HH:mm");
        final String currentDateandTime = sdf.format(new Date());
        //dodajv.setText(currentDateandTime);


        //Dodaj vnos
        Button dodajvnos = (Button) findViewById(R.id.dodajvnosbtn);
        dodajvnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!k.contains(m.getText())){
                    k.add(m.getText().toString());
                    saveData(loadData() + "#" + m.getText().toString());
                }
                if(!znesek.getText().toString().equals("") & !naziv.getText().toString().equals("") & !m.getText().toString().equals("")){
                    String vnos = "" + naziv.getText() + "#" + m.getText() + "#" + tb.getText() + "#" + znesek.getText() + "€#" + currentDateandTime;
                    dodaj(vnos);
                    //dodajv.setText(vnos);
                    finish();
                    Intent i = new Intent(Main2Activity.this, OverviewGraphActivity.class);
                    startActivity(i);
                    //startActivity(getIntent());
                }else {
                    napaka.setText("Napaka pri vnosu podatkov.");
                }

            }
        });
    }
    float x1,x2,y1,y2;
    public boolean onTouchEvent(MotionEvent touchevent){
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
                            Intent i = new Intent(Main2Activity.this, OverviewGraphActivity.class);
                            startActivity(i);
                        } else { //desno

                        }
                    } else { // drugače je v y osi večja in gre za vertikaln
                        if (y1 > y2) { //dol
                            Intent i = new Intent(Main2Activity.this, UserHistoryActivity.class);
                            startActivity(i);
                        }
                    }
                }
                break;
        }
        return false;
    }

    void saveData(String s) {
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
            File file = new File(getFilesDir(), "kategorije.txt");
            file.createNewFile(); // if file already exists will do nothing
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String input;
            test_string = "";
            while ((input = br.readLine()) != null)
                test_string += input;
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test_string;
    }
    void dodaj(String s) {
        bazahelper bhelper = new bazahelper(this);
        SQLiteDatabase db = bhelper.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(baza.pb.COLUMN_NAME_TITLE, s);
        long newRowId = db.insert(baza.pb.TABLE_NAME, null, v);
        db.close();
        bhelper.close();
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
