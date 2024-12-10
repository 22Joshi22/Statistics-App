package de.fhdw.joshiidkwhy.statistico.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsCalculator {

    private final List<Double> data;

    // Konstruktor, um die Daten zu initialisieren
    public StatisticsCalculator(List<String[]> loadedData) {
        this.data = parseData(loadedData);
    }

    // Daten in Double-Werte umwandeln
    private List<Double> parseData(List<String[]> loadedData) {
        List<Double> parsedData = new ArrayList<>();
        for (String[] row : loadedData) {
            for (String value : row) {
                try {
                    parsedData.add(Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    // Ignoriere ungültige Werte
                }
            }
        }
        return parsedData;
    }

    // Mittelwert berechnen
    public double calculateMean() {
        if (data.isEmpty()) {
            throw new IllegalStateException("No data available to calculate mean.");
        }
        double sum = 0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    // Median berechnen
    public double calculateMedian() {
        if (data.isEmpty()) {
            throw new IllegalStateException("No data available to calculate median.");
        }
        List<Double> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);
        int size = sortedData.size();
        if (size % 2 == 0) {
            return (sortedData.get(size / 2 - 1) + sortedData.get(size / 2)) / 2.0;
        } else {
            return sortedData.get(size / 2);
        }
    }

    // Quartile berechnen
    public String calculateQuartiles() {
        if (data.isEmpty()) {
            throw new IllegalStateException("No data available to calculate quartiles.");
        }
        List<Double> sortedData = new ArrayList<>(data);
        Collections.sort(sortedData);

        double q1 = calculatePercentile(sortedData, 25);
        double q2 = calculateMedian(); // Median entspricht Q2
        double q3 = calculatePercentile(sortedData, 75);

        return "Q1: " + q1 + ", Q2: " + q2 + ", Q3: " + q3;
    }

    // Hilfsmethode zur Berechnung eines Perzentils
    private double calculatePercentile(List<Double> sortedData, double percentile) {
        int n = sortedData.size();
        double rank = percentile / 100.0 * (n - 1);
        int lowerIndex = (int) Math.floor(rank);
        int upperIndex = (int) Math.ceil(rank);

        if (lowerIndex == upperIndex) {
            return sortedData.get(lowerIndex);
        }

        double weight = rank - lowerIndex;
        return sortedData.get(lowerIndex) * (1 - weight) + sortedData.get(upperIndex) * weight;
    }
}
