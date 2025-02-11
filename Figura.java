package Shapes;

import java.awt.*;

public abstract class Figura {
    protected int x;
    protected int y;
    protected Color color;

    public Figura(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}
