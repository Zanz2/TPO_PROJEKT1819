package com.tpo.sparovcek.sparovcek;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import android.view.MotionEvent;
import android.view.View;

import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection;

public class UserHistoryActivity extends AppCompatActivity {

    RecyclerView rv;
    private List<Vnos> vnosi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);

        //final TextView izpis = findViewById(R.id.izpis);

        vnosi = new ArrayList<>();

        String izpisi = izpisi();
        if(izpisi!="") {
            String[] vrstice = izpisi.split("\n");
            for (int i = 0; i < vrstice.length; i++) {
                vnosi.add(new Vnos(vrstice[i]));
            }
        /*
        final String[] newvrstica = new String[vrstica.length];
        */
            //izpis.setText(Arrays.toString(vrstica));
            //izpis.setText(izpisi().replaceAll("#", "    "));

            rv = (RecyclerView) findViewById(R.id.rv);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);
            RVAdapter adapter = new RVAdapter(vnosi);
            rv.setAdapter(adapter);
        }

    }



    float x1,x2,y1,y2;
    public boolean onTouchEvent(MotionEvent touchevent){
        //super.onTouchEvent(touchevent);
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
                            Intent i = new Intent(UserHistoryActivity.this, OverviewGraphActivity.class);
                            startActivity(i);
                        }
                    } else { // drugače je v y osi večja in gre za vertikaln
                        if (y1 > y2) { //dol
                            Intent i = new Intent(UserHistoryActivity.this, OverviewGraphActivity.class);
                            startActivity(i);
                        } else { // gor
                            Intent i = new Intent(UserHistoryActivity.this, OverviewGraphActivity.class);
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
        String selectQuery = "SELECT  * FROM " + baza.pb.TABLE_NAME + " ORDER BY "+ baza.pb._ID +" DESC ";
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

    class Vnos {
        public String naziv;
        public String kategorija;
        public String znesek;
        public BigDecimal znesek_value;
        public String timestamp;
        public String datetime;

        Vnos(String value) {
            String[] values_arr = value.split("#");
            if(values_arr.length==1){
                naziv = values_arr[0];
                kategorija = values_arr[0];
                znesek = values_arr[0];
                znesek_value = new BigDecimal(values_arr[0]);
                timestamp = values_arr[0];
                datetime = values_arr[0];
            }else {
                naziv = values_arr[0];
                kategorija = values_arr[1];
                znesek = values_arr[2] + " " + values_arr[3];
                znesek_value = new BigDecimal(values_arr[2] + values_arr[3].replace("€", ""));
                timestamp = values_arr[4];
                datetime = values_arr[5];
            }
        }
    }


}

