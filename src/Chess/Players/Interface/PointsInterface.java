package Chess.Players.Interface;/* Created by oguzkeremyildiz on 5.12.2020 */

import Chess.Game;

import java.util.HashMap;

public interface PointsInterface {
    double calculatePoints(Game game);
    HashMap<String, Integer> setMap();
}
