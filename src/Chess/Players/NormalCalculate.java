package Chess.Players;/* Created by oguzkeremyildiz on 5.12.2020 */

import Chess.Game;

import java.util.HashMap;

public class NormalCalculate implements PointsInterface {

    private HashMap<String, Integer> pointMap = setMap();

    @Override
    public int calculatePoints(Game game) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (game.getPiece(i, j) != null) {
                    sum += pointMap.get(game.getPiece(i, j).getName());
                }
            }
        }
        return sum;
    }

    @Override
    public HashMap<String, Integer> setMap() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("P", -1);
        map.put("B", -3);
        map.put("N", -3);
        map.put("R", -5);
        map.put("Q", -9);
        map.put("K", -1000);
        map.put("p", 1);
        map.put("b", 3);
        map.put("n", 3);
        map.put("r", 5);
        map.put("q", 9);
        map.put("k", 1000);
        return map;
    }
}
