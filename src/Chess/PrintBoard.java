package Chess;/* Created by oguzkeremyildiz on 1.12.2020 */

public abstract class PrintBoard implements Runnable{
    public abstract void print(Game game);
    public abstract void humanMove() throws InterruptedException;
}
