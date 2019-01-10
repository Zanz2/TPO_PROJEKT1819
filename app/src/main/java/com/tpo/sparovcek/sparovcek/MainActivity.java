package com.tpo.sparovcek.sparovcek;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {
    private String test_string = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView name = (TextView) findViewById(R.id.nameText);
        final TextView pin = (TextView) findViewById(R.id.pinText);
        final TextView test = (TextView) findViewById(R.id.textView);
        String debugValue = loadData();
        if(debugValue!="") {
            String ime = loadData().split("#")[0].replaceAll("\\s+", "");
            name.setText(ime);
            //za testerat
            String geslo = loadData().split("#")[1].replaceAll("\\s+", "");
            pin.setText(geslo);
            //test.setText("samzdatoteke" + loadData());
            test.setText("");
        }
        //GUMBI
        final Button login = (Button) findViewById(R.id.button_login);
        Button register = (Button) findViewById(R.id.button_register);

        //za nakonc
        /*
        if(test_string == ""){
            login.setVisibility(View.INVISIBLE);
            register.setVisibility(View.VISIBLE);
        }else {
            login.setVisibility(View.VISIBLE);
            register.setVisibility(View.INVISIBLE);
        }
        */

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ime = loadData().split("#")[0].replaceAll("\\s+","");
                String geslo = loadData().split("#")[1].replaceAll("\\s+","");
                if(ime.equals(name.getText().toString()) & geslo.equals(pin.getText().toString())){
                    finish();
                    startActivity(getIntent());
                    startActivity(new Intent(MainActivity.this, Main2Activity.class));
                }else{
                    test.setText("Napačno uporabniško ime ali geslo!");
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                if(preveri_geslo(pin.getText().toString())){
                    String a = name.getText().toString() + "#" + pin.getText().toString();
                    saveData(a);
                    //test.setText("register" + loadData());
                    login.callOnClick();
                }
            }
        });
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
            System.out.println("string: "+test_string);
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test_string;
    }

    boolean preveri_geslo(String g){
        if(g.length() == 1)
        //test
        //if(g.length() == 4)
            return true;
        return false;
    }
}
