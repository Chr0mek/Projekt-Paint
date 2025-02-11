package Shapes;

import java.awt.*;

public class Kolo extends Figura {
    public Kolo(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public String toString() {
        return "Kolo\n" + x + "\n" + y + "\n" + color.getRed() + "\n" + color.getGreen() + "\n" + color.getBlue() + "\n";
    }
}
