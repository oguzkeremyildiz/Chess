package Chess.Players;/* Created by oguzkeremyildiz on 1.12.2020 */

import Chess.Game;
import Chess.Move;

import java.io.FileNotFoundException;
import java.util.HashMap;

public abstract class Player {

    protected Game game;
    protected HashMap<String, Integer> stringMap;

    public Player(Game game) {
        this.game = game;
        stringMap = setStringMap();
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

}
