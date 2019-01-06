package com.tpo.sparovcek.sparovcek;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

public class Main2Activity extends AppCompatActivity {
    private String test_string = "hue hue hue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final TextView test = (TextView) findViewById(R.id.test);
        final TextView dodajv = (TextView) findViewById(R.id.dodajvnos);
        final TextView spr = (TextView) findViewById(R.id.spremenigeslot);
        //test.setText(loadData());

        //Izpis
        test.setText(izpisi());

        //Odjava
        Button odjava = (Button) findViewById(R.id.odjava);
        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
                startActivity(new Intent(Main2Activity.this, MainActivity.class));
            }
        });
        //Dodaj vnos
        Button dodajvnos = (Button) findViewById(R.id.dodajvnosbtn);
        dodajvnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodaj(dodajv.getText().toString());
                finish();
                startActivity(getIntent());
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
