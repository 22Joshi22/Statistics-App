package de.fhdw.joshiidkwhy.statistico;

import java.util.HashMap;

public class Data {
    // HashMap to store column name-value pairs
    private final HashMap<String, Object> dataMap;

    // Constructor initializes the HashMap
    public Data() {
        dataMap = new HashMap<>();
    }

    // Method to add a key-value pair to the HashMap
    public void addData(String key, Object value) {
        dataMap.put(key, value);
    }

    // Method to retrieve a value based on column name
    public Object getData(String key) {
        return dataMap.get(key);
    }

    // Method to retrieve the entire map
    public HashMap<String, Object> getDataMap() {
        return dataMap;
    }
}
