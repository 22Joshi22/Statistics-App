package de.fhdw.joshiidkwhy.statistico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // UI-Element für die Ergebnisse
        TextView resultTextView = findViewById(R.id.resultTextView);

        // Hole die Ergebnisse aus dem Intent
        Intent intent = getIntent();
        String results = intent.getStringExtra("RESULTS");

        // Ergebnisse anzeigen
        if (results != null) {
            resultTextView.setText(results);
        } else {
            resultTextView.setText("No results available.");
        }
    }
}
