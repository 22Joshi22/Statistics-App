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
import java.sql.Array;

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
        intent.setType("text/*"); // Adjust MIME type for your file type (e.g., "text/csv" for CSV files)
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
        String[] collum_name;
        try (InputStream inputStream = getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            int counter = 0;
            while ((line = reader.readLine()) != null) {
                if (counter == 0) {
                    title = line.split(";"); // Split the first line by ";"
                }
                if (counter != 0){

                }
                counter++;
            }
            //stringBuilder.append(line).append("\n"); // Append lines if needed

            // Display the file content in the TextView
            dataTextView.setText(stringBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show();
        }
    }

}