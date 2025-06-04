package model.score;


import java.io.Serializable;
import java.util.HashMap;

public class ScoreMap implements Serializable {

    HashMap<String, Integer> scoreMap = new HashMap<>();

    public HashMap<String, Integer> getMap() {
        return scoreMap;
    }

}
