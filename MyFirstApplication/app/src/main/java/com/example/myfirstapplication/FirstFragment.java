package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class FirstFragment extends Fragment {

    private EditText number1, number2;
    private RadioGroup operation;
    private Button calc, ms, mr;
    private TextView ausgabe;

    // Hier könntest du eine Klasse zum Speichern von Werten implementieren
    private static class MemoryStorage {
        private static double storedValue = 0;

        static void store(double value) {
            storedValue = value;
        }

        static double retrieve() {
            return storedValue;
        }

        static void clear() {
            storedValue = 0;
        }
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        number1 = view.findViewById(R.id.number1);
        number2 = view.findViewById(R.id.number2);
        operation = view.findViewById(R.id.operation);
        calc = view.findViewById(R.id.calc);
        ms = view.findViewById(R.id.ms);
        mr = view.findViewById(R.id.mr);
        ausgabe = view.findViewById(R.id.ausgabe);

        ausgabe.setText("hallo");



        ms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToMemory();
            }
        });

        mr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveFromMemory();
            }
        });
    }

    private void calculate() {

        int a =0; //get Radio
        try {
            double n1 = Double.parseDouble(number1.getText().toString());
            double n2 = Double.parseDouble(number2.getText().toString());

            switch (a) {
                case (0):
                    System.out.println(n1 + n2 + "+");
                    ausgabe.setText(String.valueOf(n1 + n2));
                    break;
                case (1):
                    System.out.println(n1 - n2 + "-");
                    ausgabe.setText(String.valueOf(n1 - n2));
                    break;
                case (2):
                    System.out.println(n1 * n2 + "*");
                    ausgabe.setText(String.valueOf(n1 * n2));
                    break;
                case (3):
                    System.out.println(n1 / n2 + "/");
                    ausgabe.setText(String.valueOf(n1 / n2));
            }
        }catch (Exception e){
            System.out.println(e);
            ausgabe.setText("Error");
        }
        // Hier könntest du die eigentliche Berechnung durchführen und das Ergebnis in der TextView anzeigen
    }

    private void saveToMemory() {
        // Hier wird der Wert in der TextView in das Speicherobjekt gespeichert
        try {
            double value = Double.parseDouble(ausgabe.getText().toString());
            MemoryStorage.store(value);
        } catch (NumberFormatException e) {
            // Handle Fehler bei der Konvertierung
        }
    }

    private void retrieveFromMemory() {
        // Hier wird der gespeicherte Wert aus dem Speicherobjekt abgerufen und in der TextView angezeigt
        double retrievedValue = MemoryStorage.retrieve();
        ausgabe.setText(String.valueOf(retrievedValue));
    }
}

