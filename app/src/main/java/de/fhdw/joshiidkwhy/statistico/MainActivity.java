package de.fhdw.joshiidkwhy.statistico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FILE_PICKER = 2;
    private TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        Button loadFileButton = findViewById(R.id.buttonLoadFile);
        dataTextView = findViewById(R.id.dataTextView);

        // Set up the click listener for the "Load File" button
        loadFileButton.setOnClickListener(v -> openFilePicker());
    }

    private void openFilePicker() {
        // Create an intent to open the file picker
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*"); // Adjust MIME type for CSV files
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select a file"), REQUEST_CODE_FILE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE_PICKER && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                // Read the file content
                readFile(uri);
            }
        }
    }

    private void readFile(Uri uri) {
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            List<String[]> rows = new ArrayList<>();
            int rowCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(";");
                rows.add(row);
                rowCount++;
            }

            if (!rows.isEmpty()) {
                // Generate stats
                String stats = generateStats(rows);
                dataTextView.setText(stats);
            } else {
                dataTextView.setText("No data found in the file.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }

    private String generateStats(List<String[]> rows) {
        StringBuilder statsBuilder = new StringBuilder();

        // Assuming the first row contains column names
        String[] columnNames = rows.get(0);
        int columnCount = columnNames.length;

        statsBuilder.append("File Stats:\n");
        statsBuilder.append("Total Rows: ").append(rows.size() - 1).append("\n"); // Exclude header row
        statsBuilder.append("Columns:\n");

        for (int i = 0; i < columnCount; i++) {
            List<Double> columnValues = new ArrayList<>();
            for (int j = 1; j < rows.size(); j++) { // Skip header row
                try {
                    columnValues.add(Double.parseDouble(rows.get(j)[i]));
                } catch (NumberFormatException ignored) {
                    // Ignore non-numeric values
                }
            }

            double mean = calculateMean(columnValues);
            statsBuilder.append(columnNames[i]).append(" -> Mean: ").append(mean).append("\n");
        }

        return statsBuilder.toString();
    }

    private double calculateMean(List<Double> values) {
        if (values.isEmpty()) return 0;
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.size();
    }
}
