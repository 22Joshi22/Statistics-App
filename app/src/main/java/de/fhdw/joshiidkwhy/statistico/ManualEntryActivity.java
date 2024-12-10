package de.fhdw.joshiidkwhy.statistico;

import de.fhdw.joshiidkwhy.statistico.R;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManualEntryActivity extends AppCompatActivity {

    private EditText mDataInput;
    private Button mCalculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        // UI-Elemente initialisieren
        mDataInput = findViewById(R.id.editTextDataInput);
        mCalculateButton = findViewById(R.id.buttonCalculateManual);

        mCalculateButton.setOnClickListener(v -> {
            String input = mDataInput.getText().toString();
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter data.", Toast.LENGTH_SHORT).show();
            } else {
                // Daten in eine Liste konvertieren
                List<Double> numericValues = parseInput(input);
                if (numericValues.isEmpty()) {
                    Toast.makeText(this, "Invalid data format.", Toast.LENGTH_SHORT).show();
                } else {
                    // Statistiken berechnen
                    String result = calculateStatistics(numericValues);
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private List<Double> parseInput(String input) {
        List<Double> numericValues = new ArrayList<>();
        try {
            String[] parts = input.split(",");
            for (String part : parts) {
                numericValues.add(Double.parseDouble(part.trim()));
            }
        } catch (NumberFormatException e) {
            numericValues.clear(); // Ungültige Eingabe
        }
        return numericValues;
    }

    private String calculateStatistics(List<Double> values) {
        double mean = calculateMean(values);
        double median = calculateMedian(values);
        double q1 = calculateQuartile(values, 25);
        double q3 = calculateQuartile(values, 75);

        return "Mean: " + mean + "\nMedian: " + median + "\nQ1: " + q1 + "\nQ3: " + q3;
    }

    private double calculateMean(List<Double> values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }

    private double calculateMedian(List<Double> values) {
        values.sort(Double::compareTo);
        int middle = values.size() / 2;
        if (values.size() % 2 == 0) {
            return (values.get(middle - 1) + values.get(middle)) / 2.0;
        } else {
            return values.get(middle);
        }
    }

    private double calculateQuartile(List<Double> values, int percentile) {
        values.sort(Double::compareTo);
        int index = (int) Math.ceil(percentile / 100.0 * values.size()) - 1;
        return values.get(Math.max(index, 0));
    }
}
