package com.tpo.sparovcek.sparovcek;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class OverviewGraphActivity extends AppCompatActivity {
    public String test_string = "";
    public ArrayList<String> k = new ArrayList<String>();
    public Map map = new HashMap();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_graph);
        double balance = 0.0;
        //kategorije
        String kat = loadData();
        try {

            for (int i = 0; i < kat.split("#").length; i++) {
                try {
                    if (!k.contains(kat.split("#")[i]) & !kat.split("#")[i].equals("")) {
                        k.add(kat.split("#")[i]);
                    }
                } catch (Exception e) {
                }
            }
            //Log.d("kat", Arrays.toString(k.toArray()));
            for (int i = 0; i < k.size(); i++) {
                map.put(k.get(i), 0.0);
            }
            String[] sez = izpisi().split("\n");
            for (int i = 0; i < sez.length; i++) {
                for (int j = 0; j < k.size(); j++) {
                    String[] v = sez[i].split("#");
                    if (v[1].equals(k.get(j))) {
                        Double d = Double.parseDouble(map.get(v[1]).toString()) + Double.parseDouble(v[3].replace("€", ""));
                        //Log.d("kat", String.valueOf(v[3]));
                        if(v[2].equals("+")){
                            balance += Double.parseDouble(v[3].replace("€", ""));
                        }
                        if(v[2].equals("-")){
                            balance -= Double.parseDouble(v[3].replace("€", ""));
                        }
                        map.put(v[1], d);
                    }
                }
            }

            //Log.d("kat", map.toString());
        }catch (Exception e){}

        Log.d("kat", map.toString());
        FloatingActionButton plus = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent());
                startActivity(new Intent(OverviewGraphActivity.this, Main2Activity.class));
            }
        });

        FloatingActionButton nastavitve = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        nastavitve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(getIntent());
                startActivity(new Intent(OverviewGraphActivity.this, UserSettingsActivity.class));
            }
        });

        //pie char
        double sum = 0.0;
        for(int i = 0; i < map.size(); i++){
            sum += Double.parseDouble(map.get(k.get(i)).toString());
        }
        Log.d("kat", String.valueOf(sum));

        // tole spodi je primer uporabe grafa
        final PieChartView pieChartView = findViewById(R.id.spendingChart);
        List<SliceValue> pieData = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < map.size(); i++){
            int nextInt = random.nextInt(0xffffff + 1);
            String colorCode = String.format("#%06x", nextInt);

            pieData.add(new SliceValue((float) (Double.parseDouble(map.get(k.get(i)).toString())/sum), Color.parseColor(colorCode)).setLabel(k.get(i)+" " + map.get(k.get(i)).toString()));
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);

        if(balance > 0){
            pieChartData.setHasCenterCircle(true).setCenterText1("Balance: " + balance + "€").setCenterText1FontSize(14).setCenterText1Color(Color.GREEN);
        }
        if(balance < 0){
            pieChartData.setHasCenterCircle(true).setCenterText1("Balance: " + balance + "€").setCenterText1FontSize(14).setCenterText1Color(Color.RED);
        }
        pieChartView.setPieChartData(pieChartData);

        //tu se konča primer uporabe grafa

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
                    if(xDiff>yDiff) { // če je razlika v x osi večje, gre za horizontaln premik
                        if (x1 < x2) { //levo

                            Intent i = new Intent(OverviewGraphActivity.this, UserSettingsActivity.class);
                            startActivity(i);
                        }else  { //desno
                            Intent i = new Intent(OverviewGraphActivity.this, Main2Activity.class);
                            startActivity(i);
                        }
                    }else{ // drugače je v y osi večja in gre za vertikaln
                        if (y1 > y2) { //dol
                            Intent i = new Intent(OverviewGraphActivity.this, UserHistoryActivity.class);
                            startActivity(i);
                        }
                    }
                }
                break;
        }
        return false;
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

}
