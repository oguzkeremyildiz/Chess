package Chess.Players;/* Created by oguzkeremyildiz on 1.12.2020 */

import Chess.Game;
import Chess.Move;

import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class Player {

    protected Game game;
    protected HashMap<Integer, Integer> integerMap;
    protected HashMap<String, Integer> stringMap;

    public Player(Game game) {
        this.game = game;
        integerMap = setIntegerMap();
        stringMap = setStringMap();
    }

    protected static HashMap<Integer, Integer> setIntegerMap() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(8, 0);
        map.put(7, 1);
        map.put(6, 2);
        map.put(5, 3);
        map.put(4, 4);
        map.put(3, 5);
        map.put(2, 6);
        map.put(1, 7);
        return map;
    }

    protected static HashMap<String, Integer> setStringMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a", 0);
        map.put("b", 1);
        map.put("c", 2);
        map.put("d", 3);
        map.put("e", 4);
        map.put("f", 5);
        map.put("g", 6);
        map.put("h", 7);
        return map;
    }

    public abstract Move findMove() throws CloneNotSupportedException, FileNotFoundException;
}
