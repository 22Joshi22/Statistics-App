package de.fhdw.joshiidkwhy.statistico.utils;

import android.content.Context;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String[]> readFile(Context context, Uri uri) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            List<String[]> data = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(";"));
            }
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatData(List<String[]> data) {
        StringBuilder builder = new StringBuilder();
        for (String[] row : data) {
            builder.append(String.join(", ", row)).append("\n");
        }
        return builder.toString();
    }
}
