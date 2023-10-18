package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main Klasse für den Android Calculator
 * @author Sebastian Sailer
 * @version 2023-10-17
 */
public class MainActivity extends AppCompatActivity {
    /*----------------------------------------------------*/
    // Attribute
    private EditText number1, number2;
    private RadioGroup operation;
    private Button calc, ms, mr;
    private TextView ausgabe;

    private SharedPreferences sp;


    /*----------------------------------------------------*/
    // Sobald die App erstellt wird soll ... ausgeführt werden
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operation = findViewById(R.id.operation);
        operation.setActivated(false); //RadioButton deaktivieren

    }

    /*----------------------------------------------------*/
    // Sobald die App gestartet wird soll ... ausgeführt werden
    @Override
    protected void onStart() {
        super.onStart();

        /*----------------------------------------------------*/
        // Attributen werte geben / zuweisen
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        calc = findViewById(R.id.calc);
        ms = findViewById(R.id.ms);
        mr = findViewById(R.id.mr);
        ausgabe = findViewById(R.id.ausgabe);
        ausgabe.setText("");
        ausgabe.setTextColor(0xFFFFFFFF);
        ausgabe.setBackgroundColor(0xFF0000FF);

        operation.setActivated(true); //RadioButton aktivieren

        /*----------------------------------------------------*/
        //Zwischenspeicher
        sp = getSharedPreferences("Operation", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        /*----------------------------------------------------*/
        // Was passieren soll, wenn der ms Knopf gedrückt wird
        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String save = String.valueOf(operation.getCheckedRadioButtonId());
                editor.putString("selectedOperation", save);
                editor.apply();
                Toast.makeText(getApplicationContext(), "Erfolgreich gespeichert", Toast.LENGTH_SHORT).show();
            }
        });
        /*----------------------------------------------------*/
        // Was passieren soll, wenn der mr Knopf gedrückt wird
        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String savedOp = sp.getString("selectedOperation", "");
                System.out.println("Test: " + savedOp);

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
        /*----------------------------------------------------*/
        // Was passieren soll, wenn der calc Knopf gedrückt wird
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });
        /*----------------------------------------------------*/
        // Was passieren soll, wenn die Ausgabe berührt wird
        ausgabe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setBack();
                return true;
            }
        });

    }

    /*----------------------------------------------------*/
    // Werte zurücksetzen
    private void setBack() {
        ausgabe.setText("0");
        number1.setText("");
        number2.setText("");
    }

    /*----------------------------------------------------*/
    // Methode zum berechnen
    private void calculate() {

        int a = getRadioNumber();
        Color b = new Color();

        double erg = 0;
        try {
            double n1 = Double.parseDouble(number1.getText().toString());
            double n2 = Double.parseDouble(number2.getText().toString());


            switch (a) {
                case (0):
                    erg = n1 + n2;
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
        if (erg >= 0) {
            ausgabe.setBackgroundColor(0xFF000000);
        } else {
            ausgabe.setBackgroundColor(0xFFFF0000);
        }
    }

    /*----------------------------------------------------*/
    // returned welcher Recheoperator verwendet werden soll
    private int getRadioNumber() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        if (item.getItemId() == R.id.info) {
            Toast toast = Toast.makeText(this, "Sailer Sebastian, @tgm, 2023/24", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }
        if (item.getItemId() == R.id.reset) {
            setBack();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
