package model.score;


import java.io.Serializable;
import java.util.*;

public class ScoreMap implements Serializable {

    HashMap<String, Integer> scoreMap = new LinkedHashMap<>();

    public HashMap<String, Integer> getMap() {
        sort();
        return scoreMap;
    }

    public void putScore(String name, Integer score) {
        scoreMap.put(name, score);
        sort();
    }

    private void sort() {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(scoreMap.entrySet());

        entries.sort(Map.Entry.comparingByValue());

        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        for(Map.Entry<String, Integer> entry : entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        scoreMap = sortedMap;
    }
}
