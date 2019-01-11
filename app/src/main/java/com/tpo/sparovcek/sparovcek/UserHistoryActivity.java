package com.tpo.sparovcek.sparovcek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;

public class UserHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

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

                    }
                }else{ // drugače je v y osi večja in gre za vertikaln
                    if (y1 > y2) { //dol

                    }else { // gor
                        Intent i = new Intent(UserHistoryActivity.this, Main2Activity.class);
                        startActivity(i);
                    }
                }
                break;
        }
        return false;
    }
}
