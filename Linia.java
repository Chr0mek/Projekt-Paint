package Shapes;

import java.awt.*;

public class Linia extends Figura{
    private int oldX;
    private int oldY;

    public Linia(int x, int y, int oldX, int oldY, Color color) {
        super(x, y, color);
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    @Override
    public String toString() {
        return "Linia\n" + x + "\n" + y + "\n" + color.getRed() + "\n" + color.getGreen() + "\n" + color.getBlue() + "\n" + oldX + "\n" + oldY + "\n";
    }
}
