package Chess;

public class Coordinates implements Cloneable {

    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        int switchedX;
        String switchedY = "";
        switchedX = 8 - x;
        switch (y) {
            case 0:
                switchedY = "a";
                break;
            case 1:
                switchedY = "b";
                break;
            case 2:
                switchedY = "c";
                break;
            case 3:
                switchedY = "d";
                break;
            case 4:
                switchedY = "e";
                break;
            case 5:
                switchedY = "f";
                break;
            case 6:
                switchedY = "g";
                break;
            case 7:
                switchedY = "h";
                break;
        }
        return switchedY + switchedX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates)) {
            return false;
        }
        Coordinates coordinates = (Coordinates) obj;
        return this.x == coordinates.getX() && this.y == coordinates.getY();
    }

    @Override
    public int hashCode() {
        return (x + " " + y).hashCode();
    }

    @Override
    public Object clone() {
        return new Coordinates(x, y);
    }
}
