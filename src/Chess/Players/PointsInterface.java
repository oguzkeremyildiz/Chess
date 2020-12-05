package Chess.Players;/* Created by oguzkeremyildiz on 5.12.2020 */

import Chess.Game;

import java.util.HashMap;

public interface PointsInterface {
    int calculatePoints(Game game);
    HashMap<String, Integer> setMap();
}
