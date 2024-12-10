package de.fhdw.joshiidkwhy.statistico;

import de.fhdw.joshiidkwhy.statistico.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import de.fhdw.joshiidkwhy.statistico.logic.StatisticsCalculator;
import de.fhdw.joshiidkwhy.statistico.utils.FileReader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FILE_PICKER = 2;
    private TextView mDataTextView, mResultTextView;
    private CheckBox mCheckboxMean, mCheckboxMedian, mCheckboxQuartiles;
    private List<String[]> mLoadedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI-Elemente initialisieren
        Button loadFileButton = findViewById(R.id.buttonLoadFile);
        Button calculateButton = findViewById(R.id.buttonCalculate);
        mDataTextView = findViewById(R.id.dataTextView);
        mResultTextView = findViewById(R.id.resultTextView);
        mCheckboxMean = findViewById(R.id.checkboxMean);
        mCheckboxMedian = findViewById(R.id.checkboxMedian);
        mCheckboxQuartiles = findViewById(R.id.checkboxQuartiles);

        // Datei laden, falls URI übergeben wurde
        Intent intent = getIntent();
        String fileUriString = intent.getStringExtra("FILE_URI");
        if (fileUriString != null) {
            Uri fileUri = Uri.parse(fileUriString);
            loadFile(fileUri);
        }

        // Event Listener für den File Picker Button
        loadFileButton.setOnClickListener(v -> openFilePicker());

        // Event Listener für den Berechnen-Button
        calculateButton.setOnClickListener(v -> calculateStats());

        // Wiederherstellung der Instanzdaten
        if (savedInstanceState != null) {
            mLoadedData = (List<String[]>) savedInstanceState.getSerializable("LOADED_DATA");
            String resultText = savedInstanceState.getString("RESULT_TEXT", "");
            if (mLoadedData != null) {
                mDataTextView.setText(FileReader.formatData(mLoadedData));
            }
            mResultTextView.setText(resultText);
        }
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                loadFile(uri);
            } else {
                Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadFile(Uri uri) {
        mLoadedData = FileReader.readFile(this, uri);
        if (mLoadedData != null) {
            mDataTextView.setText(FileReader.formatData(mLoadedData));
        } else {
            Toast.makeText(this, "Error loading file.", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculateStats() {
        if (mLoadedData == null || mLoadedData.isEmpty()) {
            Toast.makeText(this, "Load a file first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ergebnisse berechnen
        StatisticsCalculator calculator = new StatisticsCalculator(mLoadedData);
        StringBuilder results = new StringBuilder();

        if (mCheckboxMean.isChecked()) {
            results.append("Mean:\n").append(calculator.calculateMean()).append("\n");
        }
        if (mCheckboxMedian.isChecked()) {
            results.append("Median:\n").append(calculator.calculateMedian()).append("\n");
        }
        if (mCheckboxQuartiles.isChecked()) {
            results.append("Quartiles:\n").append(calculator.calculateQuartiles()).append("\n");
        }

        mResultTextView.setText(results.toString());
    }

    // Speichere die Daten, bevor die Aktivität zerstört wird
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Speichere die geladenen Daten
        if (mLoadedData != null) {
            outState.putSerializable("LOADED_DATA", (Serializable) mLoadedData);
        }

        // Speichere das Ergebnis
        outState.putString("RESULT_TEXT", mResultTextView.getText().toString());
    }
}
