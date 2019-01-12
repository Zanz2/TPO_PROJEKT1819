package com.tpo.sparovcek.sparovcek;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class OverviewGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_graph);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // tole spodi je primer uporabe grafa
        final PieChartView pieChartView = findViewById(R.id.spendingChart);
        List<SliceValue> pieData = new ArrayList<>();
        pieData.add(new SliceValue(45, Color.BLUE).setLabel(" kategorija 1 test "));
        pieData.add(new SliceValue(35, Color.RED).setLabel(" kategorija 2 test "));
        pieData.add(new SliceValue(20, Color.MAGENTA).setLabel(" kategorija 3 test "));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1(" Stroški zapravljanja ").setCenterText1FontSize(12);
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
                if(xDiff>yDiff) { // če je razlika v x osi večje, gre za horizontaln premik
                    if (x1 < x2) { //levo
                        Intent i = new Intent(OverviewGraphActivity.this, Main2Activity.class);
                        startActivity(i);
                    }else  { //desno

                    }
                }else{ // drugače je v y osi večja in gre za vertikaln
                    if (y1 > y2) { //dol
                        Intent i = new Intent(OverviewGraphActivity.this, UserHistoryActivity.class);
                        startActivity(i);
                    }
                }
                break;
        }
        return false;
    }

}
