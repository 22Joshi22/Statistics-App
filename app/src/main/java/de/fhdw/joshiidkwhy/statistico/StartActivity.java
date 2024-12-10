package de.fhdw.joshiidkwhy.statistico;

import de.fhdw.joshiidkwhy.statistico.MainActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FILE_PICKER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Buttons initialisieren
        Button loadFileButton = findViewById(R.id.buttonLoadFile);
        Button manualEntryButton = findViewById(R.id.buttonManualEntry);

        // Event Listener für Buttons
        loadFileButton.setOnClickListener(v -> openFilePicker());
        manualEntryButton.setOnClickListener(v -> {
            Intent intent = new Intent(StartActivity.this, ManualEntryActivity.class);
            startActivity(intent);
        });
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
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                intent.putExtra("FILE_URI", uri.toString());
                startActivity(intent);
            } else {
                Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

