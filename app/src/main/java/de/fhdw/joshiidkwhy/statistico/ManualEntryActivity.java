package de.fhdw.joshiidkwhy.statistico;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ManualEntryActivity extends AppCompatActivity {

    private EditText mDataInput;
    private Button mPassDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        // Initialize UI elements
        mDataInput = findViewById(R.id.editTextDataInput);
        mPassDataButton = findViewById(R.id.buttonPassData);

        mPassDataButton.setOnClickListener(v -> {
            String input = mDataInput.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter data.", Toast.LENGTH_SHORT).show();
            } else if (!input.matches("^(\\d+(,\\d+)*;?)+$")) {
                Toast.makeText(this, "Invalid format. Use numbers separated by commas and rows separated by semicolons.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    // Save input data to a CSV file
                    File csvFile = saveDataToCsv(input);

                    // Pass the CSV file URI to MainActivity
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("FILE_URI", Uri.fromFile(csvFile).toString());
                    startActivity(intent);
                } catch (IOException e) {
                    Toast.makeText(this, "Error saving data to CSV file.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private File saveDataToCsv(String data) throws IOException {
        // Create a temporary CSV file in the cache directory
        File csvFile = new File(getCacheDir(), "manual_input_data.csv");

        // Write the input to the CSV file
        try (FileWriter writer = new FileWriter(csvFile)) {
            String[] rows = data.split(";"); // Split input into rows by semicolon
            for (String row : rows) {
                writer.write(row.replace(",", ";") + "\n"); // Write each row into the CSV file
            }
        }

        return csvFile;
    }
}
