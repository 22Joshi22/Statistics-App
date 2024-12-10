package de.fhdw.joshiidkwhy.statistico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // UI-Element initialisieren
        TextView resultsTextView = findViewById(R.id.resultsTextView);

        // Ergebnisse aus dem Intent empfangen
        Intent intent = getIntent();
        String results = intent.getStringExtra("RESULTS");

        // Ergebnisse anzeigen
        if (results != null) {
            resultsTextView.setText(results);
        } else {
            resultsTextView.setText("No results to display.");
        }
    }
}
