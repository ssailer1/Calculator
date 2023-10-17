package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText number1, number2;
    private RadioGroup operation;
    private Button calc, ms, mr;
    private TextView ausgabe;

    private SharedPreferences sp;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operation = findViewById(R.id.operation);
        operation.setActivated(false);

    }

    @Override
    protected void onStart() {
        super.onStart();
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        calc = findViewById(R.id.calc);
        ms = findViewById(R.id.ms);
        mr = findViewById(R.id.mr);
        ausgabe = findViewById(R.id.ausgabe);
        ausgabe.setText("");
        ausgabe.setTextColor(0xFFFFFFFF);
        ausgabe.setBackgroundColor(0xFF0000FF);

        operation.setActivated(true);
        sp =  getSharedPreferences("Operation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String save = String.valueOf(operation.getCheckedRadioButtonId());
                editor.putString("selectedOperation", save);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Erfolgreich gespeichert", Toast.LENGTH_SHORT).show();
            }
        });

        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savedOp  = sp.getString("selectedOperation", "");
                System.out.println("Test: "+savedOp);

                if (!(savedOp.equals(""))) {
                    for (int i = 0; i < operation.getChildCount(); i++) {
                        RadioButton rb = (RadioButton) operation.getChildAt(i);
                        if (String.valueOf(rb.getId()).equals(savedOp)) {
                            operation.check(Integer.parseInt(savedOp));
                        }
                    }
                }
            }
        });

        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        ausgabe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setBack();
                return true;
            }
        });




    }

    // Füge diese Methode hinzu
    private void setRadioButtonsActivated(boolean activated) {
        for (int i = 0; i < operation.getChildCount(); i++) {
            View childView = operation.getChildAt(i);
            if (childView instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) childView;
                radioButton.setEnabled(activated);
            }
        }
    }

    private void setBack(){
        ausgabe.setText("0");
        number1.setText("");
        number2.setText("");
    }
    private void calculate() {

        int a = getRadioNumber();
        Color  b = new Color();

        double erg = 0;
        try {
            double n1 = Double.parseDouble(number1.getText().toString());
            double n2 = Double.parseDouble(number2.getText().toString());


            switch (a) {
                case (0):
                    erg= n1 + n2;
                    break;
                case (1):
                    erg = n1 - n2;
                    break;
                case (2):
                    erg = n1 * n2;
                    break;
                case (3):
                    erg = n1 / n2;
            }
        } catch (Exception e) {
            System.out.println(e);
            ausgabe.setText("Error");
        }
        ausgabe.setText(String.valueOf(erg));
        if(erg>=0){ ausgabe.setBackgroundColor(0xFF000000);}
        else{ ausgabe.setBackgroundColor(0xFFFF0000);}
    }
    private int getRadioNumber(){
        int selectedId = operation.getCheckedRadioButtonId();

        if (selectedId == R.id.plus) {
            return 0; // Addition
        } else if (selectedId == R.id.minus) {
            return 1; // Subtraktion
        } else if (selectedId == R.id.multiply) {
            return 2; // Multiplikation
        } else if (selectedId == R.id.divide) {
            return 3; // Division
        } else {
            return -1; // Falls kein RadioButton ausgewählt ist oder unbekannter RadioButton ausgewählt wurde
        }



    }
}