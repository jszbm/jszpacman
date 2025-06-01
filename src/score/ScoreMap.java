package score;

import java.io.Serializable;
import java.util.HashMap;

public class ScoreMap implements Serializable {

    HashMap<String, Integer> scoreMap = new HashMap<>();

    public void putEntry(String name, int score) {
        scoreMap.put(name, score);
    }

    public HashMap getMap() {
        return scoreMap;
    }

}
