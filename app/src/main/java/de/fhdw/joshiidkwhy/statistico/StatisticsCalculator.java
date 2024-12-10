package de.fhdw.joshiidkwhy.statistico.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatisticsCalculator {

    private final List<Double> mNumericValues;

    public StatisticsCalculator(List<String[]> data) {
        mNumericValues = new ArrayList<>();
        for (int i = 1; i < data.size(); i++) { // Ignoriere Header
            for (String value : data.get(i)) {
                try {
                    mNumericValues.add(Double.parseDouble(value));
                } catch (NumberFormatException ignored) {
                }
            }
        }
    }

    public double calculateMean() {
        double sum = 0;
        for (double value : mNumericValues) {
            sum += value;
        }
        return sum / mNumericValues.size();
    }

    public double calculateMedian() {
        Collections.sort(mNumericValues);
        int middle = mNumericValues.size() / 2;
        if (mNumericValues.size() % 2 == 0) {
            return (mNumericValues.get(middle - 1) + mNumericValues.get(middle)) / 2.0;
        } else {
            return mNumericValues.get(middle);
        }
    }

    public String calculateQuartiles() {
        Collections.sort(mNumericValues);
        double q1 = calculatePercentile(25);
        double q3 = calculatePercentile(75);
        return "Q1: " + q1 + ", Q3: " + q3;
    }

    private double calculatePercentile(int percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * mNumericValues.size()) - 1;
        return mNumericValues.get(Math.max(0, index));
    }
}
