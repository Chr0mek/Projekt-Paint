package Shapes;

import java.awt.*;

public class Kwadrat extends Figura {

    public Kwadrat(int x, int y, Color color) {
        super(x, y, color);
    }

    @Override
    public String toString() {
        return "Kwadrat\n" + x + "\n" + y + "\n" + color.getRed() + "\n" + color.getGreen() + "\n" + color.getBlue() + "\n";
    }
}
